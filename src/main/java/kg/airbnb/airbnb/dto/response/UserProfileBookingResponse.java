package kg.airbnb.airbnb.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserProfileBookingResponse {

    private String image;
    private String name;
    private String contact;
    private List<UserBookingsResponse> bookings;
}
