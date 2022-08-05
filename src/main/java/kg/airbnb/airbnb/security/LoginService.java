package kg.airbnb.airbnb.security;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import kg.airbnb.airbnb.dto.JwtResponse;
import kg.airbnb.airbnb.dto.LoginRequest;
import kg.airbnb.airbnb.enums.Role;
import kg.airbnb.airbnb.exceptions.NotFoundException;
import kg.airbnb.airbnb.exceptions.WrongPasswordException;
import kg.airbnb.airbnb.models.auth.User;
import kg.airbnb.airbnb.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    void init() throws IOException {

        GoogleCredentials googleCredentials =
                GoogleCredentials.fromStream(new ClassPathResource("firebase/auth-368bd-firebase-adminsdk-6bey6-b79b2aa771.json").getInputStream());

        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .build();

        FirebaseApp firebaseApp = FirebaseApp.initializeApp(firebaseOptions);
    }

    public JwtResponse authenticate(LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new NotFoundException(
                        "user with email: " + loginRequest.getEmail() + " not found!"
                ));


        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new WrongPasswordException(
                    "invalid password"
            );
        }

        String token = jwtUtils.generateToken(user.getEmail());

        return new JwtResponse(
                user.getId(),
                user.getEmail(),
                token,
                user.getRole()
        );
    }

    public JwtResponse authenticateWithGoogle(String token) throws FirebaseAuthException {

        FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(token);

        User user = null;

        if (!userRepository.existsByEmail(firebaseToken.getEmail())) {
            User newUser = new User(
                    firebaseToken.getName(),
                    firebaseToken.getEmail(),
                    passwordEncoder.encode(firebaseToken.getEmail()),
                    Role.USER
            );

            newUser.setImage(firebaseToken.getPicture());

            user = userRepository.save(newUser);
        } else {
            user = userRepository.findByEmail(firebaseToken.getEmail()).get();
        }

        return new JwtResponse(
                user.getId(),
                user.getEmail(),
                jwtUtils.generateToken(user.getEmail()),
                user.getRole()
        );
    }
}
