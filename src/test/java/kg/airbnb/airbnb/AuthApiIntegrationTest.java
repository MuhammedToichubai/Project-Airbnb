package kg.airbnb.airbnb;

import kg.airbnb.airbnb.dto.requests.LoginRequest;
import kg.airbnb.airbnb.dto.responses.JwtResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Slf4j
public class AuthApiIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldWork(){
        ResponseEntity<JwtResponse> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/authentication",
                new LoginRequest("testEmail@gmail.com", "testPassword"),
                JwtResponse.class

        );
        log.warn("response status: {}", response.getStatusCode());

        JwtResponse responseBody = response.getBody();

        System.out.println("responseBody = " + responseBody);


    }
}
