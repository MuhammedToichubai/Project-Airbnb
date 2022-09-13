package kg.airbnb.airbnb.services;

import kg.airbnb.airbnb.dto.requests.FeedbackRequest;
import kg.airbnb.airbnb.dto.responses.FeedbackRatingResponse;
import kg.airbnb.airbnb.dto.responses.FeedbackResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FeedbackService {

    FeedbackResponse saveFeedback(Long announcementId, FeedbackRequest request);

    FeedbackResponse likeFeedback(Long feedbackId);

    FeedbackResponse disLikeFeedback(Long feedbackId);

    List<FeedbackResponse> findAll(Long announcementId, Integer page, Integer size);

    FeedbackRatingResponse feedbackRatingWithPercentage(Long announcementId);

}
