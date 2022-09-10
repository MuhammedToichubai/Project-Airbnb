package kg.airbnb.airbnb.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.airbnb.airbnb.dto.responses.CountFavoritesResponse;
import kg.airbnb.airbnb.dto.responses.UserProfileResponse;
import kg.airbnb.airbnb.services.UserService;
import org.springframework.web.bind.annotation.*;


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
    public CountFavoritesResponse getUserFavoriteAnnouncements(){
       return userService.getUserFavoriteAnnouncements();
    }
}
