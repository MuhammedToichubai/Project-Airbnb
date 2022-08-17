package kg.airbnb.airbnb.services;

import kg.airbnb.airbnb.dto.responses.UserProfileResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void removeFromLikedFeedbacks(Long feedbackId);

    void removeFromDisLikedFeedbacks(Long feedbackId);

    void addToLikedFeedbacks(Long feedbackId);

    boolean ifLikedFeedback(Long feedbackId);

    boolean ifDisLikedFeedback(Long feedbackId);

    void addToDisLikedFeedbacks(Long feedbackId);

    UserProfileResponse getUserBookingsAndAnnouncements();
}
//=======
//import kg.airbnb.airbnb.dto.responses.UserProfileResponse;

//public interface UserService {

//
//>>>>>>> 7c15a0c3e8fd1bc5af126ec37016f42942e118ae
//}
