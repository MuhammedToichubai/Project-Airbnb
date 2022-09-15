package kg.airbnb.airbnb.dto.responses;

import kg.airbnb.airbnb.enums.Status;
import kg.airbnb.airbnb.enums.Type;
import kg.airbnb.airbnb.models.Announcement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAnnouncementResponse {
    private Long id;
    private String image;
    private Type houseType;
    private BigDecimal price;
    private Double rating;
    private String title;
    private String description;
    private String location;
    private Integer maxGuests;
    private Status status;
    private Integer likeCountAnnouncement;
    private Integer bookmarkCountAnnouncement;
    private Integer bookingsCountAnnouncement;
    private String messagesFromAdmin;

}
