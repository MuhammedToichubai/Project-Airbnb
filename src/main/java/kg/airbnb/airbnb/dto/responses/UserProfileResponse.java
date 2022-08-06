package kg.airbnb.airbnb.dto.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserProfileResponse {

    private String image;
    private String name;
    private String contact;
    private List<UserBookingsResponse> bookings;
    private List<UserAnnouncementResponse> announcements;
}
