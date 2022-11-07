package kg.airbnb.airbnb.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.airbnb.airbnb.dto.requests.FeedbackRequest;
import kg.airbnb.airbnb.dto.responses.FeedbackRatingResponse;
import kg.airbnb.airbnb.dto.responses.FeedbackResponse;
import kg.airbnb.airbnb.services.FeedbackService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/feedbacks")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Feedback API", description = "Feedback endpoints")
public class FeedbackAPI {

    private final FeedbackService feedbackService;

    @Operation(summary = "Get all feedbacks", description = "Get all feedbacks of announcement")
    @GetMapping("{announcementId}")
    public List<FeedbackResponse> findAll(@PathVariable Long announcementId,
                                          @RequestParam(required = false, defaultValue = "1") Integer page,
                                          @RequestParam(required = false, defaultValue = "3") Integer size) {
        return feedbackService.findAll(announcementId, page, size);
    }

    @Operation(summary = "Leave feedback", description = "Leave feedback to announcement")
    @PostMapping("leave/{announcementId}")
    public FeedbackResponse leaveFeedback(@PathVariable Long announcementId, @RequestBody FeedbackRequest request) {
        return feedbackService.saveFeedback(announcementId, request);
    }

    @Operation(summary = "Like feedback", description = "Like feedback of an announcement")
    @PostMapping("like/{feedbackId}")
    public FeedbackResponse LikeFeedback(@PathVariable Long feedbackId) {
        return feedbackService.likeFeedback(feedbackId);
    }

    @Operation(summary = "Dislike feedback", description = "Dislike feedback of an announcement")
    @PostMapping("dislike/{feedbackId}")
    public FeedbackResponse DisLikeFeedback(@PathVariable Long feedbackId) {
        return feedbackService.disLikeFeedback(feedbackId);
    }

    @Operation(summary = "Get ratings", description = "Get ratings of an announcement")
    @GetMapping("rating")
    public FeedbackRatingResponse getFeedbackRatingWithPercentage(Long announcementId) {
        return feedbackService.feedbackRatingWithPercentage(announcementId);
    }

}
