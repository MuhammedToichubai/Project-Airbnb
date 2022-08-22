package kg.airbnb.airbnb.services.impl;

import kg.airbnb.airbnb.dto.responses.*;
import kg.airbnb.airbnb.enums.Role;
import kg.airbnb.airbnb.exceptions.ForbiddenException;
import kg.airbnb.airbnb.mappers.UserProfileViewMapper;
import kg.airbnb.airbnb.mappers.announcement.AnnouncementViewMapper;
import kg.airbnb.airbnb.models.auth.User;
import kg.airbnb.airbnb.repositories.AnnouncementRepository;
import kg.airbnb.airbnb.repositories.UserRepository;
import kg.airbnb.airbnb.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserProfileViewMapper viewMapper;
    private final AnnouncementViewMapper announcementViewMapper;
    private final AnnouncementRepository announcementRepository;

    public UserServiceImpl(UserRepository userRepository, UserProfileViewMapper viewMapper
            , AnnouncementViewMapper announcementViewMapper
            , AnnouncementRepository announcementRepository) {
        this.userRepository = userRepository;
        this.viewMapper = viewMapper;
        this.announcementViewMapper = announcementViewMapper;
        this.announcementRepository = announcementRepository;
    }

    @Override
    public UserProfileResponse getUserBookingsAndAnnouncements() {

        User user = getAuthenticatedUser();
        return viewMapper.entityToDto(user);
    }

    @Override
    public void removeFromLikedAnnouncements(Long announcementId) {
        User currentUser = getAuthenticatedUser();
        currentUser.removeFromLikedAnnouncements(announcementId);
        userRepository.save(currentUser);
    }

    @Override
    public void addToLikedAnnouncements(Long announcementId) {
        User currentUser = getAuthenticatedUser();
        currentUser.addToLikedAnnouncements(announcementId);
        userRepository.save(currentUser);
    }

    @Override
    public boolean ifLikedAnnouncement(Long announcementId){
        return getAuthenticatedUser().getLikedAnnouncements().stream()
                .anyMatch(likedAnnouncement -> likedAnnouncement.equals(announcementId));
    }

    @Override
    public boolean ifBookmarkAnnouncement(Long announcementId){
        return getAuthenticatedUser().getBookmarkAnnouncements().stream()
                .anyMatch(bookmarkAnnouncement -> bookmarkAnnouncement.equals(announcementId));
    }

    @Override
    public void addAnnouncementToHistory(Long announcementId) {
        User currentUser = getAuthenticatedUser();
        currentUser.addToAnnouncementHistory(announcementId);
        userRepository.save(currentUser);
    }

    @Override
    public void removeFromBookmarkAnnouncements(Long announcementId) {
        User currentUser = getAuthenticatedUser();
        currentUser.removeFromBookmarkAnnouncement(announcementId);
        userRepository.save(currentUser);
    }

    @Override
    public void addToBookmarkAnnouncements(Long announcementId) {
        User currentUser = getAuthenticatedUser();
        currentUser.addToBookmarkAnnouncements(announcementId);
        userRepository.save(currentUser);
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() ->
                new ForbiddenException("An unregistered user cannot post an ad !"));
    }

    public SimpleResponse deleteUser(Long id) {
        User users = getAuthenticatedUser();
        if (users.getRole().equals(Role.ADMIN)) {
            User user = userRepository.findById(id).get();
            userRepository.delete(user);
            return new SimpleResponse("Пользователь успешно удалён!");
        } else {
            throw new ForbiddenException("Only admin can access this page!");
        }
    }

    public List<UserResponse> getAllUser() {
        User user = getAuthenticatedUser();
        if (user.getRole().equals(Role.ADMIN)) {
            return UserProfileViewMapper.viewFindAllUser(userRepository.findAll());
        } else {
            throw new ForbiddenException("Only admin can access this page!");
        }
    }
}
