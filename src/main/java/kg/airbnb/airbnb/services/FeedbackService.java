package kg.airbnb.airbnb.services;

import kg.airbnb.airbnb.dto.request.FeedbackRequest;
import kg.airbnb.airbnb.dto.responses.FeedbackRatingResponse;
import kg.airbnb.airbnb.dto.responses.FeedbackResponse;
import kg.airbnb.airbnb.dto.responses.SimpleResponse;
import kg.airbnb.airbnb.models.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FeedbackService {
    SimpleResponse saveFeedback(Long announcementId, FeedbackRequest request);
    FeedbackResponse likeFeedback(Long feedbackId);
    FeedbackResponse disLikeFeedback(Long feedbackId);
    List<FeedbackResponse> findAll(Long announcementId, Integer page, Integer size);
    FeedbackRatingResponse feedbackRatingWithPercentage(Long announcementId);

}
