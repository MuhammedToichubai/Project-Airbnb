package kg.airbnb.airbnb.services;

import kg.airbnb.airbnb.dto.request.FeedbackRequest;
import kg.airbnb.airbnb.dto.responses.FeedbackResponse;
import kg.airbnb.airbnb.dto.responses.SimpleResponse;
import kg.airbnb.airbnb.models.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FeedbackService {
    SimpleResponse saveFeedback(Long announcementId, FeedbackRequest request);
    List<FeedbackResponse> getAllFeedback(Long announcementId);
    FeedbackResponse likeFeedback(Long feedbackId);
    FeedbackResponse disLikeFeedback(Long feedbackId);
    Feedback getFeedbackById(Long feedbackId);
    Page<Feedback> findAll(Integer page, Integer size);

}
