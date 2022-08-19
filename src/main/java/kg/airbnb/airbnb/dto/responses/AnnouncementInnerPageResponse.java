package kg.airbnb.airbnb.dto.responses;

import kg.airbnb.airbnb.enums.Type;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnnouncementInnerPageResponse {
    private Long id;
    private List<String> images;
    private Type houseType;
    private Integer maxGuests;
    private String title;
    private String location;
    private String description;
    private String ownerImage;
    private String ownerFullName;
    private String ownerEmail;
    private Integer likeCount;
    private Integer bookmarkCount;
    private Integer viewAnnouncementCount;
}
