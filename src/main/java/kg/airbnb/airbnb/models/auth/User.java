package kg.airbnb.airbnb.models.auth;

import kg.airbnb.airbnb.enums.Role;
import kg.airbnb.airbnb.models.Announcement;
import kg.airbnb.airbnb.models.Booking;
import kg.airbnb.airbnb.models.Feedback;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class  User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_generator")
    @SequenceGenerator(name = "users_id_generator", sequenceName = "user_seq", allocationSize = 1, initialValue = 7)
    private Long id;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String password;

    private String image;

    @ElementCollection
    private List<String> messagesFromAdmin;

    @OneToMany(cascade = ALL, mappedBy = "owner", fetch = EAGER)
    private List<Announcement> announcements = new ArrayList<>();

    @OneToMany(cascade = {DETACH, REFRESH, MERGE, PERSIST}, fetch = LAZY, mappedBy = "owner")
    private List<Feedback> feedbacks = new ArrayList<>();

    @OneToMany(cascade = {REFRESH, PERSIST, DETACH, MERGE}, mappedBy = "user")
    private List<Booking> bookings = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    @ElementCollection
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<Long> likedAnnouncements;

    @ElementCollection
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<Long> bookmarkAnnouncements;

    @ElementCollection
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<Long> likedFeedbacks = ConcurrentHashMap.newKeySet();

    @ElementCollection
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<Long> disLikedFeedbacks = ConcurrentHashMap.newKeySet();

    public User(String email) {
        this.email = email;
    }

    public User(String fullName, String email, String phoneNumber, String password) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public User(String fullName, String email, String phoneNumber, String password, Role role ) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role=role;
    }

    public void addToLikedAnnouncements(Long announcementId){
        likedAnnouncements.add(announcementId);
    }

    public void removeFromLikedAnnouncements(Long announcementId) {
        likedAnnouncements.remove(announcementId);
    }

    public void addToBookmarkAnnouncements(Long announcementId){
        bookmarkAnnouncements.add(announcementId);
    }

    public void removeFromBookmarkAnnouncement(Long announcementId){bookmarkAnnouncements.remove(announcementId);}

//    public void addToAnnouncementHistory(Long announcementId) {announcementHistory.add(announcementId);}

    public void addToLikedFeedbacks(Long feedbackId) {
        likedFeedbacks.add(feedbackId);
    }

    public void removeFromLikedFeedbacks(Long feedbackId) {
        likedFeedbacks.remove(feedbackId);
    }

    public void removeFromDisLikedFeedbacks(Long feedbackId) {
        disLikedFeedbacks.remove(feedbackId);
    }

    public void addToDisLikedFeedbacks(Long feedbackId) {
        disLikedFeedbacks.add(feedbackId);
    }

}

