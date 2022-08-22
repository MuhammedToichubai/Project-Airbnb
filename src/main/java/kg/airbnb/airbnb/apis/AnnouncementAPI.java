package kg.airbnb.airbnb.apis;

import kg.airbnb.airbnb.dto.requests.AnnouncementRequest;
import kg.airbnb.airbnb.dto.responses.AnnouncementCardResponse;
import kg.airbnb.airbnb.dto.responses.AnnouncementInnerPageResponse;
import kg.airbnb.airbnb.dto.responses.AnnouncementSearchResponse;
import kg.airbnb.airbnb.dto.responses.SimpleResponse;
import kg.airbnb.airbnb.repositories.AnnouncementRepository;
import kg.airbnb.airbnb.services.AnnouncementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/announcements")
@CrossOrigin
public class AnnouncementAPI {

    private final AnnouncementService announcementService;
    private final AnnouncementRepository announcementRepository;

    public AnnouncementAPI(AnnouncementService announcementService, AnnouncementRepository announcementRepository) {
        this.announcementService = announcementService;

        this.announcementRepository = announcementRepository;
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

    @GetMapping
    public List<AnnouncementCardResponse> findAll(@RequestParam int page, @RequestParam int size) {
        return announcementService.findAll(page, size);
     }
        
    @GetMapping("/filter")
    public List<AnnouncementCardResponse> getAnnouncementsByFilter(@RequestParam(required = false) Long regionId,
                                                                   @RequestParam(required = false) String kind,
                                                                   @RequestParam(required = false) String type,
                                                                   @RequestParam(required = false) String price,
                                                                   @RequestParam(defaultValue = "1") int page,
                                                                   @RequestParam(defaultValue = "16") int size) {
        return announcementService.getAnnouncementsByFilter(regionId, kind, type, price, page, size);
    }

    @GetMapping("/global/search")
    public List<AnnouncementSearchResponse> searchAnnouncements(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        return announcementService.getSearchAnnouncements(page, pageSize, keyword);
    }
}
