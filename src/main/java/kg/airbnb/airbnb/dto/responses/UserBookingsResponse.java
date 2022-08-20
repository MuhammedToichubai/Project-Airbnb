package kg.airbnb.airbnb.dto.responses;

import kg.airbnb.airbnb.enums.Status;
import kg.airbnb.airbnb.mappers.announcement.AnnouncementViewMapper;
import kg.airbnb.airbnb.models.Booking;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

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


    public UserBookingsResponse(Booking booking, Double rating) {
        this.announcementId = booking.getAnnouncement().getId();
        this.image = booking.getAnnouncement().getImages().get(0);
        this.price = booking.getAnnouncement().getPrice();
        this.rating = rating;
        this.title = booking.getAnnouncement().getTitle();
        this.location = booking.getAnnouncement().getLocation().getAddress();
        this.maxGuests = booking.getAnnouncement().getMaxGuests();
    }


}
