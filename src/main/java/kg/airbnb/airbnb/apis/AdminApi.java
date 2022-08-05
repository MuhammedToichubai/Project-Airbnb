package kg.airbnb.airbnb.apis;

import kg.airbnb.airbnb.dto.requests.AnnouncementRejectRequest;
import kg.airbnb.airbnb.dto.responses.AdminPageAnnouncementResponse;
import kg.airbnb.airbnb.dto.responses.SimpleResponse;
import kg.airbnb.airbnb.services.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("api/admin")
public class AdminApi {

    private final AnnouncementService announcementService;

    @GetMapping("/applications")
    public List<AdminPageAnnouncementResponse> getAllAnnouncements(){
        return announcementService.getAllAnnouncements();
    }

    @GetMapping("/find/announcement/{id}")
    public AdminPageAnnouncementResponse findAnnouncementById(@PathVariable Long announcementId){
        return announcementService.findAnnouncementById(id);
    }

    @PutMapping("/announcement/accept/{announcementId}")
    public SimpleResponse acceptAnnouncement(@PathVariable Long announcementId){
        return announcementService.acceptAnnouncement(announcementId);
    }

    @PutMapping("/announcement/reject/{announcementId}")
    public SimpleResponse rejectAnnouncement(@PathVariable Long announcementId, @RequestBody AnnouncementRejectRequest announcementRejectRequest){
        return announcementService.rejectAnnouncement(announcementId,announcementRejectRequest);
    }

    @DeleteMapping("/announcement/delete/{announcementId}")
    public SimpleResponse deleteAnnouncement(@PathVariable Long announcementId, @RequestBody AnnouncementRejectRequest announcementRejectRequest) {
        return announcementService.deleteAnnouncement(announcementId, announcementRejectRequest);
    }

}
