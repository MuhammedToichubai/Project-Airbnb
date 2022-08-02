package kg.airbnb.airbnb.services;

import kg.airbnb.airbnb.dto.requests.AnnouncementRejectRequest;
import kg.airbnb.airbnb.dto.responses.AdminPageAnnouncementResponse;
import kg.airbnb.airbnb.dto.responses.SimpleResponse;
import kg.airbnb.airbnb.enums.Status;
import kg.airbnb.airbnb.exceptions.NotFoundException;
import kg.airbnb.airbnb.mappers.AnnouncementViewMapper;
import kg.airbnb.airbnb.models.Announcement;
import kg.airbnb.airbnb.repositories.AnnouncementRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService{

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementViewMapper announcementViewMapper;

    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository, AnnouncementViewMapper announcementViewMapper) {
        this.announcementRepository = announcementRepository;
        this.announcementViewMapper = announcementViewMapper;
    }

    public List<AdminPageAnnouncementResponse> getAllAnnouncements(){
        return announcementViewMapper.viewAllAdminPageAnnouncementResponses(announcementRepository.findAll());
    }

    public AdminPageAnnouncementResponse findAnnouncementById(Long id){
         Announcement announcement = getAnnouncementById(id);
         return announcementViewMapper.viewAdminPageAnnouncementResponse(announcement);
    }



    private Announcement getAnnouncementById(Long announcementId) {
        return announcementRepository.findById(announcementId)
                .orElseThrow(() -> new NotFoundException(
                        "Announcement with id " + announcementId + " not found!"
                ));
    }

    public SimpleResponse acceptAnnouncement(Long id) {

        SimpleResponse simpleResponse = new SimpleResponse();
        Announcement announcement = getAnnouncementById(id);
        announcement.setStatus(Status.ACCEPTED);
        announcementRepository.save(announcement);
        simpleResponse.setStatus("ACCEPTED");
        simpleResponse.setMessage("Successfully saved");
        return simpleResponse;


    }

    public SimpleResponse rejectAnnouncement(Long id,AnnouncementRejectRequest announcementRejectRequest) {

        SimpleResponse simpleResponse = new SimpleResponse();
        Announcement announcement = getAnnouncementById(id);
        announcement.setStatus(Status.REJECTED);
        announcementRepository.save(announcement);
        simpleResponse.setStatus("REJECTED");
        simpleResponse.setMessage(announcementRejectRequest.getMessage());
        announcementRejectRequest.setMessage("");

        return simpleResponse;
    }

    public SimpleResponse deleteAnnouncement(Long id, AnnouncementRejectRequest announcementRejectRequest) {

        SimpleResponse simpleResponse = new SimpleResponse();
        Announcement announcement = getAnnouncementById(id);
        announcement.setStatus(Status.DELETED);
        announcementRepository.deleteById(id);
        simpleResponse.setStatus("DELETED");
        simpleResponse.setMessage(announcementRejectRequest.getMessage());

        return simpleResponse;
    }
}
