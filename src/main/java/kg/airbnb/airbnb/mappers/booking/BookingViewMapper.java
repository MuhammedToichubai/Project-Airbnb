package kg.airbnb.airbnb.mappers.booking;

import kg.airbnb.airbnb.dto.requests.BookedRequest;
import kg.airbnb.airbnb.dto.responses.AnnouncementCardResponse;
import kg.airbnb.airbnb.dto.responses.BookedResponse;
import kg.airbnb.airbnb.dto.responses.BookingCardResponse;
import kg.airbnb.airbnb.enums.Status;
import kg.airbnb.airbnb.models.Announcement;
import kg.airbnb.airbnb.models.Booking;
import kg.airbnb.airbnb.models.Feedback;
import kg.airbnb.airbnb.models.auth.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
        response.setPrice(booking.getAnnouncement().getPrice());
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

    public BookedResponse viewBooked(BookedRequest request) {

        if (request == null) {
            return null;
        }

        BookedResponse response = new BookedResponse();
        response.setPrice(request.getPrice());
        response.setCheckIn(request.getBooking().getCheckin());
        response.setCheckOut(request.getBooking().getCheckout());
        response.setUserName(request.getBooking().getUser().getFullName());
        response.setUserEmail(request.getBooking().getUser().getEmail());
        response.setUserImage(request.getBooking().getUser().getImage());
        return response;
    }

    public List<BookedResponse> viewBooked(List<BookedRequest> requests) {
        List<BookedResponse> responses = new ArrayList<>();
        for (BookedRequest a : requests) {
            responses.add(viewBooked(a));
        }
        return responses;
    }

}
