package kg.airbnb.airbnb.services;

import kg.airbnb.airbnb.dto.responses.UserAnnouncementResponse;
import kg.airbnb.airbnb.dto.responses.UserProfileAnnouncementResponse;
import kg.airbnb.airbnb.dto.responses.UserProfileBookingResponse;

public interface UserService {
    UserProfileBookingResponse getUserAllBookings();

    UserProfileAnnouncementResponse getAllUserAnnouncements();

    UserAnnouncementResponse findById(Long announcementId);
}
