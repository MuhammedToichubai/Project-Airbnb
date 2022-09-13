package kg.airbnb.airbnb.apis;

import kg.airbnb.airbnb.dto.requests.ChangeBookingsStatusRequest;
import kg.airbnb.airbnb.dto.requests.BlockBookDateRequest;
import kg.airbnb.airbnb.dto.requests.BookRequest;
import kg.airbnb.airbnb.dto.requests.UpdateBookRequest;
import kg.airbnb.airbnb.dto.responses.BookedResponse;
import kg.airbnb.airbnb.dto.responses.BookingCardResponse;
import kg.airbnb.airbnb.dto.responses.ClosedDatesResponse;
import kg.airbnb.airbnb.dto.responses.UserProfileResponse;
import kg.airbnb.airbnb.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("/user/profile")
public class UserProfileAPI {

    private final UserService userService;

    public UserProfileAPI(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/bookings/myAnnouncements")
    public UserProfileResponse getUserBookingsAndAnnouncements() {
        return userService.getUserBookingsAndAnnouncements();
    }

    @GetMapping("/myBookings")
    public List<BookingCardResponse> getMyBookings() {
        return userService.findUsersBookings();
    }
}
