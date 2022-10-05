package kg.airbnb.airbnb.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.airbnb.airbnb.dto.requests.FeedbackRequest;
import kg.airbnb.airbnb.dto.responses.FeedbackRatingResponse;
import kg.airbnb.airbnb.dto.responses.FeedbackResponse;
import kg.airbnb.airbnb.services.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/feedbacks")
@CrossOrigin
@Tag(name = "This API is for Feedback")
public class FeedbackAPI {

    private final FeedbackService feedbackService;

    @Operation(summary = "Get all feedbacks of announcement")
    @GetMapping("/{announcementId}")
    public List<FeedbackResponse> findAll(@PathVariable Long announcementId,
                                          @RequestParam(required = false, defaultValue = "1") Integer page,
                                          @RequestParam(required = false, defaultValue = "3") Integer size) {
        return feedbackService.findAll(announcementId, page, size);
    }

    @Operation(summary = "Leave feedback to announcement")
    @PostMapping("/leave/feedback/{announcementId}")
    public FeedbackResponse leaveFeedback(@PathVariable Long announcementId, @RequestBody FeedbackRequest feedbackRequest) {
        return feedbackService.saveFeedback(announcementId, feedbackRequest);
    }

    @Operation(summary = "Like feedback of an announcement")
    @PostMapping("/like/{feedbackId}")
    public FeedbackResponse LikeFeedback(@PathVariable Long feedbackId) {
        return feedbackService.likeFeedback(feedbackId);
    }

    @Operation(summary = "Dislike feedback of an announcement")
    @PostMapping("/dislike/{feedbackId}")
    public FeedbackResponse DisLikeFeedback(@PathVariable Long feedbackId) {
        return feedbackService.disLikeFeedback(feedbackId);
    }

    @Operation(summary = "Get ratings of an announcement ")
    @GetMapping("/feedback/rating/with/percentages")
    public FeedbackRatingResponse getFeedbackRatingWithPercentage(Long announcementId) {
        return feedbackService.feedbackRatingWithPercentage(announcementId);
    }
}
