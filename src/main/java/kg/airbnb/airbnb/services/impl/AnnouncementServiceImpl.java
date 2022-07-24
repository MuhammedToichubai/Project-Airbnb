package kg.airbnb.airbnb.services.impl;

import kg.airbnb.airbnb.dto.request.AnnouncementRequest;
import kg.airbnb.airbnb.dto.response.AnnouncementInnerPageResponse;
import kg.airbnb.airbnb.dto.response.SimpleResponse;
import kg.airbnb.airbnb.enums.Status;
import kg.airbnb.airbnb.enums.Type;
import kg.airbnb.airbnb.exceptions.BadRequestException;
import kg.airbnb.airbnb.exceptions.ForbiddenException;
import kg.airbnb.airbnb.exceptions.NotFoundException;
import kg.airbnb.airbnb.mapper.announcement.AnnouncementEditMapper;
import kg.airbnb.airbnb.mapper.announcement.AnnouncementViewMapper;
import kg.airbnb.airbnb.models.Address;
import kg.airbnb.airbnb.models.Announcement;
import kg.airbnb.airbnb.models.Booking;
import kg.airbnb.airbnb.models.Region;
import kg.airbnb.airbnb.models.auth.User;
import kg.airbnb.airbnb.repositories.AnnouncementRepository;
import kg.airbnb.airbnb.repositories.RegionRepository;
import kg.airbnb.airbnb.repositories.UserRepository;
import kg.airbnb.airbnb.services.AnnouncementService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementRepository repository;
    private final AnnouncementEditMapper editMapper;
    private final AnnouncementViewMapper viewMapper;
    private final RegionRepository regionRepository;
    private final UserRepository userRepository;

    public AnnouncementServiceImpl(AnnouncementRepository repository, AnnouncementEditMapper editMapper, AnnouncementViewMapper viewMapper, RegionRepository regionRepository, UserRepository userRepository) {
        this.repository = repository;
        this.editMapper = editMapper;
        this.viewMapper = viewMapper;
        this.regionRepository = regionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SimpleResponse announcementSave(AnnouncementRequest request) {
        Announcement newAnnouncement = editMapper.saveAnnouncement(request);
        checkAdField(request, newAnnouncement);
        repository.save(newAnnouncement);
        return new SimpleResponse("SAVE", "Ad saved successfully !");
    }

    @Override
    public AnnouncementInnerPageResponse announcementFindById(Long announcementId) {
        Announcement announcement = getAnnouncementById(announcementId);
        return viewMapper.viewAnnouncementInnerPageResponse(announcement);

    }

    @Override
    @Transactional
    public SimpleResponse announcementUpdate(Long announcementId, AnnouncementRequest request) {

        Announcement newAnnouncement = getAnnouncementById(announcementId);
        if (!Objects.equals(newAnnouncement.getOwner().getId(), request.getOwnerId())) {
            throw new ForbiddenException("You can only edit your ads !");
        }
        editMapper.updateAnnouncement(newAnnouncement, request);

        checkAdField(request, newAnnouncement);

        return new SimpleResponse(
                "UPDATE",
                "Ad successfully updated."
        );
    }

    private void checkAdField(AnnouncementRequest request, Announcement announcement) {
        if (request.getOwnerId() <= 0) {
            throw new BadRequestException("Id cannot be negative and null!");
        }
        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new NotFoundException("Owner whit id = " + request.getOwnerId() + " not found!"));
        announcement.setOwner(owner);
        owner.setAnnouncements(List.of(announcement));
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

        if (request.getHouseType().equals(Type.HOUSE)) {
            for (Announcement announcement1 : repository.findAll()) {
                if (request.getAddress().equals(announcement1.getLocation().getAddress())) {
                    throw new BadRequestException("Announcement type-house cannot be located at the same address!");
                }
            }
            Address address = new Address();
            address.setAddress(request.getAddress());
            address.setCity(request.getTownProvince());
            Region region = regionRepository.findById(request.getRegionId())
                    .orElseThrow(() -> new NotFoundException("Region whit id = " + request.getRegionId() + " not found!"));
            address.setRegion(region);
            address.setAnnouncement(announcement);
            announcement.setLocation(address);
        }
        announcement.setStatus(Status.NEW);
    }

    @Override
    @Transactional
    public SimpleResponse announcementDelete(Long announcementId) {
        Announcement announcement = getAnnouncementById(announcementId);
        List<Booking> exist = announcement.getBookings();
        if (exist == null) {
            throw new ForbiddenException("You cannot delete the listing because the listing has a booking!");
        } else {
            repository.delete(announcement);
        }
        repository.deleteById(announcementId);

        repository.deleteById(announcementId);
        return new SimpleResponse(
                "DELETE",
                "Ad successfully removed!"
        );
    }

    private Announcement getAnnouncementById(Long announcementId) {
        return repository.findById(announcementId)
                .orElseThrow(() -> new NotFoundException(
                        "Announcement with id " + announcementId + " not found!"
                ));
    }
}

