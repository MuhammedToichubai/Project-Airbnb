package kg.airbnb.airbnb.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.airbnb.airbnb.dto.requests.PhoneNumberRequest;
import kg.airbnb.airbnb.dto.responses.BookingCardResponse;
import kg.airbnb.airbnb.dto.responses.FavoritesResponse;
import kg.airbnb.airbnb.dto.responses.SimpleResponse;
import kg.airbnb.airbnb.dto.responses.UserProfileResponse;
import kg.airbnb.airbnb.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/user/profile")
@Tag(name = "This is API is for User Profile")
public class UserProfileAPI {

    private final UserService userService;

    public UserProfileAPI(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "User profile", description = "Any registered user can access their own profile")
    @GetMapping("/bookings/myAnnouncements")
    public UserProfileResponse getUserBookingsAndAnnouncements() {
        return userService.getUserProfile();
    }

    @Operation(summary = "Favorite", description = "The user's favorite announcements")
    @GetMapping("/favorite")
    public FavoritesResponse getUserFavoriteAnnouncements(){
       return userService.getUserFavoriteAnnouncements();
    }

    @GetMapping("/myBookings")
    public List<BookingCardResponse> getMyBookings() {
        return userService.findUsersBookings();
    }
    
    @Operation(summary = "Delete message from admin")
    @GetMapping("/delete/messages")
    public SimpleResponse deleteMessages(){
      return   userService.deleteMessagesFromAdmin();
    }

    @PostMapping("/update/phoneNumber")
    public SimpleResponse updatePhoneNumber(@RequestBody PhoneNumberRequest request){
        return userService.updatePhoneNumber(request);
    }
}
