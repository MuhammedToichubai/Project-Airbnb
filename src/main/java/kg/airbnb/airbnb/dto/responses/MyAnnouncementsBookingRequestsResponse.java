package kg.airbnb.airbnb.dto.responses;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MyAnnouncementsBookingRequestsResponse {

    private Long announcementId;
    private String title;
    private String description;
    private List<String> images = new ArrayList<>();
    private BigDecimal price;
    private Integer maxGuests;
    private String location;
    private double rating;
    private String type;
    private List<BookedResponse> bookedResponses;
}
