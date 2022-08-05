package kg.airbnb.airbnb.services;

import kg.airbnb.airbnb.dto.response.UserAnnouncementResponse;
import kg.airbnb.airbnb.dto.response.UserProfileAnnouncementResponse;
import kg.airbnb.airbnb.dto.response.UserProfileBookingResponse;

public interface UserService {
    UserProfileBookingResponse getUserAllBookings();

    UserProfileAnnouncementResponse getAllUserAnnouncements();

    UserAnnouncementResponse findById(Long announcementId);
}
