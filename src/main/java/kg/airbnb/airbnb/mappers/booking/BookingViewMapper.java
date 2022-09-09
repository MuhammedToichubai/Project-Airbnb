package kg.airbnb.airbnb.mappers.booking;

import kg.airbnb.airbnb.dto.responses.BookingResponse;
import kg.airbnb.airbnb.models.Booking;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookingViewMapper {

    public BookingResponse entityToDTO(Booking booking) {
        if (booking == null){
            return null;
        }
        BookingResponse response = new BookingResponse(
                booking.getId(),
                booking.getUser(),
                booking.getCheckin(),
                booking.getCheckout(),
                booking.getStatus(),
                booking.getStatus()
        );
        return response;
    }

    public List<BookingResponse> viewAllBookingsResponse(List<Booking> bookings){
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (Booking booking : bookings) {
            bookingResponses.add(entityToDTO(booking));
        }
      return bookingResponses;
    }
}
