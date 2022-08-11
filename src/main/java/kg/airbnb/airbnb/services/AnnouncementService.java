package kg.airbnb.airbnb.services;
import kg.airbnb.airbnb.dto.requests.AnnouncementRequest;
import kg.airbnb.airbnb.dto.requests.AnnouncementRejectRequest;
import kg.airbnb.airbnb.dto.responses.AnnouncementCardResponse;
import kg.airbnb.airbnb.dto.responses.AnnouncementInnerPageResponse;
import kg.airbnb.airbnb.dto.responses.SimpleResponse;
import kg.airbnb.airbnb.dto.responses.AdminPageAnnouncementResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnnouncementService {

    SimpleResponse announcementSave(AnnouncementRequest request);

    AnnouncementInnerPageResponse announcementFindById(Long announcementId);

    SimpleResponse announcementUpdate(Long id, AnnouncementRequest request);

    SimpleResponse announcementDelete(Long announcementId);

    List<AdminPageAnnouncementResponse> getAllAnnouncements();

    AdminPageAnnouncementResponse findAnnouncementById(Long id);

    kg.airbnb.airbnb.dto.responses.SimpleResponse acceptAnnouncement(Long announcementId);

    kg.airbnb.airbnb.dto.responses.SimpleResponse rejectAnnouncement(Long announcementId, AnnouncementRejectRequest announcementRejectRequest);

    kg.airbnb.airbnb.dto.responses.SimpleResponse deleteAnnouncement(Long announcementId, AnnouncementRejectRequest announcementRejectRequest);

    List<AnnouncementCardResponse> getAnnouncementsByFilter(String region, String kind, String type, String price, int page, int size);
}
