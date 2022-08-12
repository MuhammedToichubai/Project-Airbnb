package kg.airbnb.airbnb.apis;

import kg.airbnb.airbnb.dto.requests.AnnouncementRejectRequest;
import kg.airbnb.airbnb.dto.responses.*;
import kg.airbnb.airbnb.services.AnnouncementService;
import kg.airbnb.airbnb.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("api/admin")
public class AdminApi {

    private final AnnouncementService announcementService;

    private final UserServiceImpl userService;

    @GetMapping("/applications")
    public List<AdminPageAnnouncementResponse> getAllAnnouncements() {
        return announcementService.getAllAnnouncements();
    }

    @GetMapping("/find/announcement/{announcementId}")
    public AdminPageAnnouncementResponse findAnnouncementById(@PathVariable Long announcementId) {
        return announcementService.findAnnouncementById(announcementId);
    }

    @PutMapping("/announcement/accept/{announcementId}")
    public SimpleResponse acceptAnnouncement(@PathVariable Long announcementId) {
        return announcementService.acceptAnnouncement(announcementId);
    }

    @PutMapping("/announcement/reject/{announcementId}")
    public SimpleResponse rejectAnnouncement(@PathVariable Long announcementId, @RequestBody AnnouncementRejectRequest announcementRejectRequest) {
        return announcementService.rejectAnnouncement(announcementId, announcementRejectRequest);
    }

    @DeleteMapping("/announcement/delete/{announcementId}")
    public SimpleResponse deleteAnnouncement(@PathVariable Long announcementId, @RequestBody AnnouncementRejectRequest announcementRejectRequest) {
        return announcementService.deleteAnnouncement(announcementId, announcementRejectRequest);
    }

    @DeleteMapping("/delete/user/{id}")
    public SimpleResponse deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/bookings/{userId}")
    public List<UserBookingsResponse> getAllBookingsFromUser(@PathVariable Long userId) {
        return userService.getAllBookings(userId);
    }
    @GetMapping("/announcement/{userId}")
    public List<UserAnnouncementResponse> getAllAnnouncementFromUser(@PathVariable Long userId){
        return userService.getAllAnnouncements(userId);
    }
    @GetMapping("/innerPage/{announcId}")
    public AnnouncementInnerPageResponse getInnerPageAnnouncementAdmin(@PathVariable Long announcId){
        return userService.getInnerPageAnnouncement(announcId);
    }

}
