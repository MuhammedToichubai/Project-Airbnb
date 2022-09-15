package kg.airbnb.airbnb.config;

import kg.airbnb.airbnb.enums.Role;
import kg.airbnb.airbnb.models.auth.User;
import kg.airbnb.airbnb.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminConfig {


    private final PasswordEncoder passwordEncoder;

    public AdminConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            String password = "admin";
            User user = new User(
                    "Airbnb Admin",
                    "admin@gmail.com",
                    "+996 700 000 000",
                    passwordEncoder.encode(password),
                    Role.ADMIN
            );

            userRepository.save(user);
        };
    }
}
