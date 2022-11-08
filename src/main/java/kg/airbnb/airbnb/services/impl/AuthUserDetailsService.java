package kg.airbnb.airbnb.services.impl;

import kg.airbnb.airbnb.exceptions.NotFoundException;
import kg.airbnb.airbnb.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final kg.airbnb.airbnb.repositories.UserRepository userRepository;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new NotFoundException("User with " + email + " not found!");
        }
        return new AuthUserDetails(user.get());
    }

}
