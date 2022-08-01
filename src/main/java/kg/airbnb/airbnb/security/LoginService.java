package kg.airbnb.airbnb.security;

import kg.airbnb.airbnb.dto.JwtResponse;
import kg.airbnb.airbnb.dto.LoginRequest;
import kg.airbnb.airbnb.exceptions.NotFoundException;
import kg.airbnb.airbnb.exceptions.WrongPasswordException;
import kg.airbnb.airbnb.models.auth.User;
import kg.airbnb.airbnb.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {



    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

}
