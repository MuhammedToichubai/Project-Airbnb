package kg.airbnb.airbnb.services;

import kg.airbnb.airbnb.dto.responses.UserProfileResponse;

public interface UserService {
    UserProfileResponse getUserBookingsAndAnnouncements();

    void removeFromLikedAnnouncements(Long announcementId);
    void addToLikedAnnouncements(Long announcementId);
    void removeFromBookmarkAnnouncements(Long announcementId);
    void addToBookmarkAnnouncements(Long announcementId);
}
