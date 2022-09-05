package kg.airbnb.airbnb.services.impl;

import kg.airbnb.airbnb.dto.responses.JwtResponse;
import kg.airbnb.airbnb.dto.requests.UserRegisterRequest;
import kg.airbnb.airbnb.enums.Role;
import kg.airbnb.airbnb.exceptions.AlreadyExistException;
import kg.airbnb.airbnb.models.auth.User;
import kg.airbnb.airbnb.repositories.UserRepository;
import kg.airbnb.airbnb.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
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
        log.info("sdfsfsfsfsfsfsfsfsfsssssssssssssssssssssssssssssss");
        String token = jwtUtils.generateToken(userRegisterRequest.getEmail());

        return new JwtResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                token,
                savedUser.getRole()
        );
    }
}
