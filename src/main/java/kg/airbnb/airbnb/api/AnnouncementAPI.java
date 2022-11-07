package kg.airbnb.airbnb.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.airbnb.airbnb.dto.requests.AnnouncementRequest;
import kg.airbnb.airbnb.dto.responses.AnnouncementInnerPageResponse;
import kg.airbnb.airbnb.dto.responses.AnnouncementSaveResponse;
import kg.airbnb.airbnb.dto.responses.AnnouncementSearchResponse;
import kg.airbnb.airbnb.dto.responses.AnnouncementsResponse;
import kg.airbnb.airbnb.dto.responses.FilterResponse;
import kg.airbnb.airbnb.dto.responses.SimpleResponse;
import kg.airbnb.airbnb.enums.Kind;
import kg.airbnb.airbnb.enums.PriceType;
import kg.airbnb.airbnb.enums.Type;
import kg.airbnb.airbnb.services.AnnouncementService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/announcements")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Announcement API", description = "User announcement endpoints")
public class AnnouncementAPI {

    private final AnnouncementService announcementService;

    @Operation(summary = "Save announcement", description = "Any registered user can save.")
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public AnnouncementSaveResponse saveAnnouncement(@RequestBody AnnouncementRequest announcementRequest) {
        return announcementService.announcementSave(announcementRequest);
    }

    @Operation(summary = "Like announcement", description = "Any registered user can like")
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("like/{id}")
    public AnnouncementInnerPageResponse likeAnnouncement(@PathVariable Long id) {
        return announcementService.likeAnnouncement(id);
    }

    @Operation(summary = "Add to Favorites", description = "Any registered user can add to favorites")
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("bookmark/{id}")
    public AnnouncementInnerPageResponse bookmarkAnnouncement(@PathVariable Long id) {
        return announcementService.bookmarkAnnouncement(id);
    }

    @Operation(summary = "Find an announcement by id", description = "Any user can find announcement by id")
    @GetMapping("{id}")
    public AnnouncementInnerPageResponse getAnnouncementDetails(@PathVariable Long id) {
        return announcementService.getAnnouncementDetails(id);
    }

    @Operation(summary = "Update announcement", description = "Any registered user can update, only their own announcements")
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("{id}")
    public SimpleResponse updateAnnouncement(@PathVariable Long id, @RequestBody AnnouncementRequest request) {
        return announcementService.announcementUpdate(id, request);
    }

    @Operation(summary = "Delete announcement", description = "Any registered user can delete his own announcement ")
    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("{id}")
    public SimpleResponse deleteAnnouncement(@PathVariable Long id) {
        return announcementService.announcementDelete(id);
    }

    @Operation(summary = "Get all announcements", description = "Any user can view all announcements accepted by the administrator")
    @GetMapping
    public AnnouncementsResponse findAll(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "16") int size) {
        return announcementService.findAllAnnouncements(page, size);
    }

    @Operation(summary = "Any user can filter announcements", description = "Filter accepted announcements by Region, Popular, Latest, House Type, and Price Low to High and High to Low")
    @GetMapping("filter")
    public FilterResponse getAnnouncementsByFilter(@RequestParam(required = false) Long regionId,
                                                   @RequestParam(value = "city", required = false) String city,
                                                   @RequestParam(required = false) Kind kind,
                                                   @RequestParam(required = false) Type type,
                                                   @RequestParam(required = false) PriceType price,
                                                   @RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(defaultValue = "16") int size) {
        return announcementService.getAnnouncementsByFilter(regionId, city, kind, type, price, page, size);
    }

    @Operation(summary = "Any user can search announcements", description = "Search accepted announcements by region & city & location & house type & latitude and longitude")
    @GetMapping("search")
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
