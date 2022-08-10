package kg.airbnb.airbnb.dto.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class UserProfileAnnouncementResponse {

    private String image;
    private String name;
    private String contact;
    private List<UserAnnouncementResponse> myAnnouncements;
}
