package Beauty_ECatalog.Beauty_ECatalog.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import Beauty_ECatalog.Beauty_ECatalog.domain.User;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResCreateUserDTO;
import Beauty_ECatalog.Beauty_ECatalog.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User handleCreateUser(User user){
        if(this.userRepository.existsByEmail(user.getEmail()) == false){
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
}
