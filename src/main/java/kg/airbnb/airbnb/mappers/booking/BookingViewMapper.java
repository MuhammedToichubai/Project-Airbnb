package kg.airbnb.airbnb.mappers.booking;

import kg.airbnb.airbnb.dto.responses.BookedResponse;
import kg.airbnb.airbnb.dto.responses.BookingCardResponse;
import kg.airbnb.airbnb.dto.responses.MyAnnouncementsBookingRequestsResponse;
import kg.airbnb.airbnb.db.model.Announcement;
import kg.airbnb.airbnb.db.model.Booking;
import kg.airbnb.airbnb.db.model.Feedback;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookingViewMapper {

    public BookingCardResponse viewCardBooking(Booking booking) {
        if (booking == null) {
            return null;
        }
        BookingCardResponse response = new BookingCardResponse();
        response.setId(booking.getAnnouncement().getId());
        response.setBookingId(booking.getId());
        response.setTitle(booking.getAnnouncement().getTitle());
        response.setDescription(booking.getAnnouncement().getDescription());
        response.setPrice(booking.getPricePerDay());
        response.setMaxGuests(booking.getAnnouncement().getMaxGuests());
        response.setLocation(booking.getAnnouncement().getLocation().getAddress() + ", " +
                booking.getAnnouncement().getLocation().getCity() + ", " +
                booking.getAnnouncement().getLocation().getRegion().getRegionName());
        response.setImages(booking.getAnnouncement().getImages());
        double a = 0;
        double b = 0;
        for (Feedback f : booking.getAnnouncement().getFeedbacks()) {
            a = a + f.getRating();
            b++;
        }
        double rating = a / b;
        response.setRating(rating);
        response.setType(String.valueOf(booking.getAnnouncement().getHouseType()));
        response.setStatus(booking.getStatus());
        response.setCheckIn(booking.getCheckin());
        response.setCheckOut(booking.getCheckout());
        return response;
    }

    public List<BookingCardResponse> viewCard(List<Booking> bookings) {
        List<BookingCardResponse> responses = new ArrayList<>();
        for (Booking booking : bookings) {
            responses.add(viewCardBooking(booking));
        }
        return responses;
    }

    public BookedResponse viewBooked(Booking request) {
        if (request == null) {
            return null;
        }
        BookedResponse response = new BookedResponse();
        response.setBookingId(request.getId());
        response.setPrice(request.getPricePerDay());
        response.setCheckIn(request.getCheckin());
        response.setCheckOut(request.getCheckout());
        response.setStatus(request.getStatus().name());
        response.setUserName(request.getUser().getFullName());
        response.setUserEmail(request.getUser().getEmail());
        response.setUserImage(request.getUser().getImage());
        response.setUserPhoneNumber(request.getUser().getPhoneNumber());
        return response;
    }

    public List<BookedResponse> viewBooked(List<Booking> requests) {
        List<BookedResponse> responses = new ArrayList<>();
        for (Booking a : requests) {
            responses.add(viewBooked(a));
        }
        return responses;
    }

    public MyAnnouncementsBookingRequestsResponse viewUsersRequests(Announcement a, List<Booking> bookings) {
        if (a == null) {
            return null;
        }
        MyAnnouncementsBookingRequestsResponse response = new MyAnnouncementsBookingRequestsResponse();
        response.setAnnouncementId(a.getId());
        response.setTitle(a.getTitle());
        response.setDescription(a.getDescription());
        response.setImages(a.getImages());
        response.setPrice(a.getPrice());
        response.setMaxGuests(a.getMaxGuests());
        response.setLocation(a.getLocation().getAddress() + ", " +
                a.getLocation().getCity() + ", " +
                a.getLocation().getRegion().getRegionName());
        double b = 0;
        double c = 0;
        for (Feedback f : a.getFeedbacks()) {
            if (f.getRating() != null) {
                b = b + f.getRating();
                c++;
            }
        }
        double rating = b / c;
        response.setRating(rating);
        response.setType(a.getHouseType().name());
        response.setBookedResponses(viewBooked(bookings));
        return response;
    }

}
