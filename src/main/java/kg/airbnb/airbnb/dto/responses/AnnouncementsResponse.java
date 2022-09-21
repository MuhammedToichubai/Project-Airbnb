package kg.airbnb.airbnb.dto.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class AnnouncementsResponse {

    private Long countOfResult;

    private List<AnnouncementCardResponse> announcements;
}
