package kg.airbnb.airbnb.services.impl;

import kg.airbnb.airbnb.dto.requests.BlockBookDateRequest;
import kg.airbnb.airbnb.dto.requests.BookRequest;
import kg.airbnb.airbnb.dto.requests.BookedRequest;
import kg.airbnb.airbnb.dto.responses.*;
import kg.airbnb.airbnb.enums.Role;
import kg.airbnb.airbnb.enums.Status;
import kg.airbnb.airbnb.exceptions.BadRequestException;
import kg.airbnb.airbnb.exceptions.ForbiddenException;
import kg.airbnb.airbnb.mappers.booking.BookingViewMapper;
import kg.airbnb.airbnb.mappers.user.UserProfileViewMapper;
import kg.airbnb.airbnb.models.Announcement;
import kg.airbnb.airbnb.models.Booking;
import kg.airbnb.airbnb.models.auth.User;
import kg.airbnb.airbnb.repositories.AnnouncementRepository;
import kg.airbnb.airbnb.repositories.BookingRepository;
import kg.airbnb.airbnb.repositories.UserRepository;
import kg.airbnb.airbnb.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AnnouncementRepository announcementRepository;
    private final BookingRepository bookingRepository;
    private final UserProfileViewMapper viewMapper;
    private final BookingViewMapper bookingViewMapper;

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
    public UserProfileResponse getUserBookingsAndAnnouncements() {
        User user = getAuthenticatedUser();
        return viewMapper.entityToDto(user);
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> new ForbiddenException("An unregistered user cannot write comment for this announcement!"));
    }

    public SimpleResponse deleteUser(Long id) {
        User users = getAuthenticatedUser();
        if (users.getRole().equals(Role.ADMIN)) {
            User user = userRepository.findById(id).get();
            userRepository.delete(user);
            return new SimpleResponse("Пользователь успешно удалён!");
        } else {
            throw new ForbiddenException("Only admin can access this page!");
        }
    }

    public List<UserResponse> getAllUser() {
        User user = getAuthenticatedUser();
        if (user.getRole().equals(Role.ADMIN)) {
            return UserProfileViewMapper.viewFindAllUser(userRepository.findAll());
        } else {
            throw new ForbiddenException("Only admin can access this page!");
        }
    }

    @Override
    public Map<String, String> requestToBook(BookRequest request) {

        if (request.getCheckIn().isAfter(request.getCheckOut()) ||
                request.getCheckIn().equals(request.getCheckOut()) || request.getCheckIn().isBefore(LocalDate.now())) {
            throw new BadRequestException("дата заселения не может быть после даты выселения");
        }

        List<LocalDate> datesForBook = findIntervalDates(request.getCheckIn(), request.getCheckOut());

        User client = userRepository.findById(request.getClientId()).get();
        Announcement announcement = announcementRepository.findById(request.getAnnouncementId()).get();

        for (LocalDate localDate : datesForBook) {
            if (announcement.getBlockedDatesByUser().contains(localDate)) {
                throw new BadRequestException("промежуточные даты вышего бронирования заняты Хозяином");
            }
        }

        for (LocalDate localDate : datesForBook) {
            if (announcement.getBlockedDates().contains(localDate)) {
                throw new BadRequestException("промежуточные даты вышего бронирования заняты");
            }
        }

        Booking booking = new Booking();
        booking.setUser(client);
        booking.setAnnouncement(announcement);
        booking.setCheckin(request.getCheckIn());
        booking.setCheckout(request.getCheckOut());
        booking.setStatus(Status.NEW);
        booking.setCreatedAt(LocalDate.now());

        bookingRepository.save(booking);

        return Map.of("massage", "заявка на бронирование отправилась ");
    }

    @Override
    public Map<String, String> blockDateByUser(BlockBookDateRequest request) {

        User user = userRepository.findById(request.getOwnerId()).get();
        Announcement announcement = announcementRepository.findById(request.getAnnouncementId()).get();

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

        return Map.of("massage", "ok");
    }

    @Override
    public List<BookingCardResponse> findUsersBookings(Long userId) {
        try {
            User user = userRepository.findById(userId).get();
            List<Booking> bookings = user.getBookings();
            return bookingViewMapper.viewCard(bookings);
        } catch (NoSuchElementException e) {
            throw new BadRequestException("There is no user with id {" + userId + "} ");
        }
    }

    @Override
    public Map<String, String> deleteRequestToBook(Long userId, Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId).get();

        if (!userId.equals(booking.getUser().getId())) {
            throw new ForbiddenException("This booking is not belong to user with id = " + userId);
        }

        bookingRepository.delete(booking);

        return Map.of("massage", "request to book has been deleted!");
    }

    @Override
    public Map<String, String> updateRequestToBook(BookRequest request) {

        if (request.getCheckIn().isAfter(request.getCheckOut()) ||
                request.getCheckIn().equals(request.getCheckOut()) || request.getCheckIn().isBefore(LocalDate.now())) {
            throw new BadRequestException("дата заселения не может быть после даты выселения");
        }

        List<LocalDate> datesForBook = findIntervalDates(request.getCheckIn(), request.getCheckOut());

        Booking booking = bookingRepository.findById(request.getBookingId()).get();
        Announcement announcement = announcementRepository.findById(request.getAnnouncementId()).get();

        if (!booking.getUser().getId().equals(request.getClientId()) ||
             !booking.getAnnouncement().getId().equals(request.getAnnouncementId())) {
            throw new ForbiddenException("incorrect id");
        }

        for (LocalDate localDate : datesForBook) {
            if (announcement.getBlockedDatesByUser().contains(localDate)) {
                throw new BadRequestException("промежуточные даты вышего бронирования заняты Хозяином");
            }
        }

        for (LocalDate localDate : datesForBook) {
            if (announcement.getBlockedDates().contains(localDate)) {
                throw new BadRequestException("промежуточные даты вышего бронирования заняты");
            }
        }

        booking.setCheckin(request.getCheckIn());
        booking.setCheckout(request.getCheckOut());

        bookingRepository.save(booking);

        return Map.of("massage", "The dates has been updated!");
    }

    @Override
    public List<BookedResponse> getAnnouncementsBookings(Long userId, Long announcementId) {

        User user = userRepository.findById(userId).get();
        Announcement announcement = announcementRepository.findById(announcementId).get();

        if (!user.getId().equals(announcement.getOwner().getId())) {
            throw new ForbiddenException("This ann not belongs to this user");
        }

        List<Booking> bookings = bookingRepository.findByAnnouncementId(announcementId);

        List<BookedRequest> requests = new ArrayList<>();

        for (Booking b: bookings) {
            BookedRequest bookedRequest = new BookedRequest();
            bookedRequest.setBooking(b);
            bookedRequest.setPrice(announcement.getPrice());
            requests.add(bookedRequest);
        }

        return bookingViewMapper.viewBooked(requests);
    }

    @Override
    public Map<String, String> acceptRequestToBook(BookRequest request) {

        User user = userRepository.findById(request.getClientId()).get();
        Announcement announcement = announcementRepository.findById(request.getAnnouncementId()).get();
        Booking booking = bookingRepository.findById(request.getBookingId()).get();

        if (!booking.getAnnouncement().equals(announcement) ||
        !announcement.getOwner().equals(user)) {
            throw new ForbiddenException();
        }

        booking.setStatus(Status.ACCEPTED);
        announcement.addBlockedDate(findIntervalDates(booking.getCheckin(), booking.getCheckout()));

        bookingRepository.save(booking);
        announcementRepository.save(announcement);

        return Map.of("massage", "Request to book accepted");
    }

    @Override
    public Map<String, String> rejectRequestToBook(BookRequest request) {

        User user = userRepository.findById(request.getClientId()).get();
        Announcement announcement = announcementRepository.findById(request.getAnnouncementId()).get();
        Booking booking = bookingRepository.findById(request.getBookingId()).get();

        if (!booking.getAnnouncement().equals(announcement) ||
                !announcement.getOwner().equals(user)) {
            throw new ForbiddenException();
        }

        booking.setStatus(Status.REJECTED);

        bookingRepository.save(booking);

        return Map.of("massage", "Request to book rejected");
    }

    private List<LocalDate> findIntervalDates(LocalDate checkIn, LocalDate checkOut) {
        List<LocalDate> datesForBook = new ArrayList<>();
        while (checkIn.isBefore(checkOut)) {
            datesForBook.add(checkIn);
            checkIn = checkIn.plusDays(1L);
        }

        datesForBook.add(checkOut);

        System.out.println(datesForBook);
        return datesForBook;
    }
}


