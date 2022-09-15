package kg.airbnb.airbnb.models;

import kg.airbnb.airbnb.enums.Status;
import kg.airbnb.airbnb.enums.Type;
import kg.airbnb.airbnb.exceptions.BadRequestException;
import kg.airbnb.airbnb.models.auth.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "announcements")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Announcement implements Comparable<Announcement> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "announcement_id_generator")
    @SequenceGenerator(name = "announcement_id_generator", sequenceName = "announcement_seq", allocationSize = 1, initialValue = 28)
    private Long id;

    private String title;

    @Column(length = 10000)
    private String description;

    @ElementCollection
    private List<String> images;

    private Status status;

    private BigDecimal price;

    @OneToMany(cascade = ALL, orphanRemoval = true, mappedBy = "announcement")
    private List<Feedback> feedbacks;

    private Integer maxGuests;

    @Enumerated(EnumType.STRING)
    private Type houseType;

    @ManyToOne(cascade = {DETACH, MERGE, PERSIST, REFRESH})
    private User owner;

    private String messageFromAdmin;

    @OneToOne(cascade = ALL)
    private Address location;

    @ManyToMany(cascade = ALL)
    private List<User> guests;

    @OneToMany(cascade = ALL, mappedBy = "announcement")
    private List<Booking> bookings;

    private LocalDate createdAt;

    @ElementCollection
    private List<LocalDate> blockedDates;

    @ElementCollection
    private List<LocalDate> blockedDatesByUser;

    @Column(name = "likes")
    private volatile int like = 0;

    @Column(name = "bookmarks")
    private volatile int bookmark = 0;

    @Column(name = "viewAnnouncements")
    private volatile int viewAnnouncementHistoryCount = 0;

    private String colorOfLike;

    private String colorOfBookmark;

    public int incrementLikes(){return like++;}
    public int incrementBookmark(){return bookmark++;}
    public int decrementLikes() {return like--;}
    public int decrementBookmark(){return bookmark--;}
    public int incrementViewCount(){return viewAnnouncementHistoryCount++;}
    public void addFeedback(Feedback feedback) {
        this.feedbacks.add(feedback);
    }
    public void addBlockedDateByUser(LocalDate date) {
        this.blockedDatesByUser.add(date);
    }
    public void addBlockedDate(LocalDate date) {
        this.blockedDates.add(date);
    }
    public void removeBlockedDateByUser(LocalDate date) {
        this.blockedDatesByUser.remove(date);
    }
    public void removeIfExistDate(LocalDate date) {
        this.blockedDates.remove(date);
    }

    @Override
    public int compareTo(Announcement o) {

        int a = 0;
        int b = 0;

        for (Feedback c : this.feedbacks) {
            a = a + c.getRating();
        }
        for (Feedback d : o.getFeedbacks()) {
            b = b + d.getRating();
        }

        return b - a;
    }

    public void releaseTakenDates(LocalDate checkin, LocalDate checkout) {
        while (checkin.isBefore(checkout)) {
            this.blockedDates.remove(checkin);
            checkin = checkin.plusDays(1L);
        }
        this.blockedDates.remove(checkout);
    }

    public void addBlockedDate(LocalDate checkin, LocalDate checkout) {
        while (checkin.isBefore(checkout)) {
            this.blockedDates.add(checkin);
            checkin = checkin.plusDays(1L);
        }
        this.blockedDates.add(checkout);
    }
}
