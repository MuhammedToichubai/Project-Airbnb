package kg.airbnb.airbnb.dto.response;

import kg.airbnb.airbnb.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
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
   private Status status;



}
