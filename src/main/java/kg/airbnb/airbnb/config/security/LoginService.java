package kg.airbnb.airbnb.config.security;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import kg.airbnb.airbnb.dto.requests.LoginRequest;
import kg.airbnb.airbnb.dto.requests.PhoneNumberRequest;
import kg.airbnb.airbnb.dto.responses.JwtResponse;
import kg.airbnb.airbnb.dto.responses.SimpleResponse;
import kg.airbnb.airbnb.enums.Role;
import kg.airbnb.airbnb.exceptions.BadRequestException;
import kg.airbnb.airbnb.exceptions.ForbiddenException;
import kg.airbnb.airbnb.exceptions.NotFoundException;
import kg.airbnb.airbnb.exceptions.WrongPasswordException;
import kg.airbnb.airbnb.models.auth.User;
import kg.airbnb.airbnb.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class LoginService {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    void init() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(
                new ClassPathResource("firebase/auth-368bd-firebase-adminsdk-6bey6-b79b2aa771.json").getInputStream());

        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .build();

        FirebaseApp firebaseApp = FirebaseApp.initializeApp(firebaseOptions);
    }

    public JwtResponse authenticate(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() ->
                new NotFoundException("User with email: " + loginRequest.getEmail() + " not found!"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new WrongPasswordException("Invalid password");
        }
        String token = jwtUtils.generateToken(user.getEmail());
        return new JwtResponse(
                user.getId(),
                user.getEmail(),
                token,
                user.getRole(),
                user.getPhoneNumber()
        );
    }

    public JwtResponse authenticateWithGoogle(String token) throws FirebaseAuthException {
        log.info("User started logging in with google");
        FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(token);

        User user;

        if (!userRepository.existsByEmail(firebaseToken.getEmail())) {
            User newUser = new User(
                    firebaseToken.getName(),
                    firebaseToken.getEmail(),
                    passwordEncoder.encode(firebaseToken.getEmail()),
                    Role.USER
            );
            newUser.setImage(firebaseToken.getPicture());
            user = userRepository.save(newUser);
            log.info("{} successfully logged in via google", newUser.getEmail());
        } else {
            user = userRepository.findByEmail(firebaseToken.getEmail()).orElseThrow(() ->
                    new NotFoundException("User with email: " + firebaseToken.getEmail() + " not found!"));
        }
        return new JwtResponse(
                user.getId(),
                user.getEmail(),
                jwtUtils.generateToken(user.getEmail()),
                user.getRole(),
                user.getPhoneNumber()
        );
    }

    @Transactional
    public SimpleResponse addPhoneNumber(PhoneNumberRequest phoneNumberRequest) {
        User currentUser = getAuthenticatedUser();
        if (phoneNumberRequest.getPhoneNumber().length() == 9) {
            currentUser.setPhoneNumber("+996 " + phoneNumberRequest.getPhoneNumber());
        } else {
            throw new BadRequestException("Invalid phone number, too long!");
        }
        return new SimpleResponse("SAVE", "Phone number added!"
        );
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() ->
                new ForbiddenException("An unregistered user cannot post an ad!"));
    }

}
