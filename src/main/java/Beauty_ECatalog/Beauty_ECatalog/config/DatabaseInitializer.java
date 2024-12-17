package Beauty_ECatalog.Beauty_ECatalog.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Beauty_ECatalog.Beauty_ECatalog.domain.Permission;
import Beauty_ECatalog.Beauty_ECatalog.domain.Role;
import Beauty_ECatalog.Beauty_ECatalog.domain.User;
import Beauty_ECatalog.Beauty_ECatalog.repository.PermissionRepository;
import Beauty_ECatalog.Beauty_ECatalog.repository.RoleRepository;
import Beauty_ECatalog.Beauty_ECatalog.repository.UserRepository;

@Service
public class DatabaseInitializer implements CommandLineRunner{
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseInitializer(
            PermissionRepository permissionRepository,
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>> START INIT DATABASE");
        long countPermissions = this.permissionRepository.count();
        long countRoles = this.roleRepository.count();
        long countUsers = this.userRepository.count();

        if(countPermissions == 0){
            ArrayList<Permission> arr = new ArrayList<>();
            arr.add(new Permission("Create a product", "/Product", "POST", "PRODUCT"));
            arr.add(new Permission("Update a product", "/Product", "PUT", "PRODUCT"));

            this.permissionRepository.saveAll(arr);
        }

        if(countRoles == 0){
            List<Permission> allPermissions = this.permissionRepository.findAll();

            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            adminRole.setDescription("Bo may la nhat");
            adminRole.setActive(true);
            adminRole.setPermissions(allPermissions);

            this.roleRepository.save(adminRole);
        }

        if (countUsers == 0) {
            User adminUser = new User();
            adminUser.setEmail("admin@gmail.com");
            adminUser.setAddress("kt");
            adminUser.setName("Tao la bo cua tui may");
            adminUser.setPassword(this.passwordEncoder.encode("123456"));

            Role adminRole = this.roleRepository.findByName("ADMIN");
            if (adminRole != null) {
                adminUser.setRole(adminRole);
            }

            this.userRepository.save(adminUser);
        }

        if (countPermissions > 0 && countRoles > 0 && countUsers > 0) {
            System.out.println(">>> SKIP INIT DATABASE ~ ALREADY HAVE DATA...");
        } else
            System.out.println(">>> END INIT DATABASE");
    }
}
