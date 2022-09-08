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

    @PostMapping("/booking")
    public Map<String, String> sendRequestToBook(@RequestBody BookRequest request) {
        return userService.requestToBook(request);
    }

    @DeleteMapping("/deleteMyBooking")
    public Map<String, String> deleteRequestToBook(@RequestParam Long userId, @RequestParam Long bookingId) {
        return userService.deleteRequestToBook(userId, bookingId);
    }

    @PutMapping("/changeBookingDates")
    public Map<String, String> updateRequestToBook(@RequestBody UpdateBookRequest request) {
        return userService.updateRequestToBook(request);
    }

    @PostMapping("/blockDatesToBook")
    public Map<String, String> blockDate(@RequestBody BlockBookDateRequest request) {
        return userService.blockDateByUser(request);
    }

    @GetMapping("/myBookings")
    public List<BookingCardResponse> getMyBookings(@RequestParam Long userId) {
        return userService.findUsersBookings(userId);
    }

    @GetMapping("/announcementsBookings")
    public List<BookedResponse> getAnnouncementsBookings(@RequestParam Long vendorId, @RequestParam Long announcementId) {
        return userService.getAnnouncementsBookings(vendorId, announcementId);
    }

    @PutMapping("/changeBookingsStatus")
    public Map<String, String> changeBookingsStatus(@RequestBody ChangeBookingsStatusRequest request) {
        return userService.changeBookingsStatus(request);
    }

    @GetMapping("/getClosedDates")
    public ClosedDatesResponse getClosedDates(@RequestParam Long vendorId, @RequestParam Long announcementId) {
        return userService.getClosedDates(vendorId, announcementId);
    }
}
