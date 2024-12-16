package Beauty_ECatalog.Beauty_ECatalog.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import Beauty_ECatalog.Beauty_ECatalog.domain.User;
import Beauty_ECatalog.Beauty_ECatalog.domain.request.ReqLoginDTO;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResCreateUserDTO;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResLoginDTO;
import Beauty_ECatalog.Beauty_ECatalog.service.UserService;
import Beauty_ECatalog.Beauty_ECatalog.util.SecurityUtil;
import Beauty_ECatalog.Beauty_ECatalog.util.error.IdInvalidException;
import jakarta.validation.Valid;

@RestController
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService userService;
    private final SecurityUtil securityUtil;
    private final PasswordEncoder passwordEncoder;
    @Value("${ecatalog.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;
    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil,
            UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/User/Login")
    public ResponseEntity<ResLoginDTO> login(@Valid @RequestBody ReqLoginDTO loginDTO) {
        // Nạp input gồm username/password vào Security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword());
        // xác thực người dùng => cần viết hàm loadUserByUsername
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // nạp thông tin (nếu xử lý thành công) vào SecurityContext

        SecurityContextHolder.getContext().setAuthentication(authentication);
        ResLoginDTO res = new ResLoginDTO();
        User user = this.userService.handleGetUserByUsername(loginDTO.getUsername());
        if (user != null) {
            ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin(user.getId(), user.getEmail(), user.getName(), user.getPhoneNumber(), user.getAddress(), user.getBirthDay(), user.getUserImage(),
                    user.getRole());
            res.setUser(userLogin);
        }
        String access_token = this.securityUtil.createAccessToken(authentication.getName(), res);
        res.setAccessToken(access_token);
        String refresh_token = this.securityUtil.createRefreshToken(loginDTO.getUsername(), res);
        this.userService.updateUserToken(refresh_token, loginDTO.getUsername());

        // set cookies
        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", refresh_token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiration)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(res);
    }
    @GetMapping("/auth/account")
    public ResponseEntity<ResLoginDTO.UserGetAccount> getAccount() {
        String email = SecurityUtil.getCurrentUserLogin() != null ? SecurityUtil.getCurrentUserLogin().get() : "";
        User user = this.userService.handleGetUserByUsername(email);
        ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin();
        ResLoginDTO.UserGetAccount userGetAccount = new ResLoginDTO.UserGetAccount();
        if (user != null) {
            userLogin.setId(user.getId());
            userLogin.setEmail(user.getEmail());
            userLogin.setName(user.getName());
            userLogin.setRole(user.getRole());
            userGetAccount.setUser(userLogin);
        }
        return ResponseEntity.ok().body(userGetAccount);
    }

    @GetMapping("/auth/refresh")
    public ResponseEntity<ResLoginDTO> getRefreshToken(@CookieValue(name = "refresh_token") String refresh_token)
            throws IdInvalidException {
        Jwt decoded = this.securityUtil.checkValidRefreshToken(refresh_token);
        String email = decoded.getSubject();
        User currentUser = this.userService.getUserByRefreshTokenAndEmail(refresh_token, email);
        if (currentUser == null) {
            throw new IdInvalidException("Refresh Token khong hop le");
        }
        ResLoginDTO res = new ResLoginDTO();
        User user = this.userService.handleGetUserByUsername(email);
        if (user != null) {
            ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin(user.getId(), user.getEmail(), user.getName(), user.getPhoneNumber(), user.getAddress(), user.getBirthDay(), user.getUserImage(),
                    user.getRole());
            res.setUser(userLogin);
        }
        String access_token = this.securityUtil.createAccessToken(email, res);
        res.setAccessToken(access_token);
        String new_refresh_token = this.securityUtil.createRefreshToken(email, res);
        this.userService.updateUserToken(new_refresh_token, email);

        // set cookies
        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", new_refresh_token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiration)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(res);
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout() {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";
        String refresh_token = "";
        this.userService.updateUserToken(refresh_token, email);
        ResponseCookie deleteSpringCookie = ResponseCookie
                .from("refresh_token", null)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, deleteSpringCookie.toString())
                .build();
    }

    @PostMapping("/User/Register")
    public ResponseEntity<ResCreateUserDTO> register(@Valid @RequestBody User postManUser) throws IdInvalidException {
        String hashPassword = this.passwordEncoder.encode(postManUser.getPassword());
        postManUser.setPassword(hashPassword);
        User ericUser = this.userService.handleCreateUser(postManUser);
        ResCreateUserDTO resCreateUserDTO = this.userService.convertToResCreateUserDTO(ericUser);
        if (ericUser == null) {
            throw new IdInvalidException(
                    "Email " + postManUser.getEmail() + "đã tồn tại, vui lòng sử dụng email khác.");
        } else {
            return ResponseEntity.ok().body(resCreateUserDTO);
        }
    }
}
