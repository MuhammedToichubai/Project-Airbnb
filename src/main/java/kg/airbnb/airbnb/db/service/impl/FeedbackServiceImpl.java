package kg.airbnb.airbnb.db.service.impl;

import kg.airbnb.airbnb.dto.requests.FeedbackRequest;
import kg.airbnb.airbnb.dto.responses.FeedbackRatingResponse;
import kg.airbnb.airbnb.dto.responses.FeedbackResponse;
import kg.airbnb.airbnb.exceptions.ForbiddenException;
import kg.airbnb.airbnb.exceptions.NotFoundException;
import kg.airbnb.airbnb.db.model.Announcement;
import kg.airbnb.airbnb.db.model.Feedback;
import kg.airbnb.airbnb.db.model.User;
import kg.airbnb.airbnb.db.repositories.AnnouncementRepository;
import kg.airbnb.airbnb.db.repositories.FeedbackRepository;
import kg.airbnb.airbnb.db.repositories.UserRepository;
import kg.airbnb.airbnb.db.service.FeedbackService;
import kg.airbnb.airbnb.db.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final AnnouncementRepository announcementRepository;
    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public FeedbackResponse saveFeedback(Long announcementId, FeedbackRequest request) {
        Announcement announcement = getFindByAnnouncementId(announcementId);
        if (request.getRating() != null) {
            announcement.setRating(request.getRating());
        }
        Feedback newFeedback = new Feedback();
        newFeedback.setImages(request.getImages());
        newFeedback.setRating(request.getRating());
        newFeedback.setDescription(request.getDescription());
        announcement.getFeedbacks().add(newFeedback);
        newFeedback.setAnnouncement(announcement);
        newFeedback.setCreatedAt(LocalDate.now());
        newFeedback.setOwner(getCurrentUser());
        feedbackRepository.save(newFeedback);
        return getFeedbackResponse(newFeedback);
    }

    @Override
    public FeedbackResponse likeFeedback(Long feedbackId) {
        Feedback feedbackById = getFeedbackById(feedbackId);
        if (userService.ifLikedFeedback(feedbackId)) {
            feedbackById.decrementLikes();
            feedbackById.setColorOfLike(null);
            userService.removeFromLikedFeedbacks(feedbackId);
        } else if (userService.ifDisLikedFeedback(feedbackId)) {
            feedbackById.decrementDisLikes();
            feedbackById.setColorOfDisLike(null);
            userService.removeFromDisLikedFeedbacks(feedbackId);
            feedbackById.incrementLikes();
            feedbackById.setColorOfLike("Yellow");
            userService.addToLikedFeedbacks(feedbackId);
        } else {
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
        if (userService.ifDisLikedFeedback(feedbackId)) {
            feedbackById.decrementDisLikes();
            feedbackById.setColorOfDisLike(null);
            userService.removeFromDisLikedFeedbacks(feedbackId);
        } else if (userService.ifLikedFeedback(feedbackId)) {
            feedbackById.decrementLikes();
            feedbackById.setColorOfLike(null);
            userService.removeFromLikedFeedbacks(feedbackId);
            feedbackById.incrementDisLikes();
            feedbackById.setColorOfDisLike("Yellow");
            userService.addToDisLikedFeedbacks(feedbackId);
        } else {
            feedbackById.incrementDisLikes();
            feedbackById.setColorOfDisLike("Yellow");
            userService.addToDisLikedFeedbacks(feedbackId);
        }
        feedbackRepository.save(feedbackById);
        return getFeedbackResponse(feedbackById);
    }

    @Override
    public List<FeedbackResponse> findAll(Long announcementId, Integer page, Integer size) {
        Announcement announcement = getFindByAnnouncementId(announcementId);
        PageRequest pr = PageRequest.of(page - 1, size);
        List<Feedback> announcementFeedback = feedbackRepository.findAnnouncementFeedback(announcement.getId(), pr);
        System.out.println("announcementFeedback = " + announcementFeedback);
        return announcementFeedback.stream().map(this::getFeedbackResponseForGetAll).collect(Collectors.toList());
    }

    @Override
    public FeedbackRatingResponse feedbackRatingWithPercentage(Long announcementId) {
        Announcement announcement = getFindByAnnouncementId(announcementId);
        double rating;
        double percentageOfFive;
        double percentageOfFour;
        double percentageOfThree;
        double percentageOfTwo;
        double percentageOfOne;
        int fives = 0;
        int fours = 0;
        int threes = 0;
        int twos = 0;
        int ones = 0;

        List<Feedback> allFeedbacksOfAnnouncement = announcement.getFeedbacks();
        List<Integer> ratings = new ArrayList<>();
        for (Feedback feedback : allFeedbacksOfAnnouncement) {
            if (feedback.getRating() != 0) {
                ratings.add(feedback.getRating());
            }
        }

        FeedbackRatingResponse response = new FeedbackRatingResponse();
        if (ratings.size() <= 0) {
            rating = 0.0;
            percentageOfFive = 0.0;
            percentageOfFour = 0.0;
            percentageOfThree = 0.0;
            percentageOfTwo = 0.0;
            percentageOfOne = 0.0;

            response.setRating(rating);
            response.setPercentageOfFive(percentageOfFive);
            response.setPercentageOfFour(percentageOfFour);
            response.setPercentageOfThree(percentageOfThree);
            response.setPercentageOfTwo(percentageOfTwo);
            response.setPercentageOfOne(percentageOfOne);
            return response;

        } else {
            for (Integer integer : ratings) {
                if (integer == 5) {
                    fives++;
                } else if (integer == 4) {
                    fours++;
                } else if (integer == 3) {
                    threes++;
                } else if (integer == 2) {
                    twos++;
                } else if (integer == 1) {
                    ones++;
                }
            }

            percentageOfFive = (double) (fives * 100) / ratings.size();
            percentageOfFour = (double) (fours * 100) / ratings.size();
            percentageOfThree = (double) (threes * 100) / ratings.size();
            percentageOfTwo = (double) (twos * 100) / ratings.size();
            percentageOfOne = (double) (ones * 100) / ratings.size();
            rating = (double) (5 * fives + 4 * fours + 3 * threes + 2 * twos + ones) / (ratings.size());

            response.setRating(rating);
            response.setPercentageOfFive(percentageOfFive);
            response.setPercentageOfFour(percentageOfFour);
            response.setPercentageOfThree(percentageOfThree);
            response.setPercentageOfTwo(percentageOfTwo);
            response.setPercentageOfOne(percentageOfOne);
            return response;
        }
    }

    private Feedback getFeedbackById(Long feedbackId) {
        return feedbackRepository.findById(feedbackId).orElseThrow(() ->
                new IllegalArgumentException("Cannot find feedback by id - " + feedbackId));
    }

    private Announcement getFindByAnnouncementId(Long id) {
        return announcementRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Announcement whit id = " + id + " not found!"));
    }

    private FeedbackResponse getFeedbackResponse(Feedback feedback) {
        User user = getCurrentUser();
        FeedbackResponse feedbackResponse = new FeedbackResponse();
        feedbackResponse.setId(feedback.getId());
        feedbackResponse.setFeedbackOwnerImage(user.getImage());
        feedbackResponse.setFeedbackOwnerFullName(user.getFullName());
        feedbackResponse.setRating(feedback.getRating());
        feedbackResponse.setImages(feedback.getImages());
        feedbackResponse.setDescription(feedback.getDescription());
        feedbackResponse.setCreatedAt(feedback.getCreatedAt());
        feedbackResponse.setLikeCount(feedback.getLike());
        feedbackResponse.setDisLikeCount(feedback.getDislike());
        feedbackResponse.setColorOfLike(feedback.getColorOfLike());
        feedbackResponse.setColorOfDisLike(feedback.getColorOfDisLike());
        return feedbackResponse;
    }

    private FeedbackResponse getFeedbackResponseForGetAll(Feedback feedback) {
        FeedbackResponse feedbackResponse = new FeedbackResponse();
        feedbackResponse.setId(feedback.getId());

        if (Objects.isNull(feedback.getOwner())) {
            feedbackResponse.setFeedbackOwnerImage(null);
            feedbackResponse.setFeedbackOwnerFullName(null);
        } else {
            feedbackResponse.setFeedbackOwnerImage(feedback.getOwner().getImage());
            feedbackResponse.setFeedbackOwnerFullName(feedback.getOwner().getFullName());
        }

        feedbackResponse.setRating(feedback.getRating());
        feedbackResponse.setImages(feedback.getImages());
        feedbackResponse.setDescription(feedback.getDescription());
        feedbackResponse.setCreatedAt(feedback.getCreatedAt());
        feedbackResponse.setLikeCount(feedback.getLike());
        feedbackResponse.setDisLikeCount(feedback.getDislike());
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
