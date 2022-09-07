package kg.airbnb.airbnb.mappers.booking;

import kg.airbnb.airbnb.dto.responses.AnnouncementCardResponse;
import kg.airbnb.airbnb.dto.responses.BookingCardResponse;
import kg.airbnb.airbnb.enums.Status;
import kg.airbnb.airbnb.models.Announcement;
import kg.airbnb.airbnb.models.Booking;
import kg.airbnb.airbnb.models.Feedback;
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
}
