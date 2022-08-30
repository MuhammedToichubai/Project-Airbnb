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
public class AnnouncementInnerPageResponse {
    private Long id;
    private List<String> images;
    private Type houseType;
    private Integer maxGuests;
    private BigDecimal price;
    private String title;
    private String description;
<<<<<<< HEAD
    private BigDecimal price;
    private Long userID;
    private String ownerImage;
    private String ownerFullName;
    private String ownerEmail;
    private Integer likeCount;
    private Integer bookmarkCount;
    private Integer viewAnnouncementCount;
    private String colorOfLike;
    private String colorOfBookmark;
=======
    private Long regionId;
    private String RegionName;
    private String townProvince;
    private String address;
>>>>>>> 88be080ca19a035331b4714ede38c8c490b4a1f7
}
