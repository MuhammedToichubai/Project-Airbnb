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
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteAnnouncementResponse {

    private Long id;
    private List<String> image;
    private BigDecimal price;
    private Double rating;
    private String title;
    private String location;
    private Integer maxGuests;
    private Integer likeCount;
    private Integer bookmarkCount;
    private Status status;

}
