package kg.airbnb.airbnb.services.impl;

import kg.airbnb.airbnb.dto.responses.UserProfileResponse;
import kg.airbnb.airbnb.exceptions.ForbiddenException;
import kg.airbnb.airbnb.mappers.UserProfileViewMapper;
import kg.airbnb.airbnb.models.auth.User;
import kg.airbnb.airbnb.repositories.UserRepository;
import kg.airbnb.airbnb.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserProfileViewMapper viewMapper;

    public UserServiceImpl(UserRepository userRepository, UserProfileViewMapper viewMapper) {
        this.userRepository = userRepository;
        this.viewMapper = viewMapper;

    }

    @Override
    public UserProfileResponse getUserBookingsAndAnnouncements() {

        User user = getAuthenticatedUser();
        return viewMapper.entityToDto(user);
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() ->
                new ForbiddenException("An unregistered user cannot post an ad !"));
    }
}
