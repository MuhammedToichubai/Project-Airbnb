package kg.airbnb.airbnb.dto.responses;

import kg.airbnb.airbnb.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementSaveResponse {

    private String message;
    private Long id;
    private List<String> images;
    private Type houseType;
    private Integer maxGuests;
    private BigDecimal price;
    private String title;
    private String description;
    private Long regionId;
    private String RegionName;
    private String townProvince;
    private String address;

}
