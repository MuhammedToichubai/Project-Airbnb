package kg.airbnb.airbnb.services;

import kg.airbnb.airbnb.dto.responses.FavoritesResponse;
import kg.airbnb.airbnb.dto.responses.FavoriteAnnouncementResponse;
import kg.airbnb.airbnb.dto.responses.UserProfileResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    void removeFromLikedAnnouncements(Long announcementId);

    void addToLikedAnnouncements(Long announcementId);

    void removeFromBookmarkAnnouncements(Long announcementId);

    void addToBookmarkAnnouncements(Long announcementId);

    boolean ifLikedAnnouncement(Long announcementId);

    boolean ifBookmarkAnnouncement(Long announcementId);

    void removeFromLikedFeedbacks(Long feedbackId);

    void removeFromDisLikedFeedbacks(Long feedbackId);

    void addToLikedFeedbacks(Long feedbackId);

    boolean ifLikedFeedback(Long feedbackId);

    boolean ifDisLikedFeedback(Long feedbackId);

    void addToDisLikedFeedbacks(Long feedbackId);

    UserProfileResponse getUserProfile();

    UserProfileResponse getUserProfile(Long userId);

    List<FavoriteAnnouncementResponse> userFavoriteAnnouncements();

    FavoritesResponse getUserFavoriteAnnouncements();

}

