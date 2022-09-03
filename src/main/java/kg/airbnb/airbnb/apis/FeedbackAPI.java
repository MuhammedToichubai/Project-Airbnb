package kg.airbnb.airbnb.apis;

import kg.airbnb.airbnb.dto.requests.FeedbackRequest;
import kg.airbnb.airbnb.dto.responses.FeedbackRatingResponse;
import kg.airbnb.airbnb.dto.responses.FeedbackResponse;
import kg.airbnb.airbnb.dto.responses.SimpleResponse;
import kg.airbnb.airbnb.models.Feedback;
import kg.airbnb.airbnb.services.FeedbackService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/feedbacks")
@CrossOrigin
public class FeedbackAPI {

    private final FeedbackService feedbackService;

    public FeedbackAPI(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/{announcementId}")
    public List<FeedbackResponse> findAll(@PathVariable Long announcementId,@RequestParam Integer page, @RequestParam Integer size){
        return feedbackService.findAll(announcementId,page,size);
    }

    @PostMapping("/leave/feedback/{announcementId}")
    public FeedbackResponse leaveFeedback(@PathVariable Long announcementId, @RequestBody FeedbackRequest feedbackRequest) {
        return feedbackService.saveFeedback(announcementId, feedbackRequest);
    }

    @PostMapping("/like/{feedbackId}")
    public FeedbackResponse LikeFeedback(@PathVariable Long feedbackId){
        return feedbackService.likeFeedback(feedbackId);
    }

    @PostMapping("/disLike/{feedbackId}")
    public FeedbackResponse DisLikeFeedback(@PathVariable Long feedbackId){
        return feedbackService.disLikeFeedback(feedbackId);
    }

    @GetMapping("/feedback/rating/with/percentages")
    public FeedbackRatingResponse getFeedbackRatingWithPercentage(Long announcementId){
        return feedbackService.feedbackRatingWithPercentage(announcementId);
    }
}
