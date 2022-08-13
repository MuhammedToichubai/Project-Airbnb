package kg.airbnb.airbnb.services.impl;

import kg.airbnb.airbnb.dto.requests.AnnouncementRequest;
import kg.airbnb.airbnb.dto.requests.AnnouncementRejectRequest;
import kg.airbnb.airbnb.dto.responses.*;
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
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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

    protected Announcement getAnnouncementById(Long announcementId) {
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
    public List<AnnouncementSearchResponse> listAll(Integer pageNo, Integer pageSize, String keyword) {

//        List<Announcement> search = announcementRepository.search(keyword);
//        Set<Announcement> announcements = new TreeSet<>(search);


        if (keyword != null) {
            List<Announcement> foundAnnouncementsList = announcementRepository.search(transliterate(keyword));
            Optional<Announcement> optional = foundAnnouncementsList.stream().findFirst();
            optional.orElseThrow(() -> new NotFoundException
                    ("По запросу '" + keyword +"' ничего не найдено. " +
                            "Рекомендации: " +
                            "Убедитесь, что все слова написаны без ошибок. " +
                            "Попробуйте использовать другие ключевые слова. " +
                            "Попробуйте использовать более популярные ключевые слова."
                    ));
            return viewMapper.getAllFoundAnnouncement(foundAnnouncementsList);
        }

        return viewMapper.getAllFoundAnnouncement(announcementRepository.findAll());
    }

    public  String transliterate(String message){
        String prepareMessage = message.substring(0, 1).toUpperCase(Locale.ROOT)+ message.substring(1);
            char[] abcCyr = {' ', 'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
            String[] abcLat = {" ", "a", "b", "v", "g", "d", "e", "e", "zh", "z", "i", "y", "k", "l", "m", "n", "o", "p", "r", "s", "t", "u", "f", "h", "ts", "ch", "sh", "sch", "", "i", "", "e", "ju", "ja", "A", "B", "V", "G", "D", "E", "E", "Zh", "Z", "I", "Y", "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "F", "H", "Ts", "Ch", "Sh", "Sch", "", "I", "", "E", "Ju", "Ja", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < prepareMessage.length(); i++) {
                for (int x = 0; x < abcCyr.length; x++) {
                    if (prepareMessage.charAt(i) == abcCyr[x]) {
                        builder.append(abcLat[x]);
                    }
                }
            }
            return builder.toString();
        }

}

