package kg.airbnb.airbnb.services.impl;

import kg.airbnb.airbnb.dto.responses.*;
import kg.airbnb.airbnb.enums.Role;
import kg.airbnb.airbnb.exceptions.ForbiddenException;
import kg.airbnb.airbnb.exceptions.NotFoundException;
import kg.airbnb.airbnb.mappers.announcement.AnnouncementViewMapper;
import kg.airbnb.airbnb.mappers.user.UserProfileViewMapper;
import kg.airbnb.airbnb.models.Announcement;
import kg.airbnb.airbnb.models.auth.User;
import kg.airbnb.airbnb.repositories.AnnouncementRepository;
import kg.airbnb.airbnb.repositories.UserRepository;
import kg.airbnb.airbnb.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserProfileViewMapper userProfileViewMapper;
    private final AnnouncementRepository announcementRepository;
    private final AnnouncementViewMapper announcementViewMapper;

    @Override
    public void removeFromLikedFeedbacks(Long feedbackId) {
        User currentUser = getAuthenticatedUser();
        currentUser.removeFromLikedFeedbacks(feedbackId);
        userRepository.save(currentUser);
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
    public boolean ifLikedAnnouncement(Long announcementId) {
        return getAuthenticatedUser().getLikedAnnouncements().stream().anyMatch(likedAnnouncement -> likedAnnouncement.equals(announcementId));
    }

    @Override
    public boolean ifBookmarkAnnouncement(Long announcementId) {
        return getAuthenticatedUser().getBookmarkAnnouncements().stream().anyMatch(bookmarkAnnouncement -> bookmarkAnnouncement.equals(announcementId));
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
        return getAuthenticatedUser().getDisLikedFeedbacks().stream().anyMatch(disLikedFeedback -> disLikedFeedback.equals(feedbackId));
    }

    @Override
    public void addToDisLikedFeedbacks(Long feedbackId) {
        User currentUser = getAuthenticatedUser();
        currentUser.addToDisLikedFeedbacks(feedbackId);
        userRepository.save(currentUser);
    }

    @Override
    public UserProfileResponse getUserProfile() {
        User user = getAuthenticatedUser();
        return userProfileViewMapper.entityToDto(user);
    }

    @Override
    public UserProfileResponse getUserProfile(Long userId) {
        User currentUser = getAuthenticatedUser();
        UserProfileResponse userProfileResponse;
        if (currentUser.getRole().equals(Role.ADMIN)){
            User user = userRepository.findById(userId).orElseThrow(() ->
                    new NotFoundException("User with " + userId + " not found !"));
            if (user.getRole().equals(Role.ADMIN)){
                throw new NotFoundException("User with " + userId + " not found !");
            }
            else {
                userProfileResponse = userProfileViewMapper.entityToDto(user);
            }
        }else {
            throw new ForbiddenException("Only admin can access this page!");
        }
        return userProfileResponse;
    }

    @Override
    public List<FavoriteAnnouncementResponse> userFavoriteAnnouncements() {
        User currentUser = getAuthenticatedUser();

        Set<Long> bookmarkAnnouncements = currentUser.getBookmarkAnnouncements();

        List<Announcement> favoriteAnnouncement = new ArrayList<>();

        for (Long aLong : bookmarkAnnouncements) {
                Announcement announcement = getAnnouncementFindId(aLong);
                favoriteAnnouncement.add(announcement);
            }

            List<FavoriteAnnouncementResponse> responseList = new ArrayList<>();

        for (Announcement announcement : favoriteAnnouncement) {
                FavoriteAnnouncementResponse response = new FavoriteAnnouncementResponse(
                        announcement.getId(),
                        announcement.getImages(),
                        announcement.getPrice(),
                        announcementViewMapper.calculateRating(announcement),
                        announcement.getTitle(),
                        announcement.getLocation().getAddress()+ ", "
                                +announcement.getLocation().getCity()+", "
                                +announcement.getLocation().getRegion().getRegionName(),
                        announcement.getMaxGuests(),
                        announcement.getLike(),
                        announcement.getBookmark(),
                        announcement.getStatus()
                );
                responseList.add(response);
            }

        return responseList;
    }

    @Override
    public FavoritesResponse getUserFavoriteAnnouncements() {
        List<FavoriteAnnouncementResponse> responseList = userFavoriteAnnouncements();
        FavoritesResponse response = new FavoritesResponse(
                responseList.size(),
                responseList
        );
        return response;
    }

    private Announcement getAnnouncementFindId(Long announcementId){
        return announcementRepository.findById(announcementId).orElseThrow(() ->
                new NotFoundException("Announcement with "+ announcementId+ " not found!")
                );
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> new ForbiddenException("An unregistered user cannot write comment for this announcement!"));
    }

    public SimpleResponse deleteUser(Long userId) {

        User currentUser = getAuthenticatedUser();

        if (currentUser.getRole().equals(Role.ADMIN)) {
            User user = userRepository.findById(userId).orElseThrow(() ->
                    new NotFoundException("User with " + userId + "not found !"));

            if (user.getRole().equals(Role.ADMIN)) {
                throw new ForbiddenException("Admin cannot be deleted!");
            }

            userRepository.delete(user);
        }
        else {
            throw new ForbiddenException("Only admin can access this page!");

        }
           return new SimpleResponse(
                   "DELETE",
                   "User successfully deleted!"

           ) ;
    }

    public List<UserResponse> getAllUser() {
        User currentUser = getAuthenticatedUser();
        if (currentUser.getRole().equals(Role.ADMIN)) {
            return UserProfileViewMapper.viewFindAllUser(userRepository.findAllUsers());
        } else {
            throw new ForbiddenException("Only admin can access this page!");
        }
    }
}


