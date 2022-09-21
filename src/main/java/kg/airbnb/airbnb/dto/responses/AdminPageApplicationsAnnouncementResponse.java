package kg.airbnb.airbnb.dto.responses;
import kg.airbnb.airbnb.enums.Status;
import kg.airbnb.airbnb.enums.Type;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class AdminPageApplicationsAnnouncementResponse {

    private Long announcementId;

    private String title;

    private List<String> images;

    private Type houseType;

    private Integer maxGuests;

    private String location;

    private String description;

    private String ownerImage;

    private String ownerFullName;

    private String ownerEmail;

    private String ownerPhoneNumber;

    private Status status;

}
