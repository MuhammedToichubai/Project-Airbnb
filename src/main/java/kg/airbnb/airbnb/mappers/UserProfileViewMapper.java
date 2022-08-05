package kg.airbnb.airbnb.mappers;

import kg.airbnb.airbnb.dto.response.UserAnnouncementResponse;
import kg.airbnb.airbnb.dto.response.UserBookingsResponse;
import kg.airbnb.airbnb.dto.response.UserProfileAnnouncementResponse;
import kg.airbnb.airbnb.dto.response.UserProfileBookingResponse;
import kg.airbnb.airbnb.enums.Status;
import kg.airbnb.airbnb.models.Announcement;
import kg.airbnb.airbnb.models.Booking;
import kg.airbnb.airbnb.models.Feedback;
import kg.airbnb.airbnb.models.auth.User;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserProfileViewMapper {

    public UserProfileAnnouncementResponse entityToDtoForAnnouncements(User user){
        if(user == null){
            return null;
        }
        UserProfileAnnouncementResponse response = new UserProfileAnnouncementResponse();
        response.setImage(user.getImage());
        response.setName(user.getFullName());
        response.setContact(user.getEmail());
        response.setMyAnnouncements(listUserAnnouncements(user.getAnnouncements()));
        return response;
    }

    public UserAnnouncementResponse announcementToAnnouncementsResponse(Announcement announcement){
        if (announcement == null){
            return null;
        }
        UserAnnouncementResponse announcementsResponse = new UserAnnouncementResponse();
        announcementsResponse.setImage(announcement.getImages().get(0));
        announcementsResponse.setPrice(announcement.getPrice());
        announcementsResponse.setRating(calculateRating(announcement));
        announcementsResponse.setTitle(announcement.getTitle());
        announcementsResponse.setLocation(announcement.getLocation().getAddress());
        announcementsResponse.setMaxGuests(announcement.getMaxGuests());
        announcementsResponse.setStatus(Status.NEW);
        return announcementsResponse;
    }

    public List<UserAnnouncementResponse> listUserAnnouncements(List<Announcement> announcements){
        List<UserAnnouncementResponse> responses = new ArrayList<>();
        for (Announcement announcement : announcements) {
            responses.add(announcementToAnnouncementsResponse(announcement));
        }
        return responses;
    }

    public UserProfileBookingResponse entityToDtoForBookings(User user){
        if(user == null){
            return null;
        }
        UserProfileBookingResponse response = new UserProfileBookingResponse();
        response.setImage(user.getImage());
        response.setName(user.getFullName());
        response.setContact(user.getEmail());
        response.setBookings(listUserBookings(user.getBookings()));
        return response;
    }


    public List<UserBookingsResponse> listUserBookings(List<Booking> bookings){
        List<UserBookingsResponse> responses = new ArrayList<>();
        for (Booking booking : bookings) {
            responses.add(bookingsToBookingResponse(booking));
        }
        return responses;
    }

    public UserBookingsResponse bookingsToBookingResponse(Booking booking) {
        if (booking == null){
            return null;
        }
        UserBookingsResponse bookingsResponse = new UserBookingsResponse();
        bookingsResponse.setAnnouncementId(booking.getAnnouncement().getId());
        bookingsResponse.setPrice(booking.getAnnouncement().getPrice());
        bookingsResponse.setRating(calculateRating(booking.getAnnouncement()));

        bookingsResponse.setTitle(booking.getAnnouncement().getTitle());
        bookingsResponse.setLocation(booking.getAnnouncement().getLocation().getAddress());
        bookingsResponse.setMaxGuests(booking.getAnnouncement().getMaxGuests());
        bookingsResponse.setCheckIn(booking.getCheckin().format(DateTimeFormatter.ISO_LOCAL_DATE));
        bookingsResponse.setCheckOut(booking.getCheckout().format(DateTimeFormatter.ISO_LOCAL_DATE));
        bookingsResponse.setStatus(Status.NEW);
        return bookingsResponse;

    }

    public Double calculateRating(Announcement announcement){

        double rating = 0.0;
        int sumOfTotalRatings = 0;
        int fives = 0;
        int fours = 0;
        int threes = 0;
        int twos = 0;
        int  ones = 0;

        List<Feedback> feedbacks= announcement.getFeedbacks();

        if (feedbacks.size()<=0){
            rating = 0;
        }

        sumOfTotalRatings = feedbacks.size();

        for (Feedback feedback:feedbacks) {
            if(feedback.getRating() == 5) {
                fives++;
            }else if(feedback.getRating() == 4) {
                fours++;
            }else if (feedback.getRating() == 3){
                threes++;
            }else if(feedback.getRating() == 2){
                twos++;
            }else if(feedback.getRating() == 1){
                ones++;
            }
            //formula of getting rating of announcement
            rating = (5*fives+4*fours+3*threes+2*twos+ones)/(double)(sumOfTotalRatings);
        }
        return rating;
    }


}
