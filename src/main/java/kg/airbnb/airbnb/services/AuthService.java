package kg.airbnb.airbnb.services;

import kg.airbnb.airbnb.dto.JwtResponse;
import kg.airbnb.airbnb.dto.UserRegisterRequest;
import kg.airbnb.airbnb.enums.Role;
import kg.airbnb.airbnb.exceptions.AlreadyExistException;
import kg.airbnb.airbnb.models.auth.User;
import kg.airbnb.airbnb.repositories.UserRepository;
import kg.airbnb.airbnb.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public JwtResponse registerUser(UserRegisterRequest userRegisterRequest) {

        User user = new User(
//                userRegisterRequest.getFullName(),
                userRegisterRequest.getEmail()

        );

        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));

        if(userRepository.existsByEmail(userRegisterRequest.getEmail()))
            throw new AlreadyExistException("The email " + userRegisterRequest.getEmail() + " is already in use!");

        User savedUser = userRepository.save(user);
        String token = jwtUtils.generateToken(userRegisterRequest.getEmail());

        return new JwtResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                token,
                savedUser.getRole()
        );
    }
}
