package kg.airbnb.airbnb.services.impl;

import kg.airbnb.airbnb.dto.request.FeedbackRequest;
import kg.airbnb.airbnb.dto.request.LikeRequest;
import kg.airbnb.airbnb.dto.response.FeedbackResponse;
import kg.airbnb.airbnb.dto.response.SimpleResponse;
import kg.airbnb.airbnb.exceptions.NotFoundException;
import kg.airbnb.airbnb.models.Announcement;
import kg.airbnb.airbnb.models.Feedback;
import kg.airbnb.airbnb.models.auth.User;
import kg.airbnb.airbnb.repositories.AnnouncementRepository;
import kg.airbnb.airbnb.services.FeedbackService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    private final AnnouncementRepository announcementRepository;

    public FeedbackServiceImpl(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }


    @Override
    public SimpleResponse saveFeedback(Long announcementId, FeedbackRequest request) {
        Announcement announcement = getFindByIdAnnouncement(announcementId);
        Feedback feedback = new Feedback();
        feedback.setImages(request.getImages());
        feedback.setRating(request.getRating());
        feedback.setDescription(request.getDescription());
        announcement.addFeedback(feedback);
        return new SimpleResponse("SAVE","Ad saved successfully!");
    }

    @Override
    public List<FeedbackResponse> getAllFeedback(Long announcementId) {
        Announcement announcement = getFindByIdAnnouncement(announcementId);
        List<Feedback> feedbacks = announcement.getFeedbacks();
        return  feedbacks.stream().map(this::mapToFeedbackResponse).toList();
    }

    private FeedbackResponse mapToFeedbackResponse(Feedback feedback) {
        FeedbackResponse feedbackResponse = new FeedbackResponse();
        User user = new User();
        feedbackResponse.setFeedbackOwnerImage(user.getImage());
        feedbackResponse.setFeedbackOwnerImage(user.getFullName());
        feedbackResponse.setRating(feedback.getRating());
        feedbackResponse.setImages(feedback.getImages());
        feedbackResponse.setDescription(feedback.getDescription());
        feedbackResponse.setCreatedAt(feedback.getCreatedAt());
        feedbackResponse.setLike(feedback.getLikeCount());
        feedbackResponse.setDisLike(feedback.getDislikeCount());
        return feedbackResponse;
    }

    @Override
    public LikeRequest likeFeedback() {
        return null;
    }

    private Announcement getFindByIdAnnouncement(Long id){
        return announcementRepository.findById(id)
                .orElseThrow( () -> new NotFoundException(
                        "Announcement whit id = " +id +" not found!"
                ));
    }

}
