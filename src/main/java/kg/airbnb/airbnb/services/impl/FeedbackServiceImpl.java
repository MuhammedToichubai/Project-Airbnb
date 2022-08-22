package kg.airbnb.airbnb.services.impl;

import kg.airbnb.airbnb.dto.request.FeedbackRequest;
import kg.airbnb.airbnb.dto.responses.FeedbackResponse;
import kg.airbnb.airbnb.dto.responses.SimpleResponse;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        announcement.getFeedbacks().add(feedback);
        feedback.setAnnouncement(announcement);
        feedback.setCreatedAt(LocalDate.now());
        feedback.setOwner(getCurrentUser());
        feedbackRepository.save(feedback);
        return new SimpleResponse("With feedback id " + feedback.getId() + " saved!","Ad saved successfully!");
    }

    @Override
    public FeedbackResponse likeFeedback(Long feedbackId){

        Feedback feedbackById = getFeedbackById(feedbackId);

        if (userService.ifLikedFeedback(feedbackId)){
            feedbackById.decrementLikes();
            feedbackById.setColorOfLike(null);
            userService.removeFromLikedFeedbacks(feedbackId);
        } else if (userService.ifDisLikedFeedback(feedbackId)){
            feedbackById.decrementDisLikes();
            feedbackById.setColorOfDisLike(null);
            userService.removeFromDisLikedFeedbacks(feedbackId);
            feedbackById.incrementLikes();
            feedbackById.setColorOfLike("Yellow");
            userService.addToLikedFeedbacks(feedbackId);
        }else {
            feedbackById.incrementLikes();
            feedbackById.setColorOfLike("Yellow");
            userService.addToLikedFeedbacks(feedbackId);
        }
        feedbackRepository.save(feedbackById);

        return getFeedbackResponse(feedbackById);
    }

    @Override
    public FeedbackResponse disLikeFeedback(Long feedbackId) {

        Feedback feedbackById = getFeedbackById(feedbackId);

        if (userService.ifDisLikedFeedback(feedbackId)){
            feedbackById.decrementDisLikes();
            feedbackById.setColorOfDisLike(null);
            userService.removeFromDisLikedFeedbacks(feedbackId);
        } else if (userService.ifLikedFeedback(feedbackId)){
            feedbackById.decrementLikes();
            feedbackById.setColorOfLike(null);
            userService.removeFromLikedFeedbacks(feedbackId);
            feedbackById.incrementDisLikes();
            feedbackById.setColorOfDisLike("Yellow");
            userService.addToDisLikedFeedbacks(feedbackId);
        }else {
            feedbackById.incrementDisLikes();
            feedbackById.setColorOfDisLike("Yellow");
            userService.addToDisLikedFeedbacks(feedbackId);
        }

        feedbackRepository.save(feedbackById);

        return getFeedbackResponse(feedbackById);
    }

    @Override
    public List<FeedbackResponse> findAll(Long announcementId, Integer page, Integer size) {
        Announcement announcement = getFindByIdAnnouncement(announcementId);
        PageRequest pr = PageRequest.of(page - 1,size);
        List<Feedback> feedbacks = announcement.getFeedbacks();
        return feedbacks.stream().map(this::getFeedbackResponse).toList();
    }

    private Feedback getFeedbackById(Long feedbackId) {
        return feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find feedback by id - " + feedbackId));
    }

    private Announcement getFindByIdAnnouncement(Long id) {
        return announcementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Announcement whit id = " + id + " not found!"
                ));
    }
    private FeedbackResponse getFeedbackResponse(Feedback feedback) {

        FeedbackResponse feedbackResponse = new FeedbackResponse();
        User user = new User();
        feedbackResponse.setFeedbackOwnerImage(user.getImage());
        feedbackResponse.setFeedbackOwnerFullName(user.getFullName());
        feedbackResponse.setRating(feedback.getRating());
        feedbackResponse.setImages(feedback.getImages());
        feedbackResponse.setDescription(feedback.getDescription());
        feedbackResponse.setCreatedAt(feedback.getCreatedAt());
        feedbackResponse.setLikeCount(feedback.getLike().get());
        feedbackResponse.setDisLikeCount(feedback.getDislike().get());
        feedbackResponse.setColorOfLike(feedback.getColorOfLike());
        feedbackResponse.setColorOfDisLike(feedback.getColorOfDisLike());
        return feedbackResponse;
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() ->
                new ForbiddenException("An unregistered user cannot write comment for this announcement!"));
    }
}
