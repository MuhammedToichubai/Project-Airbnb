package kg.airbnb.airbnb.apis;

import kg.airbnb.airbnb.dto.requests.BlockBookDateRequest;
import kg.airbnb.airbnb.dto.requests.BookRequest;
import kg.airbnb.airbnb.dto.responses.BookingCardResponse;
import kg.airbnb.airbnb.dto.responses.UserProfileResponse;
import kg.airbnb.airbnb.services.UserService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/book")
    public Map<String, String> sendRequestToBook(@RequestBody BookRequest request) {
        return userService.requestToBook(request);
    }


    // User
    @PostMapping("/block")
    public Map<String, String> blockDate(@RequestBody BlockBookDateRequest request) {
        return userService.blockDateByUser(request);
    }

    @GetMapping("/myBookings")
    public List<BookingCardResponse> getMyBookings(@RequestParam Long userId) {
        return userService.findUsersBookings(userId);
    }
}
