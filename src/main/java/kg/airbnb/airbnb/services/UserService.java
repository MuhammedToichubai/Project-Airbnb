package kg.airbnb.airbnb.services;

import kg.airbnb.airbnb.dto.responses.UserProfileResponse;
import kg.airbnb.airbnb.models.Announcement;

public interface UserService {
    UserProfileResponse getUserBookingsAndAnnouncements();

    void removeFromLikedAnnouncements(Long announcementId);
    void addToLikedAnnouncements(Long announcementId);
    void removeFromBookmarkAnnouncements(Long announcementId);
    void addToBookmarkAnnouncements(Long announcementId);
    boolean ifLikedAnnouncement(Long announcementId);
    boolean ifBookmarkAnnouncement(Long announcementId);
    void addAnnouncementToHistory(Announcement savedAnnouncement);
}
