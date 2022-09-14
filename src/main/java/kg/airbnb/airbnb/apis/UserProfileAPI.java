package kg.airbnb.airbnb.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.airbnb.airbnb.dto.responses.FavoritesResponse;
import kg.airbnb.airbnb.dto.responses.UserResponse;
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
    public UserResponse.UserProfileResponse getUserBookingsAndAnnouncements() {
        return userService.getUserProfile();
    }

    @Operation(summary = "Favorite", description = "The user's favorite announcements")
    @GetMapping("/favorite")
    public FavoritesResponse getUserFavoriteAnnouncements(){
       return userService.getUserFavoriteAnnouncements();
    }
}
