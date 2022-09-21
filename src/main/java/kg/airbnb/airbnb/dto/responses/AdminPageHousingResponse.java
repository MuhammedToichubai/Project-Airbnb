package kg.airbnb.airbnb.dto.responses;

import kg.airbnb.airbnb.enums.Status;
import kg.airbnb.airbnb.enums.Type;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AdminPageHousingResponse {

    private Long announcementId;

    private String title;

    private List<String> images;

    private Type houseType;

    private Double rating;

    private BigDecimal price;

    private Integer maxGuests;

    private String location;

    private String description;

    private Status status;

    private LocalDate createdAt;

}
