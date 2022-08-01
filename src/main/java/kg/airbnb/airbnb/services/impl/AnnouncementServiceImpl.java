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
import kg.airbnb.airbnb.models.Region;
import kg.airbnb.airbnb.models.auth.User;
import kg.airbnb.airbnb.repositories.AddressRepository;
import kg.airbnb.airbnb.repositories.AnnouncementRepository;
import kg.airbnb.airbnb.repositories.RegionRepository;
import kg.airbnb.airbnb.repositories.UserRepository;
import kg.airbnb.airbnb.services.AnnouncementService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final AnnouncementEditMapper editMapper;
    private final AnnouncementViewMapper viewMapper;
    private final RegionRepository regionRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public AnnouncementServiceImpl(AnnouncementRepository repository, AnnouncementEditMapper editMapper, AnnouncementViewMapper viewMapper, RegionRepository regionRepository, UserRepository userRepository, AddressRepository addressRepository) {
        this.announcementRepository = repository;
        this.editMapper = editMapper;
        this.viewMapper = viewMapper;
        this.regionRepository = regionRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public SimpleResponse announcementSave(AnnouncementRequest request) {
        Announcement newAnnouncement = editMapper.saveAnnouncement(request);
        checkAdField(request, newAnnouncement);
        addressRepository.save(savedAddress(request, newAnnouncement));
        announcementRepository.save(newAnnouncement);
        return new SimpleResponse("SAVE", "Ad saved successfully !");
    }

    private void checkAdField(AnnouncementRequest request, Announcement announcement) {
        if (request.getOwnerId() <= 0) {
            throw new BadRequestException("Id cannot be negative and null!");
        }
        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new NotFoundException("Owner with id = " + request.getOwnerId() + " not found!"));
        announcement.setOwner(owner);
        List<Announcement> ownerAnnouncement = owner.getAnnouncements();
        ownerAnnouncement.add(announcement);
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
        if (request.getHouseType().equals(Type.HOUSE)) {
            for (Announcement announcement1 : announcementRepository.findAll()) {
                if (request.getAddress().equals(announcement1.getLocation().getAddress())) {
                    throw new BadRequestException("Announcement type-house cannot be located at the same address!");
                }
            }
        }
        Address address = new Address();
        address.setAddress(request.getAddress());
        address.setCity(request.getTownProvince());
        Region region = regionRepository.findById(request.getRegionId())
                .orElseThrow(() -> new NotFoundException("Region with id = " + request.getRegionId() + " not found!"));
        address.setRegion(region);
//        address.setAnnouncement(announcement);
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

        Announcement announcement = getAnnouncementById(announcementId);
        if (!Objects.equals(announcement.getOwner().getId(), request.getOwnerId())) {
            throw new ForbiddenException("You can only edit your ads !");
        }

        List<String> currentImages = announcement.getImages();
        List<String> newImages = request.getImages();
        if (!currentImages.equals(newImages) && newImages.size() <= 4 && newImages != null) {
            announcement.setImages(newImages);
        }

        if (request.getHouseType().equals(Type.HOUSE)) {
            for (Announcement announcement1 : announcementRepository.findAll()) {
                if (request.getAddress().equals(announcement1.getLocation().getAddress())) {
                    throw new BadRequestException("Announcement type-house cannot be located at the same address!");
                }
            }
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
        if (!currentAddress.equals(newAddress) && newAddress != null) {
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

        return new SimpleResponse(
                "UPDATE",
                "Ad successfully updated."
        );
    }

    @Override
    public SimpleResponse announcementDelete(Long announcementId) {
        Announcement announcement = getAnnouncementById(announcementId);
        System.out.println("sfassfsf");
//        System.out.println(announcement);
//        List<Booking> announcementBookings = announcement.getBookings();
//        if (announcementBookings == null) {
//            throw new ForbiddenException("You cannot delete the listing because the listing has a booking!");
//        }
//        announcementRepository.delete(announcement);

        announcementRepository.delete(announcement);

        System.out.println("\"hello world\" = " + "hello world");

        return new SimpleResponse(
                "DELETE",
                "Ad successfully removed!"
        );
    }

    private Announcement getAnnouncementById(Long announcementId) {
        return announcementRepository.findById(announcementId)
                .orElseThrow(() -> new NotFoundException(
                        "Announcement with id " + announcementId + " not found!"
                ));
    }
}

