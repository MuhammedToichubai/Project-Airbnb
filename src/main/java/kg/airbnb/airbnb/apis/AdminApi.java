package kg.airbnb.airbnb.apis;

import kg.airbnb.airbnb.dto.requests.AnnouncementRejectRequest;
import kg.airbnb.airbnb.dto.responses.AdminPageAnnouncementResponse;
import kg.airbnb.airbnb.dto.responses.SimpleResponse;
import kg.airbnb.airbnb.services.AnnouncementServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("api/admin")
public class AdminApi {

    private final AnnouncementServiceImpl announcementServiceImpl;

    @GetMapping("/applications")
    public List<AdminPageAnnouncementResponse> getAllAnnouncements(){
        return announcementServiceImpl.getAllAnnouncements();
    }

    @GetMapping("/find/announcement/{id}")
    public AdminPageAnnouncementResponse findAnnouncementById(@PathVariable Long id){
        return announcementServiceImpl.findAnnouncementById(id);
    }

    @PutMapping("/announcement/accept/{announcementId}")
    public SimpleResponse acceptAnnouncement(@PathVariable Long announcementId){
        return announcementServiceImpl.acceptAnnouncement(announcementId);
    }

    @PutMapping("/announcement/reject/{announcementId}")
    public SimpleResponse rejectAnnouncement(@PathVariable Long announcementId, @RequestBody AnnouncementRejectRequest announcementRejectRequest){
        return announcementServiceImpl.rejectAnnouncement(announcementId,announcementRejectRequest);
    }

    @DeleteMapping("/announcement/delete/{announcementId}")
    public SimpleResponse deleteAnnouncement(@PathVariable Long announcementId, @RequestBody AnnouncementRejectRequest announcementRejectRequest) {
        return announcementServiceImpl.deleteAnnouncement(announcementId, announcementRejectRequest);
    }

}
