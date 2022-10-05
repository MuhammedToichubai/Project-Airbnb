package kg.airbnb.airbnb.dto.responses;

import kg.airbnb.airbnb.enums.Status;
import kg.airbnb.airbnb.models.Booking;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class UserBookingsResponse {

    private Long announcementId;
    private String image;
    private BigDecimal price;
    private Double rating;
    private String title;
    private String location;
    private Integer maxGuests;
    private String checkIn;
    private String checkOut;
    private Status Status;

}
