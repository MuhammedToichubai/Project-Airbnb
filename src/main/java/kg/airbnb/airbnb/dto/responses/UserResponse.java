package kg.airbnb.airbnb.dto.responses;

import kg.airbnb.airbnb.db.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private int booking;
    private int announcement;

    public UserResponse(User user) {
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.booking = user.getBookings().size();
        this.announcement = user.getAnnouncements().size();
    }

}
