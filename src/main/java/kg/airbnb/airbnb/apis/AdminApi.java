package kg.airbnb.airbnb.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.airbnb.airbnb.dto.requests.AdminMessageRequest;
import kg.airbnb.airbnb.dto.responses.AdminPageAllHousingResponses;
import kg.airbnb.airbnb.dto.responses.AdminPageApplicationsAnnouncementResponse;
import kg.airbnb.airbnb.dto.responses.AdminPageApplicationsResponse;
import kg.airbnb.airbnb.dto.responses.SimpleResponse;
import kg.airbnb.airbnb.dto.responses.UserProfileResponse;
import kg.airbnb.airbnb.dto.responses.UserResponse;
import kg.airbnb.airbnb.enums.BookedType;
import kg.airbnb.airbnb.enums.Kind;
import kg.airbnb.airbnb.enums.PriceType;
import kg.airbnb.airbnb.enums.Type;
import kg.airbnb.airbnb.services.AnnouncementService;
import kg.airbnb.airbnb.services.UserService;
import kg.airbnb.airbnb.services.impl.UserServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Admin API", description = "All \"Admin\" endpoints")
public class AdminApi {

    private final AnnouncementService announcementService;
    private final UserServiceImpl userService;
    private final UserService service;

    @Operation(summary = "Get all announcements on moderation", description = "Only admin can view all announcements")
    @GetMapping("applications")
    public AdminPageApplicationsResponse getAllAnnouncements(@RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "15") int size) {
        return announcementService.getAllAnnouncementsAndSize(page, size);
    }

    @Operation(summary = "Find an announcement by id", description = "Only admin can find announcement by id")
    @GetMapping("announcement/{id}")
    public AdminPageApplicationsAnnouncementResponse findAnnouncementById(@PathVariable Long id) {
        return announcementService.findAnnouncementById(id);
    }

    @Operation(summary = "Accept announcement", description = "Only admin can accept newly created announcement")
    @PutMapping("accept/announcement/{id}")
    public SimpleResponse acceptAnnouncement(@PathVariable Long id) {
        return announcementService.acceptAnnouncement(id);
    }

    @Operation(summary = "Reject announcement", description = "Only admin can reject newly created announcement")
    @PutMapping("reject/announcement/{id}")
    public SimpleResponse rejectAnnouncement(@PathVariable Long id, @RequestBody AdminMessageRequest request) {
        return announcementService.rejectAnnouncement(id, request);
    }

    @Operation(summary = "Block announcement", description = "Only admin can block announcement")
    @PutMapping("block/announcement/{id}")
    public SimpleResponse blockAnnouncement(@PathVariable Long id, @RequestBody AdminMessageRequest request) {
        return announcementService.blockAnnouncement(id, request);
    }

    @Operation(summary = "Unblock announcement", description = "Only admin can unblock announcement")
    @PutMapping("unblock/announcement/{id}")
    public SimpleResponse unBlockAnnouncement(@PathVariable Long id, @RequestBody AdminMessageRequest request) {
        return announcementService.unBlockAnnouncement(id, request);
    }

    @Operation(summary = "Block all announcements", description = "Only admin can block all announcement")
    @PutMapping("block/announcements/{userId}")
    public SimpleResponse blockAllAnnouncement(@RequestBody AdminMessageRequest request, @PathVariable Long userId) {
        return announcementService.blockAllAnnouncements(request, userId);
    }

    @Operation(summary = "Unblock all announcements", description = "Only admin can unblock all announcement")
    @PutMapping("unblock/announcements/{userId}")
    public SimpleResponse unBlockAllAnnouncement(@RequestBody AdminMessageRequest request, @PathVariable Long userId) {
        return announcementService.unBlockAllAnnouncements(request, userId);
    }

    @Operation(summary = "Delete announcements", description = "Only admin can delete announcements")
    @DeleteMapping("/announcement/delete/{announcementId}")
    public SimpleResponse deleteAnnouncement(@PathVariable Long announcementId, @RequestBody AdminMessageRequest announcementRejectRequest) {
        return announcementService.deleteAnnouncement(announcementId, announcementRejectRequest);
    }

    @Operation(summary = "Delete users", description = "Only admin can delete users")
    @DeleteMapping("user/{id}")
    public SimpleResponse deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @Operation(summary = "Get all users", description = "Only admin can see all users")
    @GetMapping("users")
    public List<UserResponse> getAllUser() {
        return userService.getAllUser();
    }

    @Operation(summary = "User profile for admin page", description = "Admin can see user profile")
    @GetMapping("user/profile/{id}")
    public UserProfileResponse getUserProfile(@PathVariable Long id) {
        return service.getUserProfile(id);
    }

    @Operation(summary = "Get All Housing", description = "Only admin can see all housing")
    @GetMapping("all-housing")
    public AdminPageAllHousingResponses getAllHousingJ(@RequestParam(required = false) BookedType bookedType,
                                                       @RequestParam(required = false) Type housingType,
                                                       @RequestParam(required = false) Kind kind,
                                                       @RequestParam(required = false) PriceType price,
                                                       @RequestParam(defaultValue = "1") int page,
                                                       @RequestParam(defaultValue = "16") int size) {

        return announcementService.getAllHousingJ(bookedType, housingType, kind, price, page, size);
    }

}
