package kg.airbnb.airbnb.db.service.impl;

import kg.airbnb.airbnb.dto.requests.BlockBookDateRequest;
import kg.airbnb.airbnb.dto.requests.BookRequest;
import kg.airbnb.airbnb.dto.requests.ChangeBookingsStatusRequest;
import kg.airbnb.airbnb.dto.requests.UpdateBookRequest;
import kg.airbnb.airbnb.dto.responses.BookedResponse;
import kg.airbnb.airbnb.dto.responses.BookingCardResponse;
import kg.airbnb.airbnb.dto.responses.ClosedDatesResponse;
import kg.airbnb.airbnb.dto.responses.FavoriteAnnouncementResponse;
import kg.airbnb.airbnb.dto.responses.FavoritesResponse;
import kg.airbnb.airbnb.dto.responses.MyAnnouncementsBookingRequestsResponse;
import kg.airbnb.airbnb.dto.responses.SimpleResponse;
import kg.airbnb.airbnb.dto.responses.UserProfileResponse;
import kg.airbnb.airbnb.dto.responses.UserResponse;
import kg.airbnb.airbnb.enums.Role;
import kg.airbnb.airbnb.enums.Status;
import kg.airbnb.airbnb.exceptions.BadRequestException;
import kg.airbnb.airbnb.exceptions.ForbiddenException;
import kg.airbnb.airbnb.exceptions.NotFoundException;
import kg.airbnb.airbnb.mappers.announcement.AnnouncementViewMapper;
import kg.airbnb.airbnb.mappers.booking.BookingViewMapper;
import kg.airbnb.airbnb.mappers.user.UserProfileViewMapper;
import kg.airbnb.airbnb.db.model.Announcement;
import kg.airbnb.airbnb.db.model.Booking;
import kg.airbnb.airbnb.db.model.Feedback;
import kg.airbnb.airbnb.db.model.User;
import kg.airbnb.airbnb.db.repositories.AnnouncementRepository;
import kg.airbnb.airbnb.db.repositories.BookingRepository;
import kg.airbnb.airbnb.db.repositories.FeedbackRepository;
import kg.airbnb.airbnb.db.repositories.UserRepository;
import kg.airbnb.airbnb.db.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AnnouncementRepository announcementRepository;
    private final BookingRepository bookingRepository;
    private final BookingViewMapper bookingViewMapper;
    private final UserProfileViewMapper userProfileViewMapper;
    private final AnnouncementViewMapper announcementViewMapper;
    private final FeedbackRepository feedbackRepository;

    @Override
    public void removeFromLikedFeedbacks(Long feedbackId) {
        User currentUser = getAuthenticatedUser();
        currentUser.removeFromLikedFeedbacks(feedbackId);
        userRepository.save(currentUser);
    }

    @Override
    public void removeFromLikedAnnouncements(Long announcementId) {
        User currentUser = getAuthenticatedUser();
        currentUser.removeFromLikedAnnouncements(announcementId);
        userRepository.save(currentUser);
    }

    @Override
    public void addToLikedAnnouncements(Long announcementId) {
        User currentUser = getAuthenticatedUser();
        currentUser.addToLikedAnnouncements(announcementId);
        userRepository.save(currentUser);
    }

    @Override
    public boolean ifLikedAnnouncement(Long announcementId) {
        return getAuthenticatedUser().getLikedAnnouncements().stream().anyMatch(likedAnnouncement -> likedAnnouncement.equals(announcementId));
    }

    @Override
    public boolean ifBookmarkAnnouncement(Long announcementId) {
        return getAuthenticatedUser().getBookmarkAnnouncements().stream().anyMatch(bookmarkAnnouncement -> bookmarkAnnouncement.equals(announcementId));
    }

    @Override
    public void removeFromBookmarkAnnouncements(Long announcementId) {
        User currentUser = getAuthenticatedUser();
        currentUser.removeFromBookmarkAnnouncement(announcementId);
        userRepository.save(currentUser);
    }

    @Override
    public void addToBookmarkAnnouncements(Long announcementId) {
        User currentUser = getAuthenticatedUser();
        currentUser.addToBookmarkAnnouncements(announcementId);
        userRepository.save(currentUser);
    }

    @Override
    public void removeFromDisLikedFeedbacks(Long feedbackId) {
        User currentUser = getAuthenticatedUser();
        currentUser.removeFromDisLikedFeedbacks(feedbackId);
        userRepository.save(currentUser);
    }

    @Override
    public void addToLikedFeedbacks(Long feedbackId) {
        User currentUser = getAuthenticatedUser();
        currentUser.addToLikedFeedbacks(feedbackId);
        userRepository.save(currentUser);
    }

    @Override
    public boolean ifLikedFeedback(Long feedbackId) {
        return getAuthenticatedUser().getLikedFeedbacks().stream().anyMatch(likedFeedback -> likedFeedback.equals(feedbackId));
    }

    @Override
    public boolean ifDisLikedFeedback(Long feedbackId) {
        return getAuthenticatedUser().getDisLikedFeedbacks().stream().anyMatch(disLikedFeedback -> disLikedFeedback.equals(feedbackId));
    }

    @Override
    public void addToDisLikedFeedbacks(Long feedbackId) {
        User currentUser = getAuthenticatedUser();
        currentUser.addToDisLikedFeedbacks(feedbackId);
        userRepository.save(currentUser);
    }

    @Override
    public UserProfileResponse getUserProfile() {
        User user = getAuthenticatedUser();
        return userProfileViewMapper.entityToDto(user);
    }

    @Override
    public UserProfileResponse getUserProfile(Long userId) {
        User currentUser = getAuthenticatedUser();
        UserProfileResponse userProfileResponse;
        if (currentUser.getRole().equals(Role.ADMIN)) {
            User user = userRepository.findById(userId).orElseThrow(() ->
                    new NotFoundException("User with " + userId + " not found !"));
            if (user.getRole().equals(Role.ADMIN)) {
                throw new NotFoundException("User with " + userId + " not found !");
            } else {
                userProfileResponse = userProfileViewMapper.entityToDto(user);
            }
        } else {
            throw new ForbiddenException("Only admin can access this page!");
        }
        return userProfileResponse;
    }

    @Override
    public List<FavoriteAnnouncementResponse> userFavoriteAnnouncements() {
        User currentUser = getAuthenticatedUser();
        Set<Long> bookmarkAnnouncements = currentUser.getBookmarkAnnouncements();
        List<Announcement> favoriteAnnouncement = new ArrayList<>();

        for (Long aLong : bookmarkAnnouncements) {
            Announcement announcement = getAnnouncementFindId(aLong);
            favoriteAnnouncement.add(announcement);
        }

        List<FavoriteAnnouncementResponse> responseList = new ArrayList<>();

        for (Announcement announcement : favoriteAnnouncement) {
            FavoriteAnnouncementResponse response = new FavoriteAnnouncementResponse(
                    announcement.getId(),
                    announcement.getImages(),
                    announcement.getPrice(),
                    announcementViewMapper.calculateRating(announcement),
                    announcement.getTitle(),
                    announcement.getLocation().getAddress() + ", "
                            + announcement.getLocation().getCity() + ", "
                            + announcement.getLocation().getRegion().getRegionName(),
                    announcement.getMaxGuests(),
                    announcement.getLike(),
                    announcement.getBookmark(),
                    announcement.getStatus()
            );
            responseList.add(response);
        }
        return responseList;
    }

    @Override
    public FavoritesResponse getUserFavoriteAnnouncements() {
        List<FavoriteAnnouncementResponse> responseList = userFavoriteAnnouncements();
        return new FavoritesResponse(responseList.size(), responseList);
    }

    @Override
    public SimpleResponse deleteMessagesFromAdmin() {
        User currentUser = getAuthenticatedUser();
        userRepository.clearMessages(currentUser.getId());
        return new SimpleResponse("DELETE", "Successfully deleted all messages!");
    }

    @Override
    public List<MyAnnouncementsBookingRequestsResponse> findUsersRequests() {
        User user = getAuthenticatedRoleUser();
        List<Announcement> announcements = user.getAnnouncements();
        List<MyAnnouncementsBookingRequestsResponse> responses = new ArrayList<>();

        for (Announcement a : announcements) {
            List<Booking> bookings = bookingRepository.findAllByAnnouncementId(a.getId());
            bookings.sort(Comparator.comparing(Booking::getStatus));
            responses.add(bookingViewMapper.viewUsersRequests(a, bookings));
        }
        return responses;
    }

    private Announcement getAnnouncementFindId(Long announcementId) {
        return announcementRepository.findById(announcementId).orElseThrow(() ->
                new NotFoundException("Announcement with " + announcementId + " not found!"));
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() ->
                new ForbiddenException("An unregistered user cannot write comment for this announcement!"));
    }

    private User getAuthenticatedRoleUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        User user = userRepository.findByEmail(login).orElseThrow(() ->
                new ForbiddenException("An unregistered user cannot write comment for this announcement!"));
        if (!user.getRole().equals(Role.USER)) {
            throw new ForbiddenException("You are not user!");
        }
        return user;
    }

    public SimpleResponse deleteUser(Long userId) {
        User currentUser = getAuthenticatedUser();
        if (currentUser.getRole().equals(Role.ADMIN)) {
            User user = userRepository.findById(userId).orElseThrow(() ->
                    new NotFoundException("User with " + userId + "not found !"));

            if (user.getRole().equals(Role.ADMIN)) {
                throw new ForbiddenException("User with id not found !");

            } else {
                userRepository.clearMessages(userId);
                userRepository.clearBookings(userId);

                for (Feedback feedback : user.getFeedbacks()) {
                    feedbackRepository.clearImages(feedback.getId());
                }
                userRepository.clearFeedbacks(userId);

                for (Announcement announcement : user.getAnnouncements()) {
                    announcementRepository.clearImages(announcement.getId());
                    for (Feedback feedback : announcement.getFeedbacks()) {
                        feedbackRepository.clearImages(feedback.getId());
                    }
                    announcementRepository.clearFeedback(announcement.getId());
                    announcementRepository.clearBooking(announcement.getId());
                }
                userRepository.clearAnnouncements(userId);
                userRepository.customDeleteById(userId);
            }
        } else {
            throw new ForbiddenException("Only admin can access this page!");
        }
        return new SimpleResponse("DELETE", "User successfully deleted!");
    }

    public List<UserResponse> getAllUser() {
        User currentUser = getAuthenticatedUser();
        if (currentUser.getRole().equals(Role.ADMIN)) {
            return UserProfileViewMapper.viewFindAllUser(userRepository.findAllUsers());
        } else {
            throw new ForbiddenException("Only admin can access this page!");
        }
    }

    @Override
    public Map<String, String> requestToBook(BookRequest request) {
        User user = getAuthenticatedRoleUser();
        Announcement announcement = announcementRepository.findById(request.getAnnouncementId()).orElseThrow(BadRequestException::new);

        if (request.getAnnouncementId() == null || request.getCheckIn() == null || request.getCheckOut() == null) {
            throw new BadRequestException("???? ???????????? ????????????????????");
        }

        if (request.getCheckIn().isAfter(request.getCheckOut()) ||
                request.getCheckIn().equals(request.getCheckOut()) || request.getCheckIn().isBefore(LocalDate.now())) {
            throw new BadRequestException("???????? ??????????????");
        }

        findTakenDates(request.getCheckIn(), request.getCheckOut(), announcement.getBlockedDates(), announcement.getBlockedDatesByUser());

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setAnnouncement(announcement);
        booking.setCheckin(request.getCheckIn());
        booking.setCheckout(request.getCheckOut());
        booking.setPricePerDay(announcement.getPrice());
        booking.setStatus(Status.NEW);
        booking.setCreatedAt(LocalDate.now());
        bookingRepository.save(booking);
        return Map.of("massage", "???????????? ???? ???????????????????????? ?????????????????????? ");
    }

    @Override
    public Map<String, String> blockDateByUser(BlockBookDateRequest request) {
        User user = getAuthenticatedRoleUser();
        Announcement announcement = announcementRepository.findById(request.getAnnouncementId()).orElseThrow(BadRequestException::new);

        if (!announcement.getOwner().equals(user)) {
            throw new ForbiddenException("This user not allowed to block dates of this ann");
        }

        for (int i = 0; i < request.getDatesToBlock().size(); i++) {
            if (!announcement.getBlockedDatesByUser().contains(request.getDatesToBlock().get(i))) {
                announcement.addBlockedDateByUser(request.getDatesToBlock().get(i));
            } else {
                announcement.removeBlockedDateByUser(request.getDatesToBlock().get(i));
            }
        }
        announcementRepository.save(announcement);
        return Map.of("massage", "blocked dates has been updated");
    }

    @Override
    public List<BookingCardResponse> findUsersBookings() {
        try {
            User user = getAuthenticatedUser();
            List<Booking> bookings = user.getBookings();
            return bookingViewMapper.viewCard(bookings);
        } catch (NoSuchElementException e) {
            throw new BadRequestException("There is no user ");
        }
    }

    @Override
    public Map<String, String> deleteRequestToBook(Long bookingId) {
        User user = getAuthenticatedRoleUser();
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(BadRequestException::new);

        if (!user.getId().equals(booking.getUser().getId())) {
            throw new ForbiddenException("This booking is not belong to user with id = " + user.getId());
        }

        if (booking.getStatus().equals(Status.ACCEPTED)) {
            Announcement announcement = announcementRepository.findById(booking.getAnnouncement().getId()).orElseThrow(BadRequestException::new);
            announcement.releaseTakenDates(booking.getCheckin(), booking.getCheckout());
        }
        bookingRepository.delete(booking);
        return Map.of("massage", "request to book has been deleted!");
    }

    @Override
    public Map<String, String> updateRequestToBook(UpdateBookRequest request) {
        if (request.getCheckIn().isAfter(request.getCheckOut()) ||
                request.getCheckIn().equals(request.getCheckOut()) ||
                request.getCheckIn().isBefore(LocalDate.now())) {
            throw new BadRequestException("???????? ?????????????? ??????????????!");
        }

        Booking booking = bookingRepository.findById(request.getBookingId()).orElseThrow(BadRequestException::new);
        Announcement announcement = announcementRepository.findById(request.getAnnouncementId()).orElseThrow(BadRequestException::new);
        User user = getAuthenticatedRoleUser();

        if (!booking.getUser().getId().equals(user.getId()) ||
                !booking.getAnnouncement().getId().equals(request.getAnnouncementId())) {
            throw new ForbiddenException("incorrect id");
        }

        findTakenDates(booking.getCheckin(), booking.getCheckout(), announcement.getBlockedDates(), announcement.getBlockedDatesByUser());

        if (booking.getStatus().equals(Status.ACCEPTED)) {
            booking.setStatus(Status.NEW);
            announcement.releaseTakenDates(booking.getCheckin(), booking.getCheckout());
        } else if (booking.getStatus().equals(Status.REJECTED)) {
            booking.setStatus(Status.NEW);
        }
        booking.setCheckin(request.getCheckIn());
        booking.setCheckout(request.getCheckOut());
        bookingRepository.save(booking);
        announcementRepository.save(announcement);
        return Map.of("massage", "The dates has been updated!");
    }

    @Override
    public List<BookedResponse> getAnnouncementsBookings(Long announcementId) {
        User user = getAuthenticatedRoleUser();
        Announcement announcement = announcementRepository.findById(announcementId).orElseThrow(BadRequestException::new);

        if (!user.getId().equals(announcement.getOwner().getId())) {
            throw new ForbiddenException("This ann not belongs to this user");
        }
        List<Booking> bookings = bookingRepository.findAcceptedByAnnouncementId(announcementId);
        return bookingViewMapper.viewBooked(bookings);
    }

    @Override
    public Map<String, String> changeBookingsStatus(ChangeBookingsStatusRequest request) {
        User owner = getAuthenticatedRoleUser();
        Announcement announcement = announcementRepository.findById(request.getAnnouncementId()).orElseThrow(BadRequestException::new);
        Booking booking = bookingRepository.findById(request.getBookingId()).orElseThrow(BadRequestException::new);

        if (!booking.getAnnouncement().equals(announcement) ||
                !announcement.getOwner().equals(owner)) {
            throw new ForbiddenException();
        }

        if (!request.getStatus().equals(Status.REJECTED)) {
            findTakenDates(booking.getCheckin(), booking.getCheckout(), announcement.getBlockedDates(), announcement.getBlockedDatesByUser());
        }

        if (request.getStatus().equals(Status.ACCEPTED)) {
            booking.setStatus(Status.ACCEPTED);
            announcement.addBlockedDate(booking.getCheckin(), booking.getCheckout());
            bookingRepository.save(booking);
            announcementRepository.save(announcement);
        } else if (request.getStatus().equals(Status.REJECTED)) {
            if (booking.getStatus().equals(Status.ACCEPTED)) {
                announcement.releaseTakenDates(booking.getCheckin(), booking.getCheckout());
            }
            booking.setStatus(Status.REJECTED);
            bookingRepository.save(booking);
            announcementRepository.save(announcement);
        } else if (request.getStatus().equals(Status.NEW)) {
            if (booking.getStatus().equals(Status.ACCEPTED)) {
                announcement.releaseTakenDates(booking.getCheckin(), booking.getCheckout());
            }
            booking.setStatus(Status.NEW);
            bookingRepository.save(booking);
            announcementRepository.save(announcement);
        } else {
            return Map.of("massage", "Nothing changed!");
        }
        return Map.of("massage", "booking status updated!");
    }

    @Override
    public ClosedDatesResponse getClosedDates(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId).orElseThrow(BadRequestException::new);
        ClosedDatesResponse response = new ClosedDatesResponse();
        response.setTakenDates(announcement.getBlockedDates());
        response.setDatesBlockedByVendor(announcement.getBlockedDatesByUser());
        return response;
    }

    private void findTakenDates(LocalDate checkIn, LocalDate checkOut, List<LocalDate> takenDates, List<LocalDate> takenDatesByUser) {
        takenDates.addAll(takenDatesByUser);
        while (checkIn.isBefore(checkOut)) {
            if (takenDates.contains(checkIn)) {
                throw new BadRequestException("?????????????????????????? ???????? ???????????? ???????????????????????? ????????????!");
            }
            checkIn = checkIn.plusDays(1L);
        }
        if (takenDates.contains(checkOut)) {
            throw new BadRequestException("?????????????????????????? ???????? ???????????? ???????????????????????? ????????????!");
        }
    }

}


