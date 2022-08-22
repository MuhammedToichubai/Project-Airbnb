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

    //User
    @PostMapping("/like/{announcementId}")
    public AnnouncementInnerPageResponse likeAnnouncement(@PathVariable Long announcementId){
        return announcementService.likeAnnouncement(announcementId);
    }

    //User
    @PostMapping("/bookmark/{announcementId}")
    public AnnouncementInnerPageResponse bookmarkAnnouncement(@PathVariable Long announcementId){
        return announcementService.bookmarkAnnouncement(announcementId);
    }

    //Admin , User
    @GetMapping("/find/{announcementId}")
    public AnnouncementInnerPageResponse getAnnouncementDetails(@PathVariable Long announcementId) {
        return announcementService.getAnnouncementDetails(announcementId);
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
