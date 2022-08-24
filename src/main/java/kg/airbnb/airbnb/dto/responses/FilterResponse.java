package kg.airbnb.airbnb.dto.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FilterResponse {

    private List<AnnouncementCardResponse> responses;
    private Long countOfResult;
}
