package kg.airbnb.airbnb.dto.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackRatingResponse {

    private double rating;
    private double percentageOfFive;
    private double percentageOfFour;
    private double percentageOfThree;
    private double percentageOfTwo;
    private double percentageOfOne;

}
