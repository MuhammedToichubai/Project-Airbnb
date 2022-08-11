package kg.airbnb.airbnb.mappers.booking;

import kg.airbnb.airbnb.dto.responses.UserBookingsResponse;
import kg.airbnb.airbnb.models.Booking;

import java.util.ArrayList;
import java.util.List;

public class BookingViewMapper {

    public static List<UserBookingsResponse> viewAllBooking(List<Booking> bookings) {
        List<UserBookingsResponse> userBookingsResponseList = new ArrayList<>();
        for (Booking booking : bookings) {
            userBookingsResponseList.add(new UserBookingsResponse(booking, 23.2));
        }
        return userBookingsResponseList;
    }
}
