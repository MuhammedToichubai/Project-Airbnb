package kg.airbnb.airbnb.dto.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AdminPageApplicationsResponse {

    private Integer allAnnouncementsSize;
    private List<AdminPageAnnouncementResponse> pageAnnouncementResponseList;
}
