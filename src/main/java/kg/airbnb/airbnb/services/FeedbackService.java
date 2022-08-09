package kg.airbnb.airbnb.services;

import kg.airbnb.airbnb.dto.request.FeedbackRequest;
import kg.airbnb.airbnb.dto.request.LikeRequest;
import kg.airbnb.airbnb.dto.response.FeedbackResponse;
import kg.airbnb.airbnb.dto.response.SimpleResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FeedbackService {
    SimpleResponse saveFeedback(Long announcementId, FeedbackRequest request);
    List<FeedbackResponse> getAllFeedback(Long announcementId);
    FeedbackResponse likeFeedback(Long feedbackId);
    FeedbackResponse disLikeFeedback(Long feedbackId);
    FeedbackResponse getFeedbackDetails(Long feedbackId);

}
