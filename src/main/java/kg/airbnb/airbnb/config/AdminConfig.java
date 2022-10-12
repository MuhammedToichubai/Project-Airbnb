//package kg.airbnb.airbnb.config;
//
//import kg.airbnb.airbnb.enums.Role;
//import kg.airbnb.airbnb.models.auth.User;
//import kg.airbnb.airbnb.repositories.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@RequiredArgsConstructor
//public class AdminConfig {
//
//    private final PasswordEncoder passwordEncoder;
//
//    @Bean
//    CommandLineRunner commandLineRunner(UserRepository userRepository) {
//        return args -> {
//            String password = "admin";
//            User user = new User(
//                    "Airbnb Admin",
//                    "admin@gmail.com",
//                    passwordEncoder.encode(password),
//                    Role.ADMIN
//            );
//            userRepository.save(user);
//        };
//    }
//}
