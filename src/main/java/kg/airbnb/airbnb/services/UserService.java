package kg.airbnb.airbnb.services;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
    boolean ifLikedFeedback(Long feedbackId);
    boolean ifDisLikedFeedback(Long feedbackId);

    void removeFromLikedFeedbacks(Long feedbackId);

    void removeFromDisLikedFeedbacks(Long feedbackId);

    void addToLikedFeedbacks(Long feedbackId);
}
