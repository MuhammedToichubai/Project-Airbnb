package kg.airbnb.airbnb.apis;

import kg.airbnb.airbnb.dto.response.UserAnnouncementResponse;
import kg.airbnb.airbnb.dto.response.UserProfileAnnouncementResponse;
import kg.airbnb.airbnb.dto.response.UserProfileBookingResponse;
import kg.airbnb.airbnb.services.UserService;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/user/profile")
public class UserProfileAPI {

    private final UserService userService;

    public UserProfileAPI(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/announcement/bookings")
    public UserProfileBookingResponse getUserAllBookings() {
        return userService.getUserAllBookings();
    }

    @GetMapping("/my/announcements/")
    public UserProfileAnnouncementResponse getAllUserAnnouncements() {
        return userService.getAllUserAnnouncements();
    }

    @GetMapping("/announcement/find/{announcementId}")
    public UserAnnouncementResponse getByUserAnnouncement(@PathVariable Long announcementId){
        return userService.findById(announcementId);
    }
}
