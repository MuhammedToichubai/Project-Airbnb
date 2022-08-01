package kg.airbnb.airbnb.apis;

import kg.airbnb.airbnb.dto.request.AnnouncementRequest;
import kg.airbnb.airbnb.dto.response.AnnouncementInnerPageResponse;
import kg.airbnb.airbnb.dto.response.SimpleResponse;
import kg.airbnb.airbnb.services.AnnouncementService;
import kg.airbnb.airbnb.services.impl.AnnouncementServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/announcements")
@CrossOrigin
public class AnnouncementAPI {

    private final AnnouncementService announcementService;
    private final AnnouncementServiceImpl announcementServiceImpl;

    public AnnouncementAPI(AnnouncementService announcementService, AnnouncementServiceImpl announcementServiceImpl) {
        this.announcementService = announcementService;
        this.announcementServiceImpl = announcementServiceImpl;
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
