package kg.airbnb.airbnb.services.impl;

import kg.airbnb.airbnb.dto.requests.AnnouncementRejectRequest;
import kg.airbnb.airbnb.dto.requests.AnnouncementRequest;
import kg.airbnb.airbnb.dto.responses.*;
import kg.airbnb.airbnb.enums.*;
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
import kg.airbnb.airbnb.services.googlemap.GoogleMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementEditMapper editMapper;
    private final AnnouncementViewMapper viewMapper;
    private final RegionRepository regionRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final GoogleMapService googleMapService;

    @Override
    public AnnouncementSaveResponse announcementSave(AnnouncementRequest request) {
        Announcement newAnnouncement = editMapper.saveAnnouncement(request);
        checkAdField(request, newAnnouncement);
        addressRepository.save(savedAddress(request, newAnnouncement));
        announcementRepository.save(newAnnouncement);
        return viewMapper.convertingEntityToDto(newAnnouncement);
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
            if (Objects.equals(request.getAddress(), announcement1.getLocation().getAddress()) && Objects.equals(request.getRegionId(), announcement1.getLocation().getRegion().getId())) {
                throw new BadRequestException("There cannot be two identical addresses in one region!");
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
    public AnnouncementResponse announcementFindById(Long announcementId) {
        Announcement announcement = getAnnouncementById(announcementId);
        return viewMapper.entityToDtoConverting(announcement);
    }

    @Override
    @Transactional
    public SimpleResponse announcementUpdate(Long announcementId, AnnouncementRequest request) {
        Announcement announcement = getAnnouncementById(announcementId);
        editMapper.updateAnnouncement(announcement, request);
        checkAdField(request, announcement);

        Region newRegion = regionRepository.findById(request.getRegionId())
                .orElseThrow(() -> new NotFoundException("Region with id = " + request.getRegionId() + " not found!"));
        Address address = announcement.getLocation();
        if (address.getAddress().equals(request.getAddress()) && address.getRegion().equals(newRegion) && address.getCity().equals(request.getTownProvince())) {
            address.setRegion(newRegion);
            address.setAddress(request.getAddress());
            address.setCity(request.getTownProvince());
        }
        savedAddress(request, announcement);

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
    public AdminPageApplicationsResponse getAllAnnouncementsAndSize(int page, int size) {
        User currentUser = getAuthenticatedUser();
        if (!currentUser.getRole().equals(Role.ADMIN)) {
            throw new ForbiddenException("Only admin can access this page!");
        }
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Announcement> allAnnouncementsPage = announcementRepository.findAll(pageable);
        List<Announcement> allAnnouncementsPageToListConversion = allAnnouncementsPage.getContent();
        List<AdminPageAnnouncementResponse> adminPageAnnouncementResponses = viewMapper.viewAllAdminPageAnnouncementResponses(allAnnouncementsPageToListConversion);
        AdminPageApplicationsResponse response = new AdminPageApplicationsResponse();
        response.setAllAnnouncementsSize(announcementRepository.findAll().size());
        response.setPageAnnouncementResponseList(adminPageAnnouncementResponses);
        return response;
    }

    @Override
    public AdminPageApplicationsAnnouncementResponse findAnnouncementById(Long id) {
        User user = getAuthenticatedUser();
        if (user.getRole().equals(Role.ADMIN)) {
            Announcement announcement = getAnnouncementById(id);
            return viewMapper.entityToDtoConver(announcement);
        } else {
            throw new ForbiddenException("Only admin can access this page!");
        }
    }

    @Override
    public SimpleResponse acceptAnnouncement(Long id) {

        User user = getAuthenticatedUser();
        if (user.getRole().equals(Role.ADMIN)) {
            SimpleResponse simpleResponse = new SimpleResponse();
            Announcement announcement = getAnnouncementById(id);
            announcement.setStatus(Status.ACCEPTED);
            announcementRepository.save(announcement);
            simpleResponse.setStatus("ACCEPTED");
            simpleResponse.setMessage("Successfully saved");
            return simpleResponse;
        } else {
            throw new ForbiddenException("Only admin can access this page!");
        }
    }

    @Override
    public SimpleResponse rejectAnnouncement(Long id, AnnouncementRejectRequest announcementRejectRequest) {

        User user = getAuthenticatedUser();
        if (user.getRole().equals(Role.ADMIN)) {
            SimpleResponse simpleResponse = new SimpleResponse();
            Announcement announcement = getAnnouncementById(id);
            announcement.setStatus(Status.REJECTED);
            announcementRepository.save(announcement);
            simpleResponse.setStatus("REJECTED");
            simpleResponse.setMessage(announcementRejectRequest.getMessage());
            announcementRejectRequest.setMessage("");
            return simpleResponse;
        } else {
            throw new ForbiddenException("Only admin can access this page!");
        }
    }

    @Override
    public SimpleResponse deleteAnnouncement(Long id, AnnouncementRejectRequest announcementRejectRequest) {

        User user = getAuthenticatedUser();
        if (user.getRole().equals(Role.ADMIN)) {
            SimpleResponse simpleResponse = new SimpleResponse();
            Announcement announcement = getAnnouncementById(id);
            announcement.setStatus(Status.DELETED);
            announcementRepository.deleteById(id);
            simpleResponse.setStatus("DELETED");
            simpleResponse.setMessage(announcementRejectRequest.getMessage());
            return simpleResponse;
        } else {
            throw new ForbiddenException("Only admin can access this page!");
        }
    }

    @Override
    public FilterResponse getAnnouncementsByFilter(Long regionId, Kind kind,
                                                   Type type, PriceType price,
                                                   int page, int size) {

        List<Announcement> announcements = new ArrayList<>(findByFilter
                (regionId, type, price, page, size).getContent());

        if (kind != null && kind.equals(Kind.POPULAR)) {
            Collections.sort(announcements);
        } else if (kind != null && kind.equals(Kind.THE_LASTEST)) {
            announcements.sort(Comparator.comparing(Announcement::getCreatedAt));
        }

        FilterResponse filterResponse = new FilterResponse();
        filterResponse.setResponses(viewMapper.viewCard(announcements));
        filterResponse.setCountOfResult(findByFilter
                (regionId, type, price, page, size).getTotalElements());
        return filterResponse;
    }

    private Page<Announcement> findByFilter(Long regionId,
                                            Type type, PriceType price,
                                            int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Announcement> announcements = null;

        if  (regionId != null) {
            try {
                regionRepository.findById(regionId).get();
            } catch (NoSuchElementException e) {
                throw new BadRequestException("There is no region with id = " + regionId);
            }
        }


        if (!Objects.equals(regionId, null) && Objects.equals(type, null) && Objects.equals(price, null)) {
            announcements = announcementRepository.findByRegion(regionId, pageable);
        } else if (!Objects.equals(regionId, null) && !Objects.equals(type, null) && Objects.equals(price, null)) {
            announcements = announcementRepository.findByRegionAndType(regionId, type, pageable);
        } else if (!Objects.equals(regionId, null) && !Objects.equals(type, null) && !Objects.equals(price, null)) {
            if (price.equals(PriceType.LOW_TO_HIGH)) {
                announcements = announcementRepository.findByRegionAndTypeAndPriceLow(regionId, type, pageable);
            } else if (price.equals(PriceType.HIGH_TO_LOW)) {
                announcements = announcementRepository.findByRegionAndTypeAndPriceHigh(regionId, type, pageable);
            }
        } else if (Objects.equals(regionId, null) && !Objects.equals(type, null) && Objects.equals(price, null)) {
            announcements = announcementRepository.findByType(type, pageable);
        } else if (Objects.equals(regionId, null) && Objects.equals(type, null) && !Objects.equals(price, null)) {
            if (price.equals(PriceType.LOW_TO_HIGH)) {
                announcements = announcementRepository.findByPriceLow(pageable);
            } else if (price.equals(PriceType.HIGH_TO_LOW)) {
                announcements = announcementRepository.findByPriceHigh(pageable);
            }
        } else if (Objects.equals(regionId, null) && !Objects.equals(type, null) && !Objects.equals(price, null)) {
            if (price.equals(PriceType.LOW_TO_HIGH)) {
                announcements = announcementRepository.findByTypeAndPriceLow(type, pageable);
            } else if (price.equals(PriceType.HIGH_TO_LOW)) {
                announcements = announcementRepository.findByTypeAndPriceHigh(type, pageable);
            }
        } else if (!Objects.equals(regionId, null) && Objects.equals(type, null) && !Objects.equals(price, null)) {
            if (price.equals(PriceType.LOW_TO_HIGH)) {
                announcements = announcementRepository.findByRegionAndPriceLow(regionId, pageable);
            } else if (price.equals(PriceType.HIGH_TO_LOW)) {
                announcements = announcementRepository.findByRegionAndPriceHigh(regionId, pageable);
            }
        } else {
            announcements = announcementRepository.findAll(pageable);
        }

        if (announcements.isEmpty()) {
            throw new NotFoundException("There is no result");
        }

        return announcements;
    }

    @Override
    public List<AnnouncementCardResponse> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return viewMapper.viewCard(
                announcementRepository.findAll(pageable).getContent());
    }

    @Override
    public List<AnnouncementSearchResponse> getSearchAnnouncements(Integer page, Integer pageSize, String region, String city, String address, String latitude, String longitude) {

        Pageable pageable = PageRequest.of(page - 1, pageSize);

        if (region != null && city == null && address == null && latitude == null && longitude == null) {
            List<Announcement> announcementList = announcementRepository.globalSearch(transliterate(region), pageable);
            return viewMapper.getViewAllSearchAnnouncements(convertingAndAnnouncementList(region, announcementList));
        } else if (region != null && city != null && address == null && latitude == null && longitude == null) {
            List<Announcement> announcementList1 = announcementRepository.searchByRegion(transliterate(region), pageable);
            List<Announcement> announcementsByRegion = convertingAndAnnouncementList(region, announcementList1);
            List<Announcement> announcementList2 = announcementRepository.searchByCity(transliterate(city), pageable);
            List<Announcement> announcementsByCity = convertingAndAnnouncementList(city, announcementList2);
            List<Announcement> resultAnnouncements = new ArrayList<>();
            for (Announcement announcement : announcementsByRegion) {
                if (announcement.getLocation().getCity().equals(announcementsByCity.get(0).getLocation().getCity()) &&
                        announcement.getLocation().getRegion().getRegionName().equals(announcementsByCity.get(0).getLocation().getRegion().getRegionName())) {
                    resultAnnouncements.add(announcement);
                }
            }
            return viewMapper.getViewAllSearchAnnouncements(resultAnnouncements);
        } else if (region != null && city != null && address != null && latitude == null && longitude == null) {
            List<Announcement> announcementList1 = announcementRepository.searchByRegion(transliterate(region), pageable);
            List<Announcement> announcementsByRegion = convertingAndAnnouncementList(region, announcementList1);
            List<Announcement> announcementList2 = announcementRepository.searchByCity(transliterate(city), pageable);
            List<Announcement> announcementsByCity = convertingAndAnnouncementList(city, announcementList2);
            List<Announcement> announcementList3 = announcementRepository.searchByAddress(transliterate(address), pageable);
            List<Announcement> announcementsByAddress = convertingAndAnnouncementList(address, announcementList3);
            List<Announcement> inOneCityInOneRegionAds = new ArrayList<>();
            List<Announcement> resultAnnouncements = new ArrayList<>();
            for (Announcement announcement : announcementsByRegion) {
                if (announcement.getLocation().getCity().equals(announcementsByCity.get(0).getLocation().getCity())) {
                    inOneCityInOneRegionAds.add(announcement);
                }
            }
            for (Announcement announcement : inOneCityInOneRegionAds) {
                if (announcement.getLocation().getAddress().equals(announcementsByAddress.get(0).getLocation().getAddress())) {
                    resultAnnouncements.add(announcement);
                }
            }
            return viewMapper.getViewAllSearchAnnouncements(resultAnnouncements);
        } else if (region == null && city == null && address == null && latitude != null && longitude != null) {
            String place = googleMapService.findPlace(latitude, longitude);
            if (place.toLowerCase().contains("chüy region") && place.toLowerCase().contains("bishkek") ){
                return getAnnouncementsByRegion("Bishkek", pageable);
            }else if (place.toLowerCase().contains("chüy region")){
                return getAnnouncementsByRegion("Chui", pageable);
            }else if (place.toLowerCase().contains("talas region")){
                return getAnnouncementsByRegion("Talas", pageable);
            }else if (place.toLowerCase().contains("issyk-kul")){
                return getAnnouncementsByRegion("Issyk-Kul", pageable);
            }else if (place.toLowerCase().contains("naryn region")){
                return getAnnouncementsByRegion("Naryn", pageable);
            }else if (place.toLowerCase().contains("jalal-abad region")){
                return getAnnouncementsByRegion("Jalalabad", pageable);
            }else if (place.toLowerCase().contains("osh region") && place.toLowerCase().contains("osh city")){
                return getAnnouncementsByRegion("Osh City", pageable);
            }else if (place.toLowerCase().contains("osh region")){
                return getAnnouncementsByRegion("Osh Obl", pageable);
            }else if (place.toLowerCase().contains("batken region")){
                return getAnnouncementsByRegion("Batken", pageable);
            }else {
                throw new BadRequestException("You are not in Kyrgyzstan!");
            }
        }

        Page<Announcement> allAnnouncementsPage = announcementRepository.findAll(pageable);
        List<Announcement> allAnnouncementsPageToListConversion = allAnnouncementsPage.getContent();
        return viewMapper.getViewAllSearchAnnouncements(allAnnouncementsPageToListConversion);
    }
    private List<AnnouncementSearchResponse> getAnnouncementsByRegion(String regionName , Pageable pageable){
        List<Announcement> announcementList1 = announcementRepository.searchByRegion(regionName, pageable);
        List<Announcement> announcementsByRegion = convertingAndAnnouncementList(regionName, announcementList1);
        return viewMapper.getViewAllSearchAnnouncements(convertingAndAnnouncementList(regionName, announcementsByRegion));
    }

    private List<Announcement> convertingAndAnnouncementList(String keyword, List<Announcement> announcements
    ) {
        Set<Announcement> foundUniqAnnouncements = new HashSet<>(announcements);
        List<Announcement> foundAnnouncementsList = new ArrayList<>(foundUniqAnnouncements);
        Optional<Announcement> optional = foundAnnouncementsList.stream().findFirst();
        optional.orElseThrow(() -> new NotFoundException
                ("По запросу '" + keyword + "' ничего не найдено. " +
                        "Рекомендации: " +
                        "Убедитесь, что все слова написаны без ошибок. " +
                        "Попробуйте использовать другие ключевые слова. " +
                        "Попробуйте использовать более популярные ключевые слова."
                ));

        return foundAnnouncementsList;
    }

    private String transliterate(String message) {
        if (message.toUpperCase(Locale.ROOT).equals("APARTMENT") || message.toUpperCase(Locale.ROOT).equals("HOUSE")) {
            message = message.toUpperCase(Locale.ROOT);
        }
        String prepareMessage = message.substring(0, 1).toUpperCase(Locale.ROOT) + message.substring(1);
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

