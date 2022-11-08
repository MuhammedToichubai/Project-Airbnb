package kg.airbnb.airbnb.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import kg.airbnb.airbnb.dto.requests.BlockBookDateRequest;
import kg.airbnb.airbnb.dto.requests.BookRequest;
import kg.airbnb.airbnb.dto.requests.ChangeBookingsStatusRequest;
import kg.airbnb.airbnb.dto.requests.UpdateBookRequest;
import kg.airbnb.airbnb.dto.responses.BookedResponse;
import kg.airbnb.airbnb.dto.responses.ClosedDatesResponse;
import kg.airbnb.airbnb.db.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/bookings")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Booking API", description = "Booking endpoints")
public class BookingApi {

    private final UserService userService;

    @PostMapping
    public Map<String, String> sendRequestToBook(@RequestBody BookRequest request) {
        return userService.requestToBook(request);
    }

    @DeleteMapping
    public Map<String, String> deleteRequestToBook(@RequestParam Long bookingId) {
        return userService.deleteRequestToBook(bookingId);
    }

    @PutMapping
    public Map<String, String> updateRequestToBook(@RequestBody UpdateBookRequest request) {
        return userService.updateRequestToBook(request);
    }

    @PostMapping("block")
    public Map<String, String> blockDate(@RequestBody BlockBookDateRequest request) {
        return userService.blockDateByUser(request);
    }

    @GetMapping("show")
    public List<BookedResponse> getAcceptedAnnouncementsBookings(@RequestParam Long announcementId) {
        return userService.getAnnouncementsBookings(announcementId);
    }

    @PutMapping("accept")
    public Map<String, String> changeBookingsStatus(@RequestBody ChangeBookingsStatusRequest request) {
        return userService.changeBookingsStatus(request);
    }

    @GetMapping("closed-dates")
    public ClosedDatesResponse getClosedDates(@RequestParam Long announcementId) {
        return userService.getClosedDates(announcementId);
    }

}
