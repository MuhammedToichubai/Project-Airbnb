package kg.airbnb.airbnb.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.airbnb.airbnb.dto.responses.BookingCardResponse;
import kg.airbnb.airbnb.dto.responses.FavoritesResponse;
import kg.airbnb.airbnb.dto.responses.MyAnnouncementsBookingRequestsResponse;
import kg.airbnb.airbnb.dto.responses.SimpleResponse;
import kg.airbnb.airbnb.dto.responses.UserProfileResponse;
import kg.airbnb.airbnb.services.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/profile")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Profile API", description = "This is API is for user profiles")
public class ProfileAPI {

    private final UserService userService;

    @Operation(summary = "User profile", description = "Any registered user can access their own profile")
    @GetMapping("bookings/my-announcements")
    public UserProfileResponse getUserBookingsAndAnnouncements() {
        return userService.getUserProfile();
    }

    @Operation(summary = "Favorite", description = "The user's favorite announcements")
    @GetMapping("favorite")
    public FavoritesResponse getUserFavoriteAnnouncements() {
        return userService.getUserFavoriteAnnouncements();
    }

    @Operation(summary = "My bookings", description = "Get all my bookings")
    @GetMapping("my-bookings")
    public List<BookingCardResponse> getMyBookings() {
        return userService.findUsersBookings();
    }

    @Operation(summary = "My booking requests", description = "Get all my booking requests")
    @GetMapping("my-booking-requests")
    public List<MyAnnouncementsBookingRequestsResponse> getMyBookingRequests() {
        return userService.findUsersRequests();
    }

    @Operation(summary = "Delete message", description = "Delete message from admin")
    @DeleteMapping("messages")
    public SimpleResponse deleteMessages() {
        return userService.deleteMessagesFromAdmin();
    }

}
