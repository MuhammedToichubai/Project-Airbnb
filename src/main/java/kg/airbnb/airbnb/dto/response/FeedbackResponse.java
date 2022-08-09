package kg.airbnb.airbnb.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
    private AtomicInteger likes;
    private AtomicInteger disLikes;
}