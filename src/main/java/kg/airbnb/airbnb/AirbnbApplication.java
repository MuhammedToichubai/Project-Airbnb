package kg.airbnb.airbnb;

import kg.airbnb.airbnb.models.auth.User;
import kg.airbnb.airbnb.models.enums.Role;
import kg.airbnb.airbnb.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@SpringBootApplication
public class AirbnbApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirbnbApplication.class, args);
		System.out.println("Welcome colleagues, project name is Airbnb!");
	}

	@GetMapping("/")
	public String greetingPage(){
		return "<h1>Welcome to Airbnb application!!!<h1/>";
	}
    @Bean
	CommandLineRunner runner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            User user = new User();
			user.setFullName("Beksultan");
            user.setEmail("Beks@gmail.com");
            user.setRole(Role.ADMIN);
            user.setPassword(passwordEncoder.encode("1234"));
            userRepository.save(user);
        };
    }
}
