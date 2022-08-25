package kg.airbnb.airbnb.dto.responses;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AnnouncementCardResponse {

    private Long id;
    private String title;
    private String description;
    private List<String> images = new ArrayList<>();
    private BigDecimal price;
    private Integer maxGuests;
    private String location;
    private double rating;

}
