package kg.airbnb.airbnb.services;

import kg.airbnb.airbnb.dto.request.AnnouncementRequest;
import kg.airbnb.airbnb.dto.response.AnnouncementInnerPageResponse;
import kg.airbnb.airbnb.dto.response.SimpleResponse;
import org.springframework.stereotype.Service;

@Service
public interface AnnouncementService {

    SimpleResponse announcementSave(AnnouncementRequest request);

    AnnouncementInnerPageResponse announcementFindById(Long announcementId);

    SimpleResponse announcementUpdate(Long id, AnnouncementRequest request);

    SimpleResponse announcementDelete(Long announcementId);
}
