package kg.airbnb.airbnb.services.impl;

import kg.airbnb.airbnb.exceptions.ForbiddenException;
import kg.airbnb.airbnb.models.auth.User;
import kg.airbnb.airbnb.repositories.UserRepository;
import kg.airbnb.airbnb.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Override
    public boolean ifLikedFeedback(Long feedbackId){
        return getAuthenticatedUser().getLikedFeedbacks().stream().
    }

    @Override
    public boolean ifDisLikedFeedback(Long feedbackId) {
        return false;
    }

    @Override
    public void removeFromLikedFeedbacks(Long feedbackId) {

    }

    @Override
    public void removeFromDisLikedFeedbacks(Long feedbackId) {

    }

    @Override
    public void addToLikedFeedbacks(Long feedbackId) {

    }


    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() ->
                new ForbiddenException("An unregistered user cannot write comment for this announcement!"));
    }
}
