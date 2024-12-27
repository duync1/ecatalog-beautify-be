package Beauty_ECatalog.Beauty_ECatalog.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import Beauty_ECatalog.Beauty_ECatalog.domain.Role;
import Beauty_ECatalog.Beauty_ECatalog.domain.Supplier;
import Beauty_ECatalog.Beauty_ECatalog.domain.User;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResCreateUserDTO;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResultPaginationDTO;
import Beauty_ECatalog.Beauty_ECatalog.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final String uploadDir = "src/main/resources/static/uploads/";
    public UserService(UserRepository userRepository, RoleService roleService){
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public User handleCreateUser(User user){
        if(this.userRepository.existsByEmail(user.getEmail()) == false){
            Role role = this.roleService.getRoleByName("CUSTOMER");
            user.setRole(role);
            this.userRepository.save(user);
            return user;
        }
        return null;
    }

    public ResCreateUserDTO convertToResCreateUserDTO(User user){
        ResCreateUserDTO resCreateUserDTO = new ResCreateUserDTO();
        resCreateUserDTO.setId(user.getId());
        resCreateUserDTO.setName(user.getName());
        resCreateUserDTO.setGender(user.getGender());
        resCreateUserDTO.setEmail(user.getEmail());
        resCreateUserDTO.setCreatedAt(user.getCreatedAt());
        resCreateUserDTO.setBirthDay(user.getBirthDay());
        resCreateUserDTO.setAddress(user.getAddress());
        return resCreateUserDTO;
    }

    public User fetchUserById(long id){
        Optional<User> user = this.userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }
        return null;
    }
    public void handleDeleteUser(User user){
        this.userRepository.delete(user);
    }
    public User handleUpdateUser(User user){
        User currentUser = this.userRepository.save(user);
        return currentUser;
    }

    public User handleGetUserByUsername(String email){
        return this.userRepository.findByEmail(email);
    }
    public void updateUserToken(String token, String email) {
        User currentUser = this.handleGetUserByUsername(email);
        if (currentUser != null) {
            currentUser.setRefreshToken(token);
            this.userRepository.save(currentUser);
        }
    }
    public User getUserByRefreshTokenAndEmail(String token, String email) {
        return this.userRepository.findByRefreshTokenAndEmail(token, email);
    }

    public User getUserByName(String name){
        return this.userRepository.findByName(name);
    }

    public User handleCreateUserBase(User user){
        return this.userRepository.save(user);
    }

    public User handleUpdateUserClient(String email, String name, Instant birthDay, String phoneNumber, String address, MultipartFile multipartFile) throws IOException{
        User dbUser = this.handleGetUserByUsername(email);
        if(name != null){
            dbUser.setName(name);
        }
        if(phoneNumber != null){
            dbUser.setPhoneNumber(phoneNumber);
        }
        if(birthDay != null){
            dbUser.setBirthDay(birthDay);
        }
        if(address != null){
            dbUser.setAddress(address);
        }
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String imagePath = saveImage(multipartFile);
            dbUser.setUserImage(imagePath);
        }
        return this.userRepository.save(dbUser);
    }

    private String saveImage(MultipartFile imageFile) throws IOException {
        if (imageFile == null || imageFile.isEmpty()) {
            return null; // Hoặc trả về giá trị mặc định nếu không có ảnh mới
        }
        Path path = Paths.get(uploadDir);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    
        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        Path filePath = path.resolve(fileName);
        Files.write(filePath, imageFile.getBytes());
    
        return "/uploads/" + fileName;
    }

    public ResultPaginationDTO fetchAllUsers(Specification<User> spec, Pageable pageable) {
        Page<User> page = this.userRepository.findAll(spec, pageable);
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(page.getNumber() + 1);
        meta.setPageSize(page.getSize());
        meta.setPages(page.getTotalPages());
        meta.setTotal(page.getTotalElements());
        resultPaginationDTO.setMeta(meta);
        resultPaginationDTO.setResult(page.getContent());
        return resultPaginationDTO;
    }
}
