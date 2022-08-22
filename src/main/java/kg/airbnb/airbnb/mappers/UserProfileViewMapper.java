package kg.airbnb.airbnb.mappers;


import kg.airbnb.airbnb.dto.responses.UserAnnouncementResponse;
import kg.airbnb.airbnb.dto.responses.UserBookingsResponse;
import kg.airbnb.airbnb.dto.responses.UserProfileResponse;
import kg.airbnb.airbnb.dto.responses.UserResponse;
import kg.airbnb.airbnb.enums.Status;
import kg.airbnb.airbnb.mappers.announcement.AnnouncementViewMapper;
import kg.airbnb.airbnb.models.Announcement;
import kg.airbnb.airbnb.models.Booking;
import kg.airbnb.airbnb.models.auth.User;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserProfileViewMapper {

    private final AnnouncementViewMapper announcementViewMapper;

    public UserProfileViewMapper(AnnouncementViewMapper announcementViewMapper) {
        this.announcementViewMapper = announcementViewMapper;
    }

    public UserAnnouncementResponse announcementToAnnouncementsResponse(Announcement announcement) {
        if (announcement == null) {
            return null;
        }
        UserAnnouncementResponse announcementsResponse = new UserAnnouncementResponse();
        announcementsResponse.setImage(announcement.getImages().get(0));
        announcementsResponse.setPrice(announcement.getPrice());
        announcementsResponse.setRating(announcementViewMapper.calculateRating(announcement));
        announcementsResponse.setTitle(announcement.getTitle());
        announcementsResponse.setLocation(announcement.getLocation().getAddress());
        announcementsResponse.setMaxGuests(announcement.getMaxGuests());
        announcementsResponse.setStatus(Status.NEW);
        return announcementsResponse;
    }

    public List<UserAnnouncementResponse> listUserAnnouncements(List<Announcement> announcements) {
        List<UserAnnouncementResponse> responses = new ArrayList<>();
        for (Announcement announcement : announcements) {
            responses.add(announcementToAnnouncementsResponse(announcement));
        }
        return responses;
    }

    public UserProfileResponse entityToDto(User user) {
        if (user == null) {
            return null;
        }
        UserProfileResponse response = new UserProfileResponse();
        response.setImage(user.getImage());
        response.setName(user.getFullName());
        response.setContact(user.getEmail());
        response.setBookings(listUserBookings(user.getBookings()));
        response.setAnnouncements(listUserAnnouncements(user.getAnnouncements()));
        return response;
    }


    public List<UserBookingsResponse> listUserBookings(List<Booking> bookings) {
        List<UserBookingsResponse> responses = new ArrayList<>();
        for (Booking booking : bookings) {
            responses.add(bookingsToBookingResponse(booking));
        }
        return responses;
    }

    public UserBookingsResponse bookingsToBookingResponse(Booking booking) {
        if (booking == null) {
            return null;
        }
        UserBookingsResponse bookingsResponse = new UserBookingsResponse();
        bookingsResponse.setAnnouncementId(booking.getAnnouncement().getId());
        bookingsResponse.setPrice(booking.getAnnouncement().getPrice());
        bookingsResponse.setRating(announcementViewMapper.calculateRating(booking.getAnnouncement()));
        bookingsResponse.setTitle(booking.getAnnouncement().getTitle());
        bookingsResponse.setLocation(booking.getAnnouncement().getLocation().getAddress());
        bookingsResponse.setMaxGuests(booking.getAnnouncement().getMaxGuests());
        bookingsResponse.setCheckIn(booking.getCheckin().format(DateTimeFormatter.ISO_LOCAL_DATE));
        bookingsResponse.setCheckOut(booking.getCheckout().format(DateTimeFormatter.ISO_LOCAL_DATE));
        bookingsResponse.setStatus(Status.NEW);
        return bookingsResponse;

    }

    public static List<UserResponse> viewFindAllUser(List<User> users) {
        List<UserResponse> userResponsesList = new ArrayList<>();
        for (User user : users) {
            userResponsesList.add(new UserResponse(user));
        }
        return userResponsesList;
    }
}
