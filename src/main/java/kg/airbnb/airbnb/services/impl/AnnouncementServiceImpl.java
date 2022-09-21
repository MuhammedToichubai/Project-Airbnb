package kg.airbnb.airbnb.services.impl;

import kg.airbnb.airbnb.dto.requests.AdminMessageRequest;
import kg.airbnb.airbnb.dto.requests.AnnouncementRequest;
import kg.airbnb.airbnb.dto.responses.*;
import kg.airbnb.airbnb.enums.*;
import kg.airbnb.airbnb.exceptions.BadRequestException;
import kg.airbnb.airbnb.exceptions.ForbiddenException;
import kg.airbnb.airbnb.exceptions.NotFoundException;
import kg.airbnb.airbnb.mappers.announcement.AnnouncementEditMapper;
import kg.airbnb.airbnb.mappers.announcement.AnnouncementViewMapper;
import kg.airbnb.airbnb.mappers.booking.BookingViewMapper;
import kg.airbnb.airbnb.mappers.user.UserProfileViewMapper;
import kg.airbnb.airbnb.models.*;
import kg.airbnb.airbnb.models.auth.User;
import kg.airbnb.airbnb.repositories.*;
import kg.airbnb.airbnb.services.AnnouncementService;
import kg.airbnb.airbnb.services.UserService;
import kg.airbnb.airbnb.services.googlemap.GoogleMapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementEditMapper editMapper;
    private final AnnouncementViewMapper viewMapper;
    private final RegionRepository regionRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final UserService userService;
    private final GoogleMapService googleMapService;
    private final UserProfileViewMapper userProfileViewMapper;
    private final FeedbackRepository feedbackRepository;
    private final BookingViewMapper bookingViewMapper;

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

        log.info("The {} started saving the announcement", user.getRole());

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
    @Transactional
    public SimpleResponse announcementUpdate(Long announcementId, AnnouncementRequest request) {
        Announcement announcement = getAnnouncementById(announcementId);
        editMapper.updateAnnouncement(announcement, request);
        checkAdField(request, announcement);

        Region newRegion = regionRepository.findById(request.getRegionId())
                .orElseThrow(() -> new NotFoundException("Region with id = " + request.getRegionId() + " not found!"));
        Address address = announcement.getLocation();
        if (address.getAddress().equals(request.getAddress()) &&
                address.getRegion().equals(newRegion) &&
                address.getCity().equals(request.getTownProvince())) {
            address.setRegion(newRegion);
            address.setAddress(request.getAddress());
            address.setCity(request.getTownProvince());
        }else {
            savedAddress(request, announcement);
        }
        return new SimpleResponse(
                "UPDATE",
                "Announcement with id " + announcementId + ", successfully updated."
        );
    }

    @Override
    @Transactional
    public SimpleResponse announcementDelete(Long announcementId) {
        User user = getAuthenticatedUser();

        Announcement announcement = getAnnouncementById(announcementId);

        if (user.equals(announcement.getOwner())) {

            List<Booking> announcementBookings = announcement.getBookings();

            if (!announcementBookings.isEmpty()) {
                throw new ForbiddenException("You cannot delete the listing because the listing has a booking!");
            }

            announcementRepository.clearImages(announcementId);

            List<Feedback> feedbacks = announcement.getFeedbacks();
            for (Feedback feedback : feedbacks) {
                feedbackRepository.clearImages(feedback.getId());
            }

            announcementRepository.clearFeedback(announcementId);

            announcementRepository.clearBooking(announcementId);

            announcementRepository.customDeleteById(announcementId);

        } else {
            throw new ForbiddenException("You cannot delete this announcement!");
        }

        Optional<Announcement> id = announcementRepository.findById(announcementId);
        if (id.isEmpty()){
            log.info("The {} is trying to remove their announcement - {}",user.getRole(), announcement);
            log.error("The announcement is not deleted in the database");
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
        Page<Announcement> allAnnouncementsPage = announcementRepository.findAllNewAndSeen(pageable);
        List<Announcement> allAnnouncementsPageToListConversion = allAnnouncementsPage.getContent();
        List<AdminPageAnnouncementResponse> adminPageAnnouncementResponses = viewMapper.viewAllAdminPageAnnouncementResponses(allAnnouncementsPageToListConversion);
        AdminPageApplicationsResponse response = new AdminPageApplicationsResponse();
        response.setAllAnnouncementsSize(announcementRepository.findAllNewAndSeen().size());
        response.setPageAnnouncementResponseList(adminPageAnnouncementResponses);
        return response;
    }

    @Override
    @Transactional
    public AdminPageApplicationsAnnouncementResponse findAnnouncementById(Long id) {
        User user = getAuthenticatedUser();
        if (user.getRole().equals(Role.ADMIN)) {
            Announcement announcement = getAnnouncementById(id);
            if(announcement.getStatus().equals(Status.NEW)){
                announcement.setStatus(Status.SEEN);
            }
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
    public SimpleResponse rejectAnnouncement(Long id, AdminMessageRequest adminMessageRequest) {

        User user = getAuthenticatedUser();

        if (user.getRole().equals(Role.ADMIN)) {

            SimpleResponse simpleResponse = new SimpleResponse();

            simpleResponse.setStatus("REJECTED");

            simpleResponse.setMessage(adminMessageRequest.getMessage());

            Announcement announcement = getAnnouncementById(id);

            if (!announcement.getStatus().equals(Status.NEW)){

                throw new BadRequestException("Can be rejected once!");
            }
            announcement.setStatus(Status.REJECTED);

            announcement.setMessageFromAdmin(adminMessageRequest.getMessage());

            announcementRepository.save(announcement);

            return simpleResponse;

        } else {

            throw new ForbiddenException("Only admin can access this page!");
        }

    }

    @Override
    @Transactional
    public SimpleResponse blockAnnouncement(Long announcementId, AdminMessageRequest messageRequest) {

        Announcement announcement = getAnnouncementById(announcementId);
        changeAnnouncementStatusToBlocked(announcement);

        return new SimpleResponse(
                "BLOCK",
                "Announcement with "+announcementId+" blocked. "+messageRequest.getMessage()
        );
    }

    @Override
    @Transactional
    public SimpleResponse blockAllAnnouncements(AdminMessageRequest messageRequest, Long userId) {

        List<Announcement> announcements = announcementRepository.findUserAllAnnouncement(userId);

        for (Announcement announcement : announcements) {
            changeAnnouncementStatusToBlocked(announcement);
        }

        return new SimpleResponse(
                "BLOCKED",
                "All announcements are block. "+ messageRequest.getMessage()

        );
    }

    @Override
    @Transactional
    public SimpleResponse unBlockAnnouncement(Long announcementId, AdminMessageRequest messageRequest) {

        Announcement announcement = getAnnouncementById(announcementId);
        changeAnnouncementStatusToUnBlocked(announcement);

        return new SimpleResponse(
                "ACCEPTED",
                "Announcement with "+announcementId+" blocked. "+messageRequest.getMessage()
        );
    }

    @Override
    public AdminPageAllHousingResponses getAllHousing(BookedType bookedType, Type housingType, Kind kind, PriceType price, int page, int size) {

        Pageable pageable = PageRequest.of( page-1, size);

            if(bookedType == null && housingType == null && kind == null && price == null){
                return adminPageAllHousingResponses(announcementRepository.defaultGetAllHousing(pageable));

            }else if(bookedType == BookedType.BOOKED && kind == null && price == null ){
                return adminPageAllHousingResponses(announcementRepository.bookedOnly(pageable));

            }else if(bookedType == BookedType.NOT_BOOKED && kind == null && price == null ){
                return adminPageAllHousingResponses(announcementRepository.notBookedOnly(pageable));

            }else if(bookedType == BookedType.BOOKED && kind == null && price == PriceType.LOW_TO_HIGH ){
                List<Announcement> announcements = announcementRepository.bookedOnly(pageable);
                announcements.sort(Comparator.comparing(Announcement::getPrice));
                return adminPageAllHousingResponses(announcements);

            }else if(bookedType == BookedType.NOT_BOOKED && kind == null && price == PriceType.HIGH_TO_LOW ){
                List<Announcement> announcements = announcementRepository.notBookedOnly(pageable);
                announcements.sort(Comparator.comparing(Announcement::getPrice).reversed());
                return adminPageAllHousingResponses(announcements);

            }else if(bookedType == null && kind == Kind.THE_LASTEST && price == PriceType.LOW_TO_HIGH ){
                List<Announcement> announcements = announcementRepository.nullBookedByPrice(housingType,pageable);
                announcements.sort(Comparator.comparing(Announcement::getPrice));
                return adminPageAllHousingResponses(announcements);

            }else if(bookedType == null && kind == Kind.POPULAR && price == PriceType.LOW_TO_HIGH ){
                List<Announcement> announcements = announcementRepository.nullBookedByPrice(housingType,pageable);
                announcements.sort(Comparator.comparing(Announcement::getPrice));
                return adminPageAllHousingResponses(announcements);

            }else if(bookedType == null && kind == Kind.THE_LASTEST && price == PriceType.HIGH_TO_LOW ){
                List<Announcement> announcements = announcementRepository.nullBookedByPrice(housingType,pageable);
                announcements.sort(Comparator.comparing(Announcement::getPrice).reversed());
                return adminPageAllHousingResponses(announcements);

            }else if(bookedType == null && kind == Kind.POPULAR && price == PriceType.HIGH_TO_LOW ){
                List<Announcement> announcements = announcementRepository.nullBookedByPrice(housingType,pageable);
                announcements.sort(Comparator.comparing(Announcement::getPrice).reversed());
                return adminPageAllHousingResponses(announcements);

            }else if(bookedType == BookedType.BOOKED && kind == Kind.THE_LASTEST && price == PriceType.LOW_TO_HIGH ){
                List<Announcement> announcements = announcementRepository.bookedByPrice(housingType,pageable);
                announcements.sort(Comparator.comparing(Announcement::getPrice));
                return adminPageAllHousingResponses(announcements);

            }else if(bookedType == BookedType.BOOKED && kind == Kind.POPULAR && price == PriceType.LOW_TO_HIGH ){
                List<Announcement> announcements = announcementRepository.bookedByPrice(housingType,pageable);
                announcements.sort(Comparator.comparing(Announcement::getPrice));
                return adminPageAllHousingResponses(announcements);

            }else if(bookedType == BookedType.BOOKED && kind == Kind.THE_LASTEST && price == PriceType.HIGH_TO_LOW ){
                List<Announcement> announcements = announcementRepository.bookedByPrice(housingType,pageable);
                announcements.sort(Comparator.comparing(Announcement::getPrice).reversed());
                return adminPageAllHousingResponses(announcements);

            }else if(bookedType == BookedType.BOOKED && kind == Kind.POPULAR && price == PriceType.HIGH_TO_LOW ){
                List<Announcement> announcements = announcementRepository.bookedByPrice(housingType,pageable);
                announcements.sort(Comparator.comparing(Announcement::getPrice).reversed());
                return adminPageAllHousingResponses(announcements);

            }else if(bookedType == BookedType.NOT_BOOKED && kind == Kind.THE_LASTEST && price == PriceType.LOW_TO_HIGH ){
                List<Announcement> announcements = announcementRepository.notBookedByPrice(housingType,pageable);
                announcements.sort(Comparator.comparing(Announcement::getPrice));
                return adminPageAllHousingResponses(announcements);

            }else if(bookedType == BookedType.NOT_BOOKED && kind == Kind.POPULAR && price == PriceType.LOW_TO_HIGH ){
                List<Announcement> announcements = announcementRepository.notBookedByPrice(housingType,pageable);
                announcements.sort(Comparator.comparing(Announcement::getPrice));
                return adminPageAllHousingResponses(announcements);

            }else if(bookedType == BookedType.NOT_BOOKED && kind == Kind.THE_LASTEST && price == PriceType.HIGH_TO_LOW ){
                List<Announcement> announcements = announcementRepository.notBookedByPrice(housingType,pageable);
                announcements.sort(Comparator.comparing(Announcement::getPrice).reversed());
                return adminPageAllHousingResponses(announcements);

            }else if(bookedType == BookedType.NOT_BOOKED && kind == Kind.POPULAR && price == PriceType.HIGH_TO_LOW ){
                List<Announcement> announcements = announcementRepository.notBookedByPrice(housingType,pageable);
                announcements.sort(Comparator.comparing(Announcement::getPrice).reversed());
                return adminPageAllHousingResponses(announcements);

            }else if(bookedType == null && kind == Kind.POPULAR && price == null){
                List<Announcement> announcements = announcementRepository.defaultGetAllHousing(pageable);
                Collections.sort(announcements);
                return adminPageAllHousingResponses(announcements);

            }else if(bookedType == null && kind == Kind.THE_LASTEST && price == null){
                List<Announcement> announcements = announcementRepository.defaultGetAllHousing(pageable);
                announcements.sort(Comparator.comparing(Announcement::getCreatedAt).reversed());
                return adminPageAllHousingResponses(announcements);

            }else if(bookedType == null && kind == null && price == PriceType.HIGH_TO_LOW){
                List<Announcement> announcements = announcementRepository.nullBookedByPrice(housingType,pageable);
                announcements.sort(Comparator.comparing(Announcement::getPrice).reversed());
                return adminPageAllHousingResponses(announcements);
            }else {
            List<Announcement> announcements = announcementRepository.nullBookedByPrice(housingType,pageable);
            announcements.sort(Comparator.comparing(Announcement::getPrice));
            return adminPageAllHousingResponses(announcements);
            }

    }



    AdminPageAllHousingResponses adminPageAllHousingResponses(List<Announcement> announcements){

        List<AdminPageHousingResponse> responses = new ArrayList<>();

        for (Announcement announcement:announcements) {
            responses.add(viewMapper.announcementToHousing(announcement));
        }

        AdminPageAllHousingResponses housingResponses = new AdminPageAllHousingResponses();
        housingResponses.setAdminPageAllHousingResponseListSize(responses.size());
        housingResponses.setAdminPageHousingResponseList(responses);

        return housingResponses;
    }


    @Override
    @Transactional
    public SimpleResponse unBlockAllAnnouncements(AdminMessageRequest messageRequest, Long userId) {

        List<Announcement> announcements = announcementRepository.findUserAllAnnouncement(userId);

        for (Announcement announcement : announcements) {
            changeAnnouncementStatusToUnBlocked(announcement);
        }

        return new SimpleResponse(
                "ACCEPTED",
                "All announcements are block. "+ messageRequest.getMessage()

        );
    }

    @Transactional
    public void changeAnnouncementStatusToBlocked(Announcement announcement){
        User currentUser = getAuthenticatedUser();

        if (!currentUser.getRole().equals(Role.ADMIN)){
            throw new ForbiddenException("Only admin can access this page!");
        }

        announcement.setStatus(Status.BLOCKED);

    }

    @Transactional
    public void changeAnnouncementStatusToUnBlocked(Announcement announcement){
        User currentUser = getAuthenticatedUser();

        if (!currentUser.getRole().equals(Role.ADMIN)){
            throw new ForbiddenException("Only admin can access this page!");
        }

        announcement.setStatus(Status.ACCEPTED);

    }


    @Override
    public SimpleResponse deleteAnnouncement(Long announcementId, AdminMessageRequest adminMessageRequest) {

        User user = getAuthenticatedUser();

        if (user.getRole().equals(Role.ADMIN)) {

            SimpleResponse simpleResponse = new SimpleResponse();

            Announcement announcement = getAnnouncementById(announcementId);

            User owner = announcement.getOwner();

            owner.setMessagesFromAdmin("DELETE: "+
                    announcement.getTitle()+", "
                    +announcement.getHouseType()+"- "
                    +adminMessageRequest.getMessage());

            userRepository.save(owner);

            announcementRepository.clearImages(announcement.getId());

            List<Feedback> feedbacks = announcement.getFeedbacks();

            for (Feedback feedback : feedbacks) {

                feedbackRepository.clearImages(feedback.getId());
            }

            announcementRepository.clearFeedback(announcementId);

            announcementRepository.clearBooking(announcementId);

            announcementRepository.customDeleteById(announcement.getId());

            simpleResponse.setStatus("DELETED");

            simpleResponse.setMessage(adminMessageRequest.getMessage());

            return simpleResponse;

        } else {

            throw new ForbiddenException("Only admin can access this page!");
        }
    }



    @Override
    public FilterResponse getAnnouncementsByFilter(Long regionId, String city, Kind kind,
                                                   Type type, PriceType price,
                                                   int page, int size) {

        List<Announcement> announcements = null;
        List<Announcement> announcements2 = new ArrayList<>();

        if  (regionId != null) {
            try {
                regionRepository.findById(regionId).get();
            } catch (NoSuchElementException e) {
                throw new BadRequestException("There is no region with id = " + regionId);
            }
        }

        if (regionId != null && city != null && type != null) {
            announcements = announcementRepository.findByAddress(regionId, city.toUpperCase(Locale.ROOT), type);
        } else if (regionId != null && city != null && type == null)  {
            announcements = announcementRepository.findByAddress(regionId, city.toUpperCase(Locale.ROOT));
        } else if (regionId != null && city == null && type == null)  {
            announcements = announcementRepository.findByRegion(regionId);
        } else if (regionId == null && city != null && type != null)  {
            announcements = announcementRepository.findByAddress(city.toUpperCase(Locale.ROOT), type);
        } else if (regionId == null && city == null && type != null)  {
            announcements = announcementRepository.findByType(type);
        } else if (regionId != null && city == null && type != null)  {
            announcements = announcementRepository.findByRegionAndType(regionId, type);
        } else if (regionId == null && city != null && type == null)  {
            announcements = announcementRepository.findByCity(city.toUpperCase(Locale.ROOT));
        } else if (regionId == null && city == null && type == null)  {
            announcements = announcementRepository.findAllAccepted();
        }

        if (announcements == null) {
            throw new NotFoundException("there is no result!");
        }

        if (price == null && kind != null) {
            if (kind.equals(Kind.POPULAR)) {
                announcements.sort(Comparator.comparing(Announcement::getRating).reversed());
            } else if (kind.equals(Kind.THE_LASTEST)) {
                announcements.sort(Comparator.comparing(Announcement::getCreatedAt));
            }
        }

        if (price != null && kind == null) {
            if (price.equals(PriceType.LOW_TO_HIGH)) {
                announcements.sort(Comparator.comparing(Announcement::getPrice));
            } else if (price.equals(PriceType.HIGH_TO_LOW)) {
                announcements.sort(Comparator.comparing(Announcement::getPrice).reversed());
            }
        }

        if (price != null && kind != null) {
            if  (kind.equals(Kind.POPULAR)) {
                if (price.equals(PriceType.HIGH_TO_LOW)) {
                    for (int i = 5; i > 0; i--) {
                        List<Announcement> announcementList = new ArrayList<>();
                        for (int c = 0; c < announcements.size(); c++) {
                            if (getRating(announcements.get(c)) >= i && getRating(announcements.get(c)) < (i + 1)) {
                                announcementList.add(announcements.get(c));
                            }
                        }
                        announcementList.sort(Comparator.comparing(Announcement::getRating).reversed());
                        announcementList.sort(Comparator.comparing(Announcement::getPrice).reversed());
                        announcements2.addAll(announcementList);
                    }
                } else if (price.equals(PriceType.LOW_TO_HIGH)) {
                    for (int i = 5; i > 0; i--) {
                        List<Announcement> announcementList = new ArrayList<>();
                        for (int c = 0; c < announcements.size(); c++) {
                            if (getRating(announcements.get(c)) >= i && getRating(announcements.get(c)) < (i + 1)) {
                                announcementList.add(announcements.get(c));
                            }
                        }
                        announcementList.sort(Comparator.comparing(Announcement::getRating).reversed());
                        announcementList.sort(Comparator.comparing(Announcement::getPrice));
                        announcements2.addAll(announcementList);
                    }
                }
            } else if (kind.equals(Kind.THE_LASTEST)) {
                if (price.equals(PriceType.HIGH_TO_LOW)) {
                    Set<LocalDate> dates = new TreeSet<>();
                    for (Announcement a : announcements) {
                        dates.add(a.getCreatedAt());
                    }
                    for (LocalDate date: dates) {
                        List<Announcement> announcementList = new ArrayList<>();
                        for (Announcement a: announcements) {
                            if  (a.getCreatedAt().equals(date)) {
                                announcementList.add(a);
                            }
                        }
                        announcementList.sort(Comparator.comparing(Announcement::getPrice).reversed());
                        announcements2.addAll(announcementList);
                    }
                } else if (price.equals(PriceType.LOW_TO_HIGH)) {
                    Set<LocalDate> dates = new TreeSet<>();

                    for (Announcement a : announcements) {
                        dates.add(a.getCreatedAt());
                    }

                    for (LocalDate date: dates) {
                        List<Announcement> announcementList = new ArrayList<>();
                        for (Announcement a: announcements) {
                            if  (a.getCreatedAt().equals(date)) {
                                announcementList.add(a);
                            }
                        }
                        announcementList.sort(Comparator.comparing(Announcement::getPrice));
                        announcements2.addAll(announcementList);
                    }
                }
            }
        }

        List<Announcement> pagination = new ArrayList<>();
        FilterResponse response = new FilterResponse();

        int start = (page - 1) * size;
        int end = size * page;

        if (announcements2.size() != 0) {
            if (end > announcements2.size()) {
                end = announcements2.size();
            }
            for (int i = start; i < end; i++) {
                pagination.add(announcements2.get(i));
            }

            response.setCountOfResult((long) announcements2.size());
            response.setResponses(viewMapper.viewCard(pagination));
            return response;
        } else {
            if (end > announcements.size()) {
                end = announcements.size();
            }
            for (int i = start; i < end; i++) {
                pagination.add(announcements.get(i));
            }

            response.setCountOfResult((long) announcements.size());
            response.setResponses(viewMapper.viewCard(pagination));
            return response;
        }
    }

    private double getRating(Announcement announcement) {
        double all = 0;
        double count = 0;

        for (Feedback f: announcement.getFeedbacks()) {
            if (f.getRating() != null) {
                all = all + f.getRating();
                count++;
            }
        }

        if  (count == 0) {
            count = 1;
        }

        return all / count;
    }

    @Override
    public AnnouncementInnerPageResponse likeAnnouncement(Long announcementId) {

        Announcement announcementById = getAnnouncementById(announcementId);

        if (userService.ifLikedAnnouncement(announcementId)) {
            announcementById.decrementLikes();
            announcementById.setColorOfLike(null);
            userService.removeFromLikedAnnouncements(announcementId);
        } else {
            announcementById.incrementLikes();
            announcementById.setColorOfLike("Red");
            userService.addToLikedAnnouncements(announcementId);
        }

        announcementRepository.save(announcementById);
        return getAnnouncementInnerPageResponse(announcementById);
    }

    @Override
    public AnnouncementInnerPageResponse bookmarkAnnouncement(Long announcementId) {

        Announcement announcementById = getAnnouncementById(announcementId);

        if (userService.ifBookmarkAnnouncement((announcementId))) {
            announcementById.decrementBookmark();
            announcementById.setColorOfBookmark(null);
            userService.removeFromBookmarkAnnouncements(announcementId);
        } else {
            announcementById.incrementBookmark();
            announcementById.setColorOfBookmark("Yellow");
            userService.addToBookmarkAnnouncements(announcementId);
        }

        announcementRepository.save(announcementById);
        return getAnnouncementInnerPageResponse(announcementById);
    }

    @Override
    public AnnouncementInnerPageResponse getAnnouncementDetails(Long announcementId) {
        Announcement announcementById = getAnnouncementById(announcementId);
        announcementById.incrementViewCount();
        announcementRepository.save(announcementById);
        return getAnnouncementInnerPageResponse(announcementById);
    }

    private AnnouncementInnerPageResponse getAnnouncementInnerPageResponse(Announcement announcement) {
        AnnouncementInnerPageResponse response = viewMapper.entityToDtoConverting(announcement);
        response.setAnnouncementBookings(bookingViewMapper.viewBooked(announcement.getBookings()));
        return response;
    }

    @Override
    public AnnouncementsResponse findAllAnnouncements(int page, int size) {

        Pageable pageable = PageRequest.of(page - 1, size);
        List<Announcement> announcements = announcementRepository.findAll(pageable).getContent();

        if (announcements.isEmpty()){
            log.warn("The database is empty, there is no announcement or the admin has not yet accepted !");
        }
        List<AnnouncementCardResponse> announcementsResponses = viewMapper.viewCard(announcements);

        AnnouncementsResponse response = new AnnouncementsResponse();
        response.setCountOfResult((long) announcementRepository.findAll().size());
        response.setAnnouncements(announcementsResponses);

        return response;
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


