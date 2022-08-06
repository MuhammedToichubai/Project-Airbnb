package kg.airbnb.airbnb.services;

import kg.airbnb.airbnb.dto.responses.UserProfileResponse;

public interface UserService {
    UserProfileResponse getUserBookingsAndAnnouncements();

}
