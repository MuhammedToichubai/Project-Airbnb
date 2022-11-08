package kg.airbnb.airbnb.db.service.impl;

import kg.airbnb.airbnb.db.repositories.UserRepository;
import kg.airbnb.airbnb.exceptions.NotFoundException;
import kg.airbnb.airbnb.db.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new NotFoundException("User with " + email + " not found!");
        }
        return new AuthUserDetails(user.get());
    }

}
