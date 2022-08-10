package kg.airbnb.airbnb.services.impl;

import kg.airbnb.airbnb.dto.request.FeedbackRequest;
import kg.airbnb.airbnb.dto.request.LikeRequest;
import kg.airbnb.airbnb.dto.response.FeedbackResponse;
import kg.airbnb.airbnb.dto.response.SimpleResponse;
import kg.airbnb.airbnb.exceptions.ForbiddenException;
import kg.airbnb.airbnb.exceptions.NotFoundException;
import kg.airbnb.airbnb.models.Announcement;
import kg.airbnb.airbnb.models.Feedback;
import kg.airbnb.airbnb.models.auth.User;
import kg.airbnb.airbnb.repositories.AnnouncementRepository;
import kg.airbnb.airbnb.repositories.FeedbackRepository;
import kg.airbnb.airbnb.repositories.UserRepository;
import kg.airbnb.airbnb.services.FeedbackService;
import kg.airbnb.airbnb.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    private final AnnouncementRepository announcementRepository;
    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public FeedbackServiceImpl(AnnouncementRepository announcementRepository, FeedbackRepository feedbackRepository, UserRepository userRepository, UserService userService) {
        this.announcementRepository = announcementRepository;
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
        this.userService = userService;
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
        return  feedbacks.stream().map(this::getFeedbackDetails).toList();
    }

    public FeedbackResponse getFeedbackDetails(Feedback feedbackId) {
        Feedback savedFeedback = getFeedbackById(feedbackId.getId());

        FeedbackResponse feedbackResponse = new FeedbackResponse();
        User user = new User();
        feedbackResponse.setFeedbackOwnerImage(user.getImage());
        feedbackResponse.setFeedbackOwnerFullName(user.getFullName());
        feedbackResponse.setRating(savedFeedback.getRating());
        feedbackResponse.setImages(savedFeedback.getImages());
        feedbackResponse.setDescription(savedFeedback.getDescription());
        feedbackResponse.setCreatedAt(savedFeedback.getCreatedAt());
        feedbackResponse.setLikes(savedFeedback.getLikes());
        feedbackResponse.setDisLikes(savedFeedback.getDisLikes());
        return feedbackResponse;
    }

    private Announcement getFindByIdAnnouncement(Long id){
        return announcementRepository.findById(id)
                .orElseThrow( () -> new NotFoundException(
                        "Announcement whit id = " +id +" not found!"
                ));
    }
    @Override
    public FeedbackResponse likeFeedback(Long feedbackId){
        // Like Feedback by id
        Feedback feedbackById = getFeedbackById(feedbackId);
        // Increment Like Count
        // If user already like the feedback, then decrement Like Count

        // Like - 0, disLike - 0
        // Like - 1, disLike - 0
        // Like - 0, disLike - 0

        // Like - 0, disLike - 0
        // Like - 0, disLike - 1
        // Like - 0, disLike - 0
        // If user already disLike the feedback, then increment Like Count and decrement disLike Count

        if (userService.ifLikedFeedback(feedbackId)){
            feedbackById.decrementLikes();
            userService.removeFromLikedFeedbacks(feedbackId);
        } else if (userService.ifDisLikedFeedback(feedbackId)){
            feedbackById.incrementDisLikes();
            userService.removeFromDisLikedFeedbacks(feedbackId);
            feedbackById.incrementLikes();
            userService.addToLikedFeedbacks(feedbackId);
        }else {
            feedbackById.incrementLikes();
            userService.addToLikedFeedbacks(feedbackId);
        }

        feedbackRepository.save(feedbackById);


        feedbackById.incrementLikes();
        return null;
    }

    @Override
    public FeedbackResponse disLikeFeedback(Long feedbackId) {
        return null;
    }

    @Override
    public FeedbackResponse getFeedbackDetails(Long feedbackId) {
        FeedbackResponse feedbackResponse = new FeedbackResponse();
        feedbackResponse.
        return feedbackResponse;
    }

    private Feedback getFeedbackById(Long feedbackId) {
        return feedbackRepository.findById(feedbackId)
                .orElseThrow(()-> new IllegalArgumentException("Cannot find feedback by id - " + feedbackId));
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() ->
                new ForbiddenException("An unregistered user cannot write comment for this announcement!"));
    }
}
