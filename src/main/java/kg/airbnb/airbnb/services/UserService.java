package kg.airbnb.airbnb.services;

import kg.airbnb.airbnb.dto.requests.BlockBookDateRequest;
import kg.airbnb.airbnb.dto.requests.BookRequest;
import kg.airbnb.airbnb.dto.responses.BookedResponse;
import kg.airbnb.airbnb.dto.responses.BookingCardResponse;
import kg.airbnb.airbnb.dto.responses.UserProfileResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public interface UserService {

    void removeFromLikedAnnouncements(Long announcementId);

    void addToLikedAnnouncements(Long announcementId);

    void removeFromBookmarkAnnouncements(Long announcementId);

    void addToBookmarkAnnouncements(Long announcementId);

    boolean ifLikedAnnouncement(Long announcementId);

    boolean ifBookmarkAnnouncement(Long announcementId);

    void removeFromLikedFeedbacks(Long feedbackId);

    void removeFromDisLikedFeedbacks(Long feedbackId);

    void addToLikedFeedbacks(Long feedbackId);

    boolean ifLikedFeedback(Long feedbackId);

    boolean ifDisLikedFeedback(Long feedbackId);

    void addToDisLikedFeedbacks(Long feedbackId);

    UserProfileResponse getUserBookingsAndAnnouncements();

    Map<String, String> requestToBook(BookRequest request);

    Map<String, String> blockDateByUser(BlockBookDateRequest dateToBlock);

    List<BookingCardResponse> findUsersBookings(Long userId);

    Map<String, String> deleteRequestToBook(Long userId, Long bookingId);

    Map<String, String> updateRequestToBook(BookRequest request);

    List<BookedResponse> getAnnouncementsBookings(Long userId, Long announcementId);

    Map<String, String> acceptRequestToBook(BookRequest request);

    Map<String, String> rejectRequestToBook(BookRequest request);
}

