package kg.airbnb.airbnb.services;
import kg.airbnb.airbnb.dto.requests.ChangeBookingsStatusRequest;
import kg.airbnb.airbnb.dto.requests.BlockBookDateRequest;
import kg.airbnb.airbnb.dto.requests.BookRequest;
import kg.airbnb.airbnb.dto.requests.UpdateBookRequest;
import kg.airbnb.airbnb.dto.responses.BookedResponse;
import kg.airbnb.airbnb.dto.responses.BookingCardResponse;
import kg.airbnb.airbnb.dto.responses.ClosedDatesResponse;
import kg.airbnb.airbnb.dto.responses.FavoritesResponse;
import kg.airbnb.airbnb.dto.responses.FavoriteAnnouncementResponse;
import kg.airbnb.airbnb.dto.responses.UserProfileResponse;
import org.springframework.stereotype.Service;

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

    UserProfileResponse getUserProfile();

    UserProfileResponse getUserProfile(Long userId);

    List<FavoriteAnnouncementResponse> userFavoriteAnnouncements();

    FavoritesResponse getUserFavoriteAnnouncements();

    Map<String, String> requestToBook(BookRequest request);

    Map<String, String> blockDateByUser(BlockBookDateRequest dateToBlock);

    List<BookingCardResponse> findUsersBookings();

    Map<String, String> deleteRequestToBook(Long bookingId);

    Map<String, String> updateRequestToBook(UpdateBookRequest request);

    List<BookedResponse> getAnnouncementsBookings(Long announcementId);

    Map<String, String> changeBookingsStatus(ChangeBookingsStatusRequest request);

    ClosedDatesResponse getClosedDates(Long announcementId);
}

