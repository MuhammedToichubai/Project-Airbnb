package kg.airbnb.airbnb.services;

import kg.airbnb.airbnb.dto.requests.AnnouncementRejectRequest;
import kg.airbnb.airbnb.dto.responses.*;
import kg.airbnb.airbnb.dto.requests.AnnouncementRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnnouncementService {

    SimpleResponse announcementSave(AnnouncementRequest request);

    AnnouncementInnerPageResponse announcementFindById(Long announcementId);

    SimpleResponse announcementUpdate(Long id, AnnouncementRequest request);

    SimpleResponse announcementDelete(Long announcementId);

    AdminPageApplicationsResponse getAllAnnouncementsAndSize(int page, int size);

    AdminPageAnnouncementResponse findAnnouncementById(Long id);

    SimpleResponse acceptAnnouncement(Long announcementId);

    SimpleResponse rejectAnnouncement(Long announcementId, AnnouncementRejectRequest announcementRejectRequest);

    SimpleResponse deleteAnnouncement(Long announcementId, AnnouncementRejectRequest announcementRejectRequest);

    List<AnnouncementCardResponse> findAll(int page, int size);

    List<AnnouncementCardResponse> getAnnouncementsByFilter(Long region, String kind, String type, String price, int page, int size);
    
    List<AnnouncementSearchResponse> getSearchAnnouncements(Integer page, Integer pageSize, String keyword);
}
