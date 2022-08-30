package kg.airbnb.airbnb.apis;

import kg.airbnb.airbnb.dto.requests.AnnouncementRequest;
import kg.airbnb.airbnb.dto.responses.*;
import kg.airbnb.airbnb.enums.Kind;
import kg.airbnb.airbnb.enums.PriceType;
import kg.airbnb.airbnb.enums.Type;
import kg.airbnb.airbnb.services.AnnouncementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public AnnouncementSaveResponse saveAnnouncement(@RequestBody AnnouncementRequest announcementRequest) {
        return announcementService.announcementSave(announcementRequest);
    }

    //Admin , User
    @GetMapping("/find/{announcementId}")
    public AnnouncementResponse findAnnouncementById(@PathVariable Long announcementId) {
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

    @GetMapping
    public List<AnnouncementCardResponse> findAll(@RequestParam int page, @RequestParam int size) {
        return announcementService.findAll(page, size);
    }

    @GetMapping("/filter")
    public FilterResponse getAnnouncementsByFilter(@RequestParam(required = false) Long regionId,
                                                   @RequestParam(required = false) Kind kind,
                                                   @RequestParam(required = false) Type type,
                                                   @RequestParam(required = false) PriceType price,
                                                   @RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(defaultValue = "16") int size) {
        return announcementService.getAnnouncementsByFilter(regionId, kind, type, price, page, size);
    }

    @GetMapping("/global/search")
    public List<AnnouncementSearchResponse> searchAnnouncements(
            @RequestParam(value = "region", required = false) String region,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "latitude", required = false) String latitude,
            @RequestParam(value = "longitude", required = false) String longitude,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        return announcementService.getSearchAnnouncements(page, pageSize, region, city, address, latitude, longitude);
    }
}
