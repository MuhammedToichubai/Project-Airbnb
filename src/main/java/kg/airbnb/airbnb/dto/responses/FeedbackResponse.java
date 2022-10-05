package kg.airbnb.airbnb.dto.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class FeedbackResponse {

    private Long id;
    private String feedbackOwnerImage;
    private String feedbackOwnerFullName;
    private Integer rating;
    private String description;
    private List<String> images;
    private LocalDate createdAt;
    private Integer likeCount;
    private Integer disLikeCount;
    private String colorOfLike;
    private String colorOfDisLike;

}