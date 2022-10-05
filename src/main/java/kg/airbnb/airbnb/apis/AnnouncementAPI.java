package kg.airbnb.airbnb.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.airbnb.airbnb.dto.requests.AnnouncementRequest;
import kg.airbnb.airbnb.dto.responses.*;
import kg.airbnb.airbnb.enums.Kind;
import kg.airbnb.airbnb.enums.PriceType;
import kg.airbnb.airbnb.enums.Type;
import kg.airbnb.airbnb.services.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/announcements")
@CrossOrigin
@Tag(name = "This API is for Announcement")
public class AnnouncementAPI {

    private final AnnouncementService announcementService;

    @Operation(summary = "Save announcement", description = "Any registered user can save.")
    @PostMapping("/save")
    public AnnouncementSaveResponse saveAnnouncement(@RequestBody AnnouncementRequest announcementRequest) {
        return announcementService.announcementSave(announcementRequest);
    }

    @Operation(summary = "Like announcement", description = "Any registered user can like")
    @PostMapping("/like/{announcementId}")
    public AnnouncementInnerPageResponse likeAnnouncement(@PathVariable Long announcementId) {
        return announcementService.likeAnnouncement(announcementId);
    }

    @Operation(summary = "Add to Favorites", description = "Any registered user can add to favorites")
    @PostMapping("/bookmark/{announcementId}")
    public AnnouncementInnerPageResponse bookmarkAnnouncement(@PathVariable Long announcementId) {
        return announcementService.bookmarkAnnouncement(announcementId);
    }

    @Operation(summary = "Find an announcement by id", description = "Any user can find announcement by id")
    @GetMapping("/find/{announcementId}")
    public AnnouncementInnerPageResponse getAnnouncementDetails(@PathVariable Long announcementId) {
        return announcementService.getAnnouncementDetails(announcementId);
    }

    @Operation(summary = "Update announcement", description = "Any registered user can update , only their own announcements")
    @PutMapping("/update/{announcementId}")
    public SimpleResponse updateAnnouncement(@PathVariable Long announcementId,
                                             @RequestBody AnnouncementRequest announcementRequest) {
        return announcementService.announcementUpdate(announcementId, announcementRequest);
    }

    @Operation(summary = "Delete announcement", description = "Any registered user can delete his own announcement ")
    @DeleteMapping("/delete/{announcementId}")
    public SimpleResponse deleteAnnouncement(@PathVariable Long announcementId) {
        return announcementService.announcementDelete(announcementId);
    }

    @Operation(summary = "Get all announcements", description = "Any user can view all announcements accepted by the administrator")
    @GetMapping
    public AnnouncementsResponse findAll(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "16") int size) {
        return announcementService.findAllAnnouncements(page, size);
    }

    @Operation(summary = "Filter accepted announcements by Region, Popular, Latest, House Type, and Price Low to High and High to Low", description = "Any user can filter announcements.")
    @GetMapping("/filter")
    public FilterResponse getAnnouncementsByFilter(@RequestParam(required = false) Long regionId,
                                                   @RequestParam(value = "city", required = false) String city,
                                                   @RequestParam(required = false) Kind kind,
                                                   @RequestParam(required = false) Type type,
                                                   @RequestParam(required = false) PriceType price,
                                                   @RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(defaultValue = "16") int size) {
        return announcementService.getAnnouncementsByFilter(regionId, city, kind, type, price, page, size);
    }

    @Operation(summary = "Search accepted announcements by region & city & location & house type & latitude and longitude", description = "Any user can search announcements.")
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
