package kg.airbnb.airbnb.dto.responses;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
public class GlobalSearchForAnnouncementResponse {
    private List<String> images;
    private BigDecimal price;
    private Double rating;
    private String description;
    private String address;
    private Integer guests;
}
