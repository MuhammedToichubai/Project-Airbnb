package kg.airbnb.airbnb.dto.responses;

import kg.airbnb.airbnb.models.Announcement;
import kg.airbnb.airbnb.models.Feedback;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class RatingOfAnnouncementResponse {
    private Double sumOfTotalRating;
    private Integer five;
    private Integer four;
    private Integer three;
    private Integer two;
    private Integer one;

    public Double getAverageOfRatings(Announcement announcement){
        sumOfTotalRating = 0.0;
        five = 0;
        four = 0;
        three = 0;
        two = 0;
        one = 0;

        FeedbackResponse feedbackResponse = new FeedbackResponse();
        List<Feedback> feedbacks = announcement.getFeedbacks();
        feedbackResponse.getRating();
        List<Integer> responses = new ArrayList<>();
        for (int i = 0; i < feedbacks.size(); i++) {
            responses.set(i, feedbackResponse.getRating());
        }


return sumOfTotalRating;
    }
}
