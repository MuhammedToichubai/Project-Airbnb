package kg.airbnb.airbnb.apis;

import kg.airbnb.airbnb.dto.requests.BlockBookDateRequest;
import kg.airbnb.airbnb.dto.requests.BookRequest;
import kg.airbnb.airbnb.dto.requests.ChangeBookingsStatusRequest;
import kg.airbnb.airbnb.dto.requests.UpdateBookRequest;
import kg.airbnb.airbnb.dto.responses.BookedResponse;
import kg.airbnb.airbnb.dto.responses.ClosedDatesResponse;
import kg.airbnb.airbnb.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/announcements/bookings")
@CrossOrigin
public class BookingApi {

    private final UserService userService;

    @PostMapping("/create")
    public Map<String, String> sendRequestToBook(@RequestBody BookRequest request) {
        return userService.requestToBook(request);
    }

    @DeleteMapping("/delete")
    public Map<String, String> deleteRequestToBook(@RequestParam Long bookingId) {
        return userService.deleteRequestToBook(bookingId);
    }

    @PutMapping("/update")
    public Map<String, String> updateRequestToBook(@RequestBody UpdateBookRequest request) {
        return userService.updateRequestToBook(request);
    }

    @PostMapping("/block")
    public Map<String, String> blockDate(@RequestBody BlockBookDateRequest request) {
        return userService.blockDateByUser(request);
    }

    @GetMapping("/show")
    public List<BookedResponse> getAcceptedAnnouncementsBookings(@RequestParam Long announcementId) {
        return userService.getAnnouncementsBookings(announcementId);
    }

    @PutMapping("/accept")
    public Map<String, String> changeBookingsStatus(@RequestBody ChangeBookingsStatusRequest request) {
        return userService.changeBookingsStatus(request);
    }

    @GetMapping("/getClosedDates")
    public ClosedDatesResponse getClosedDates(@RequestParam Long announcementId) {
        return userService.getClosedDates(announcementId);
    }
}
