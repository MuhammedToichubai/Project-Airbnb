package kg.airbnb.airbnb.services;

import kg.airbnb.airbnb.dto.requests.AnnouncementRejectRequest;
import kg.airbnb.airbnb.dto.responses.*;
import kg.airbnb.airbnb.dto.requests.AnnouncementRequest;
import kg.airbnb.airbnb.enums.Kind;
import kg.airbnb.airbnb.enums.PriceType;
import kg.airbnb.airbnb.enums.Type;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnnouncementService {

    SimpleResponse announcementSave(AnnouncementRequest request);

    AnnouncementInnerPageResponse announcementFindById(Long announcementId);

    SimpleResponse announcementUpdate(Long id, AnnouncementRequest request);

    SimpleResponse announcementDelete(Long announcementId);

    List<AdminPageAnnouncementResponse> getAllAnnouncements(int page, int size);

    AdminPageAnnouncementResponse findAnnouncementById(Long id);

    kg.airbnb.airbnb.dto.responses.SimpleResponse acceptAnnouncement(Long announcementId);

    kg.airbnb.airbnb.dto.responses.SimpleResponse rejectAnnouncement(Long announcementId, AnnouncementRejectRequest announcementRejectRequest);

    kg.airbnb.airbnb.dto.responses.SimpleResponse deleteAnnouncement(Long announcementId, AnnouncementRejectRequest announcementRejectRequest);

    List<AnnouncementCardResponse> findAll(int page, int size);

    FilterResponse getAnnouncementsByFilter(Long region, Kind kind, Type type, PriceType price, int page, int size);
    
    List<AnnouncementSearchResponse> getSearchAnnouncements(Integer page, Integer pageSize, String keyword);

    AdminApplicationsAnnouncementSize getAllAnnouncementsSize();
}
