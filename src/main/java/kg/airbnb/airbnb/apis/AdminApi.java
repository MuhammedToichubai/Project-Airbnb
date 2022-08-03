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

    @GetMapping("/find/{id}")
    public AdminPageAnnouncementResponse findAnnouncementById(@PathVariable Long id){
        return announcementServiceImpl.findAnnouncementById(id);
    }

    @PutMapping("/announcement/accept/{id}")
    public SimpleResponse acceptAnnouncement(@PathVariable Long id){
        return announcementServiceImpl.acceptAnnouncement(id);
    }

    @PutMapping("/announcement/reject/{id}")
    public SimpleResponse rejectAnnouncement(@PathVariable Long id, @RequestBody AnnouncementRejectRequest announcementRejectRequest){
        return announcementServiceImpl.rejectAnnouncement(id,announcementRejectRequest);
    }

    @DeleteMapping("/announcement/delete/{id}")
    public SimpleResponse deleteAnnouncement(@PathVariable Long id, @RequestBody AnnouncementRejectRequest announcementRejectRequest) {
        return announcementServiceImpl.deleteAnnouncement(id, announcementRejectRequest);
    }

}
