package kg.airbnb.airbnb.apis;

import kg.airbnb.airbnb.dto.request.FeedbackRequest;
import kg.airbnb.airbnb.dto.request.LikeRequest;
import kg.airbnb.airbnb.dto.response.FeedbackResponse;
import kg.airbnb.airbnb.dto.response.SimpleResponse;
import kg.airbnb.airbnb.services.FeedbackService;
import org.springframework.http.HttpStatus;
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

    //User
    //GetAll
    @GetMapping("/{announcementId}")
    public List<FeedbackResponse> getAllAnnouncementFeedbacks(@PathVariable Long announcementId) {
        return feedbackService.getAllFeedback(announcementId);
    }

    //User
    @PostMapping("/leave/feedback/{announcementId}")
    @ResponseStatus(HttpStatus.OK)
    public SimpleResponse leaveFeedback(@PathVariable Long announcementId, @RequestBody FeedbackRequest feedbackRequest) {
        return feedbackService.saveFeedback(announcementId, feedbackRequest);
    }

    //User
    @PostMapping("/{feedbackId}/like")
    public FeedbackResponse saveLikesFeedback(@PathVariable Long feedbackId){
        return feedbackService.likeFeedback(feedbackId);

    }

    @PostMapping("/{feedbackId}/disLike")
    public FeedbackResponse saveDisLikesFeedback(@PathVariable Long feedbackId){
        feedbackService.disLikeFeedback(feedbackId);
        return null;
    }

}
