package kg.airbnb.airbnb.services.impl;

import kg.airbnb.airbnb.dto.requests.AnnouncementRequest;
import kg.airbnb.airbnb.dto.requests.AnnouncementRejectRequest;
import kg.airbnb.airbnb.dto.responses.AnnouncementInnerPageResponse;
import kg.airbnb.airbnb.dto.responses.GlobalSearchForAnnouncementResponse;
import kg.airbnb.airbnb.dto.responses.SimpleResponse;
import kg.airbnb.airbnb.dto.responses.AdminPageAnnouncementResponse;
import kg.airbnb.airbnb.enums.Role;
import kg.airbnb.airbnb.enums.Status;
import kg.airbnb.airbnb.enums.Type;
import kg.airbnb.airbnb.exceptions.BadRequestException;
import kg.airbnb.airbnb.exceptions.ForbiddenException;
import kg.airbnb.airbnb.exceptions.NotFoundException;
import kg.airbnb.airbnb.mappers.announcement.AnnouncementEditMapper;
import kg.airbnb.airbnb.mappers.announcement.AnnouncementViewMapper;
import kg.airbnb.airbnb.models.Address;
import kg.airbnb.airbnb.models.Announcement;
import kg.airbnb.airbnb.models.Booking;
import kg.airbnb.airbnb.models.Region;
import kg.airbnb.airbnb.models.auth.User;
import kg.airbnb.airbnb.repositories.AddressRepository;
import kg.airbnb.airbnb.repositories.AnnouncementRepository;
import kg.airbnb.airbnb.repositories.RegionRepository;
import kg.airbnb.airbnb.repositories.UserRepository;
import kg.airbnb.airbnb.services.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementEditMapper editMapper;
    private final AnnouncementViewMapper viewMapper;
    private final RegionRepository regionRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Override
    public SimpleResponse announcementSave(AnnouncementRequest request) {
        Announcement newAnnouncement = editMapper.saveAnnouncement(request);
        checkAdField(request, newAnnouncement);
        addressRepository.save(savedAddress(request, newAnnouncement));
        announcementRepository.save(newAnnouncement);
        return new SimpleResponse("SAVE", "Announcement with id " + newAnnouncement.getId() + ", saved successfully !");
    }

    private void checkAdField(AnnouncementRequest request, Announcement announcement) {
        User user = getAuthenticatedUser();
        announcement.setOwner(user);
        List<Announcement> announcements = user.getAnnouncements();
        announcements.add(announcement);

        if (request.getImages().size() <= 4) {
            announcement.setImages(request.getImages());
        } else {
            throw new BadRequestException(" You can upload up to 4 photos !");
        }

        if (request.getMaxGuests() <= 0) {
            throw new BadRequestException("The number of guests cannot be negative and equal to zero!");
        }

        BigDecimal bd = request.getPrice();
        double d = bd.doubleValue();
        if (d <= 0) {
            throw new BadRequestException("The ad price cannot be negative or zero!");
        }
        announcement.setStatus(Status.NEW);
    }

    private Address savedAddress(AnnouncementRequest request, Announcement announcement) {
        for (Announcement announcement1 : announcementRepository.findAll()) {
            if (request.getAddress().equals(announcement1.getLocation().getAddress())) {
                throw new BadRequestException("Announcement  cannot be located at the same address!");
            }
        }

        Address address = new Address();
        address.setAddress(request.getAddress());
        address.setCity(request.getTownProvince());
        Region region = regionRepository.findById(request.getRegionId())
                .orElseThrow(() -> new NotFoundException("Region with id = " + request.getRegionId() + " not found!"));
        address.setRegion(region);
        address.setAnnouncement(announcement);
        announcement.setLocation(address);
        return address;
    }

    @Override
    public AnnouncementInnerPageResponse announcementFindById(Long announcementId) {
        Announcement announcement = getAnnouncementById(announcementId);
        return viewMapper.entityToDtoConverting(announcement);

    }

    @Override
    @Transactional
    public SimpleResponse announcementUpdate(Long announcementId, AnnouncementRequest request) {
        User user = getAuthenticatedUser();
        Announcement announcement = getAnnouncementById(announcementId);
        if (announcement.getOwner().equals(user)) {

            List<String> currentImages = announcement.getImages();
            List<String> newImages = request.getImages();
            if (!currentImages.equals(newImages) && newImages.size() <= 4) {
                announcement.setImages(newImages);
            }

            Type currentHouseType = announcement.getHouseType();
            Type newHouseType = request.getHouseType();

            if (!currentHouseType.equals(newHouseType) && newHouseType != null) {
                announcement.setHouseType(newHouseType);
            }

            Integer currentMaxGuests = announcement.getMaxGuests();
            Integer newMaxGuests = request.getMaxGuests();

            if (!currentMaxGuests.equals(newMaxGuests) && newMaxGuests != null) {
                announcement.setMaxGuests(newMaxGuests);
            }

            BigDecimal currentPrice = announcement.getPrice();
            BigDecimal newPrice = request.getPrice();

            if (!currentPrice.equals(newPrice) && newPrice != null) {
                announcement.setPrice(newPrice);
            }

            String currentTitle = announcement.getTitle();
            String newTitle = request.getTitle();

            if (!currentTitle.equals(newTitle) && newTitle != null) {
                announcement.setTitle(newTitle);
            }

            String currentDescription = announcement.getDescription();
            String newDescription = request.getDescription();

            if (!currentDescription.equals(newDescription) && newDescription != null) {
                announcement.setDescription(newDescription);
            }

            Address address = announcement.getLocation();
            String currentAddress = address.getAddress();
            String newAddress = request.getAddress();
            if (!currentAddress.equals(newAddress) && newAddress != null ) {
                address.setAddress(newAddress);
            }

            String currenCity = address.getCity();
            String newCity = request.getTownProvince();
            if (!currenCity.equals(newCity) && newCity != null) {
                address.setCity(newCity);
            }

            Region currentRegion = announcement.getLocation().getRegion();
            Region newRegion = regionRepository.findById(request.getRegionId())
                    .orElseThrow(() -> new NotFoundException("Region with id = " + request.getRegionId() + " not found!"));

            if (!currentRegion.equals(newRegion) && newRegion != null) {
                address.setRegion(newRegion);
            }

            announcement.setCreatedAt(LocalDate.now());
        } else {
            throw new ForbiddenException("You can only edit your announcement !");
        }

        return new SimpleResponse(
                "UPDATE",
                "Announcement with id " + announcementId + ", successfully updated."
        );
    }

    @Override
    public SimpleResponse announcementDelete(Long announcementId) {
        User user = getAuthenticatedUser();
        Announcement announcement = getAnnouncementById(announcementId);
        if (user.equals(announcement.getOwner())) {
            List<Booking> announcementBookings = announcement.getBookings();
            if (!announcementBookings.isEmpty()) {
                throw new ForbiddenException("You cannot delete the listing because the listing has a booking!");
            }
            announcementRepository.deleteById(announcementId);
        } else if (user.getRole() == Role.ADMIN) {
            announcementRepository.deleteById(announcementId);
        } else {
            throw new ForbiddenException("You can delete your announcement !");
        }

        return new SimpleResponse(
                "DELETE",
                "Announcement whit id " + announcementId + ", successfully removed !"
        );
    }

    private Announcement getAnnouncementById(Long announcementId) {
        return announcementRepository.findById(announcementId)
                .orElseThrow(() -> new NotFoundException(
                        "Announcement with id " + announcementId + " not found!"
                ));
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() ->
                new ForbiddenException("An unregistered user cannot post an ad !"));
    }

    @Override
    public List<AdminPageAnnouncementResponse> getAllAnnouncements(){
        User user = getAuthenticatedUser();
        if(user.getRole().equals(Role.ADMIN)){
            return viewMapper.viewAllAdminPageAnnouncementResponses(announcementRepository.findAll());
        }else{
            throw new ForbiddenException("Only admin can access this page!");
        }

    }

    @Override
    public AdminPageAnnouncementResponse findAnnouncementById(Long id){
        User user = getAuthenticatedUser();
        if(user.getRole().equals(Role.ADMIN)) {
            Announcement announcement = getAnnouncementById(id);
            return viewMapper.viewAdminPageAnnouncementResponse(announcement);
        }else{
            throw new ForbiddenException("Only admin can access this page!");
        }
    }


    @Override
    public kg.airbnb.airbnb.dto.responses.SimpleResponse acceptAnnouncement(Long id) {

        User user = getAuthenticatedUser();
        if(user.getRole().equals(Role.ADMIN)) {
            kg.airbnb.airbnb.dto.responses.SimpleResponse simpleResponse = new kg.airbnb.airbnb.dto.responses.SimpleResponse();
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

    @Override
    public kg.airbnb.airbnb.dto.responses.SimpleResponse rejectAnnouncement(Long id, AnnouncementRejectRequest announcementRejectRequest) {

        User user = getAuthenticatedUser();
        if(user.getRole().equals(Role.ADMIN)) {
            kg.airbnb.airbnb.dto.responses.SimpleResponse simpleResponse = new kg.airbnb.airbnb.dto.responses.SimpleResponse();
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

    @Override
    public kg.airbnb.airbnb.dto.responses.SimpleResponse deleteAnnouncement(Long id, AnnouncementRejectRequest announcementRejectRequest) {

        User user = getAuthenticatedUser();
        if(user.getRole().equals(Role.ADMIN)) {
            kg.airbnb.airbnb.dto.responses.SimpleResponse simpleResponse = new kg.airbnb.airbnb.dto.responses.SimpleResponse();
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

    @Override
    public List<Announcement> listAll(String keyword) {
        if (keyword != null) {
            return announcementRepository.search(keyword);
        }
        return announcementRepository.findAll();
    }

}

