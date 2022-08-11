package kg.airbnb.airbnb.dto.responses;

import kg.airbnb.airbnb.models.auth.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.util.List;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String fullName;
    private String email;
    private int booking;
    private int announcement;

    public UserResponse(User user) {
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.booking = user.getBookings().size();
        this.announcement = user.getAnnouncements().size();
    }
}
