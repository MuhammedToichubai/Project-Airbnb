package kg.airbnb.airbnb.apis;

import kg.airbnb.airbnb.dto.requests.AnnouncementRequest;
import kg.airbnb.airbnb.dto.responses.AnnouncementInnerPageResponse;
import kg.airbnb.airbnb.dto.responses.SimpleResponse;
import kg.airbnb.airbnb.services.AnnouncementService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/announcements")
@CrossOrigin
public class AnnouncementAPI {

    private final AnnouncementService announcementService;

    public AnnouncementAPI(AnnouncementService announcementService) {
        this.announcementService = announcementService;

    }
    //User
    @PostMapping("/save")
    public SimpleResponse saveAnnouncement(@RequestBody AnnouncementRequest announcementRequest) {
        return announcementService.announcementSave(announcementRequest);
    }

    //Admin , User
    @GetMapping("/find/{announcementId}")
    public AnnouncementInnerPageResponse findAnnouncementById(@PathVariable Long announcementId) {
        return announcementService.announcementFindById(announcementId);
    }

    //User //Admin
    @PutMapping("/update/{announcementId}")
    public SimpleResponse updateAnnouncement(@PathVariable Long announcementId,
                                             @RequestBody AnnouncementRequest announcementRequest) {
        return announcementService.announcementUpdate(announcementId, announcementRequest);
    }

    @DeleteMapping("/delete/{announcementId}")
    public SimpleResponse deleteAnnouncement(@PathVariable Long announcementId) {
        return announcementService.announcementDelete(announcementId);
    }
}
