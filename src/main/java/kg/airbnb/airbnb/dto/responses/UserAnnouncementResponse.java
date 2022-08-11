package kg.airbnb.airbnb.dto.responses;

import kg.airbnb.airbnb.enums.Status;
import kg.airbnb.airbnb.models.Announcement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class UserAnnouncementResponse {
    private Long id;
    private String image;
    private BigDecimal price;
    private Double rating;
    private String title;
    private String location;
    private Integer maxGuests;
    private Status status;

    public UserAnnouncementResponse(Announcement announcement,Double rating) {
        this.id = announcement.getId();
        this.image = announcement.getImages().get(0);
        this.price = announcement.getPrice();
        this.rating = rating;
        this.title = announcement.getTitle();
        this.location = announcement.getLocation().getAddress();
        this.maxGuests = announcement.getMaxGuests();
        this.status = announcement.getStatus();
    }
}
