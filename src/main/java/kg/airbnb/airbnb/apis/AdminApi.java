package kg.airbnb.airbnb.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.airbnb.airbnb.dto.requests.AnnouncementRejectRequest;
import kg.airbnb.airbnb.dto.responses.*;
import kg.airbnb.airbnb.services.AnnouncementService;
import kg.airbnb.airbnb.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("api/admin")
@PreAuthorize("hasAuthority('ADMIN')")
@Tag(name = "API  for managing Admin")
public class AdminApi {

    private final AnnouncementService announcementService;

    private final UserServiceImpl userService;

    @Operation(summary = "Get all announcements", description = "Only admin can view all announcements")
    @GetMapping("/applications")
    public AdminPageApplicationsResponse getAllAnnouncements(@RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "15") int size) {
        return announcementService.getAllAnnouncementsAndSize(page, size);
    }

    @Operation(summary = "Find an announcement by id", description = "Only admin can find announcement by id")
    @GetMapping("/find/announcement/{announcementId}")
    public AdminPageApplicationsAnnouncementResponse findAnnouncementById(@PathVariable Long announcementId) {
        return announcementService.findAnnouncementById(announcementId);
    }

    @Operation(summary = "Accept announcement", description = "Only admin can accept newly created announcement")
    @PutMapping("/announcement/accept/{announcementId}")
    public SimpleResponse acceptAnnouncement(@PathVariable Long announcementId) {
        return announcementService.acceptAnnouncement(announcementId);
    }

    @Operation(summary = "Reject announcement", description = "Only admin can reject newly created announcement")
    @PutMapping("/announcement/reject/{announcementId}")
    public SimpleResponse rejectAnnouncement(@PathVariable Long announcementId, @RequestBody AnnouncementRejectRequest announcementRejectRequest) {
        return announcementService.rejectAnnouncement(announcementId, announcementRejectRequest);
    }

    @Operation(summary = "Delete announcement", description = "Only admin can delete announcements")
    @DeleteMapping("/announcement/delete/{announcementId}")
    public SimpleResponse deleteAnnouncement(@PathVariable Long announcementId, @RequestBody AnnouncementRejectRequest announcementRejectRequest) {
        return announcementService.deleteAnnouncement(announcementId, announcementRejectRequest);
    }

    @Operation(summary = "Delete users", description = "Only admin can delete users")
    @DeleteMapping("/delete/user/{id}")
    public SimpleResponse deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @Operation(summary = "Get all users", description = "Only admin can see all users")
    @GetMapping("/users")
    public List<UserResponse> getAllUser() {
        return userService.getAllUser();
    }
}
