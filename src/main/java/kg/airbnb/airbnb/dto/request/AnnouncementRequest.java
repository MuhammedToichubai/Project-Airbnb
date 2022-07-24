package kg.airbnb.airbnb.dto.request;

import kg.airbnb.airbnb.enums.Type;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class AnnouncementRequest {
    private Long ownerId;
    private List<String> images;
    private Type houseType;
    private Integer maxGuests;
    private BigDecimal price;
    private String title;
    private String description;
    private Long regionId;
    private String townProvince;
    private String address;
}