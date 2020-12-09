package uni.finalproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import uni.finalproject.models.user.Role;
import uni.finalproject.models.user.User;
import uni.finalproject.models.user.UserRoles;
import uni.finalproject.repository.user.RoleRepository;
import uni.finalproject.repository.user.UserRepository;

import java.util.Arrays;

@SpringBootApplication
public class FinalApplication {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(FinalApplication.class, args);
    }

    @Bean
    CommandLineRunner init(RoleRepository roleRepository, UserRepository userRepository) {

        return args -> {

            Role adminRole = roleRepository.findByRole(UserRoles.ADMIN);
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setRole(UserRoles.ADMIN);
                roleRepository.save(adminRole);
            }

            User admin = userRepository.findByEmail("admin@local.com");
            if (admin == null) {
                admin = new User()
                        .setEmail("admin@local.com")
                        .setPassword(bCryptPasswordEncoder.encode("123123"))
                        .setFirstName("Vitali")
                        .setLastName("Atias")
                        .setMobileNumber("111111")
                        .setRoles(Arrays.asList(adminRole));
                userRepository.save(admin);
            }
        };
    }

}
