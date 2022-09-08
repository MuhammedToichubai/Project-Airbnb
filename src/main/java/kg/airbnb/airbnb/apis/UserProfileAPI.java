package kg.airbnb.airbnb.apis;

import kg.airbnb.airbnb.dto.requests.BlockBookDateRequest;
import kg.airbnb.airbnb.dto.requests.BookRequest;
import kg.airbnb.airbnb.dto.responses.BookedResponse;
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

    @PostMapping("/booking")
    public Map<String, String> sendRequestToBook(@RequestBody BookRequest request) {
        return userService.requestToBook(request);
    }

    @DeleteMapping("/deleteMyBooking")
    public Map<String, String> deleteRequestToBook(@RequestParam Long userId, @RequestParam Long bookingId) {
        return userService.deleteRequestToBook(userId, bookingId);
    }

    @PutMapping("/update")
    public Map<String, String> updateRequestToBook(@RequestBody BookRequest request) {
        return userService.updateRequestToBook(request);
    }

    // User
    @PostMapping("/blockDates")
    public Map<String, String> blockDate(@RequestBody BlockBookDateRequest request) {
        return userService.blockDateByUser(request);
    }

    @GetMapping("/myBookings")
    public List<BookingCardResponse> getMyBookings(@RequestParam Long userId) {
        return userService.findUsersBookings(userId);
    }

    @GetMapping("/announcementsBookings")
    public List<BookedResponse> getAnnouncementsBookings(@RequestParam Long userId, @RequestParam Long announcementId) {
        return userService.getAnnouncementsBookings(userId, announcementId);
    }

    @PutMapping("/acceptBooking")
    public Map<String, String> acceptBooking(@RequestBody BookRequest request) {
        return userService.acceptRequestToBook(request);
    }

    @PutMapping("/rejectBooking")
    public Map<String, String> rejectBooking(@RequestBody BookRequest request) {
        return userService.rejectRequestToBook(request);
    }
}
