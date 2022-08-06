package kg.airbnb.airbnb.dto.responses;

import kg.airbnb.airbnb.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class UserAnnouncementResponse {

    private String image;
    private BigDecimal price;
    private Double rating;
    private String title;
    private String location;
    private Integer maxGuests;
    private Status status;
}
