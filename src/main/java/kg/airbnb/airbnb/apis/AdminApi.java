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
    public AdminPageApplicationsResponse getAllAnnouncements(@RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "15") int size) {
        return announcementService.getAllAnnouncementsAndSize(page, size);
    }

    @GetMapping("/find/announcement/{announcementId}")
    public AdminPageApplicationsAnnouncementResponse findAnnouncementById(@PathVariable Long announcementId) {
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
}
