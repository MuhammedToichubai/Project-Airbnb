package kg.airbnb.airbnb;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
	public String greetingPage() {
		return "<h1>Welcome to Airbnb application!!!<h1/>";
	}
}