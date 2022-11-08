package kg.airbnb.airbnb.dto.responses;

import kg.airbnb.airbnb.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminPageAnnouncementResponse {

    private Long announcementId;
    private String title;
    private List<String> images;
    private BigDecimal price;
    private Integer maxGuests;
    private Double rating;
    private String location;
    private Status status;

}
