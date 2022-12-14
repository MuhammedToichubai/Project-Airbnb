package kg.airbnb.airbnb.dto.responses;

import kg.airbnb.airbnb.enums.Type;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class AnnouncementInnerPageResponse {

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
    private String location;
    private Long userID;
    private String ownerImage;
    private String ownerFullName;
    private String ownerEmail;
    private String ownerPhoneNumber;
    private Integer likeCount;
    private Integer bookmarkCount;
    private Integer viewAnnouncementCount;
    private String colorOfLike;
    private String colorOfBookmark;
    private List<BookedResponse> announcementBookings;

}
