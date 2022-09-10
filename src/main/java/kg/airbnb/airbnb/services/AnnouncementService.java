package kg.airbnb.airbnb.services;

import kg.airbnb.airbnb.dto.requests.AdminMessageRequest;
import kg.airbnb.airbnb.dto.requests.AnnouncementRequest;
import kg.airbnb.airbnb.dto.responses.*;
import kg.airbnb.airbnb.enums.BookedType;
import kg.airbnb.airbnb.enums.Kind;
import kg.airbnb.airbnb.enums.PriceType;
import kg.airbnb.airbnb.enums.Type;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnnouncementService {

    AnnouncementSaveResponse announcementSave(AnnouncementRequest request);

    SimpleResponse announcementUpdate(Long id, AnnouncementRequest request);

    SimpleResponse announcementDelete(Long announcementId);

    AdminPageApplicationsResponse getAllAnnouncementsAndSize(int page, int size);

    AdminPageApplicationsAnnouncementResponse findAnnouncementById(Long id);

    SimpleResponse acceptAnnouncement(Long announcementId);

    SimpleResponse rejectAnnouncement(Long announcementId, AdminMessageRequest announcementRejectRequest);

    SimpleResponse deleteAnnouncement(Long announcementId, AdminMessageRequest announcementRejectRequest);

    AnnouncementInnerPageResponse likeAnnouncement(Long announcementId);

    AnnouncementInnerPageResponse bookmarkAnnouncement(Long announcementId);

    AnnouncementInnerPageResponse getAnnouncementDetails(Long announcementId);

    List<AnnouncementCardResponse> findAll(int page, int size);

    FilterResponse getAnnouncementsByFilter(Long region, Kind kind, Type type, PriceType price, int page, int size);

    List<AnnouncementSearchResponse> getSearchAnnouncements(Integer page, Integer pageSize, String region, String city, String address, String latitude, String longitude);

    FilterResponse getAnnouncementsByFilter(BookedType bookedType, int page, int size);

    SimpleResponse blockAnnouncement(Long announcementId, AdminMessageRequest messageRequest);

    SimpleResponse blockAllAnnouncement(AdminMessageRequest messageRequest, Long userId);
}
