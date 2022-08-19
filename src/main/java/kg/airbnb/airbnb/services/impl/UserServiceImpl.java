package kg.airbnb.airbnb.services.impl;

import kg.airbnb.airbnb.dto.responses.UserProfileResponse;
import kg.airbnb.airbnb.exceptions.ForbiddenException;
import kg.airbnb.airbnb.mappers.UserProfileViewMapper;
import kg.airbnb.airbnb.models.auth.User;
import kg.airbnb.airbnb.repositories.UserRepository;
import kg.airbnb.airbnb.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserProfileViewMapper viewMapper;


    @Override
    public void removeFromLikedFeedbacks(Long feedbackId) {
        User currentUser = getAuthenticatedUser();
        currentUser.removeFromLikedFeedbacks(feedbackId);
        userRepository.save(currentUser);
    }

    @Override
    public void removeFromDisLikedFeedbacks(Long feedbackId) {
        User currentUser = getAuthenticatedUser();
        currentUser.removeFromDisLikedFeedbacks(feedbackId);
        userRepository.save(currentUser);
    }

    @Override
    public void addToLikedFeedbacks(Long feedbackId) {
        User currentUser = getAuthenticatedUser();
        currentUser.addToLikedFeedbacks(feedbackId);
        userRepository.save(currentUser);
    }

    @Override
    public boolean ifLikedFeedback(Long feedbackId) {
        return getAuthenticatedUser().getLikedFeedbacks().stream().anyMatch(likedFeedback -> likedFeedback.equals(feedbackId));
    }

    @Override
    public boolean ifDisLikedFeedback(Long feedbackId) {
        return getAuthenticatedUser().getDisLikedFeedbacks().stream()
                .anyMatch(disLikedFeedback -> disLikedFeedback.equals(feedbackId));
    }

    @Override
    public void addToDisLikedFeedbacks(Long feedbackId) {
        User currentUser = getAuthenticatedUser();
        currentUser.addToDisLikedFeedbacks(feedbackId);
        userRepository.save(currentUser);
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
                new ForbiddenException("An unregistered user cannot write comment for this announcement!"));
    }
}

