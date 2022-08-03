package kg.airbnb.airbnb.services;

import kg.airbnb.airbnb.dto.requests.AnnouncementRejectRequest;
import kg.airbnb.airbnb.dto.responses.AdminPageAnnouncementResponse;
import kg.airbnb.airbnb.dto.responses.SimpleResponse;
import kg.airbnb.airbnb.enums.Role;
import kg.airbnb.airbnb.enums.Status;
import kg.airbnb.airbnb.exceptions.ForbiddenException;
import kg.airbnb.airbnb.exceptions.NotFoundException;
import kg.airbnb.airbnb.mappers.AnnouncementViewMapper;
import kg.airbnb.airbnb.models.Announcement;
import kg.airbnb.airbnb.models.auth.User;
import kg.airbnb.airbnb.repositories.AnnouncementRepository;
import kg.airbnb.airbnb.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService{

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementViewMapper announcementViewMapper;
    private final UserRepository userRepository;

    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository, AnnouncementViewMapper announcementViewMapper, UserRepository userRepository) {
        this.announcementRepository = announcementRepository;
        this.announcementViewMapper = announcementViewMapper;
        this.userRepository = userRepository;
    }

    public List<AdminPageAnnouncementResponse> getAllAnnouncements(){
        User user = getAuthenticatedUser();
        if(user.getRole().equals(Role.ADMIN)){
            return announcementViewMapper.viewAllAdminPageAnnouncementResponses(announcementRepository.findAll());
        }else{
            throw new ForbiddenException("Only admin can access this page!");
        }

    }

    public AdminPageAnnouncementResponse findAnnouncementById(Long id){
        User user = getAuthenticatedUser();
        if(user.getRole().equals(Role.ADMIN)) {
            Announcement announcement = getAnnouncementById(id);
            return announcementViewMapper.viewAdminPageAnnouncementResponse(announcement);
        }else{
            throw new ForbiddenException("Only admin can access this page!");
        }
    }



    private Announcement getAnnouncementById(Long announcementId) {
        return announcementRepository.findById(announcementId)
                .orElseThrow(() -> new NotFoundException(
                        "Announcement with id " + announcementId + " not found!"
                ));
    }

    public SimpleResponse acceptAnnouncement(Long id) {

        User user = getAuthenticatedUser();
        if(user.getRole().equals(Role.ADMIN)) {
            SimpleResponse simpleResponse = new SimpleResponse();
            Announcement announcement = getAnnouncementById(id);
            announcement.setStatus(Status.ACCEPTED);
            announcementRepository.save(announcement);
            simpleResponse.setStatus("ACCEPTED");
            simpleResponse.setMessage("Successfully saved");
            return simpleResponse;
        }else{
            throw new ForbiddenException("Only admin can access this page!");
        }

    }

    public SimpleResponse rejectAnnouncement(Long id,AnnouncementRejectRequest announcementRejectRequest) {

        User user = getAuthenticatedUser();
        if(user.getRole().equals(Role.ADMIN)) {
            SimpleResponse simpleResponse = new SimpleResponse();
            Announcement announcement = getAnnouncementById(id);
            announcement.setStatus(Status.REJECTED);
            announcementRepository.save(announcement);
            simpleResponse.setStatus("REJECTED");
            simpleResponse.setMessage(announcementRejectRequest.getMessage());
            announcementRejectRequest.setMessage("");
            return simpleResponse;
        }else{
            throw new ForbiddenException("Only admin can access this page!");
        }
    }

    public SimpleResponse deleteAnnouncement(Long id, AnnouncementRejectRequest announcementRejectRequest) {

        User user = getAuthenticatedUser();
        if(user.getRole().equals(Role.ADMIN)) {
            SimpleResponse simpleResponse = new SimpleResponse();
            Announcement announcement = getAnnouncementById(id);
            announcement.setStatus(Status.DELETED);
            announcementRepository.deleteById(id);
            simpleResponse.setStatus("DELETED");
            simpleResponse.setMessage(announcementRejectRequest.getMessage());
            return simpleResponse;
        }else{
            throw new ForbiddenException("Only admin can access this page!");
        }

    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() ->
                new ForbiddenException("Authorized user only"));
    }
}
