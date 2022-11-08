package kg.airbnb.airbnb.db.model;

import kg.airbnb.airbnb.enums.Status;
import kg.airbnb.airbnb.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "announcements")
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

    @OneToMany(cascade = ALL, orphanRemoval = true, mappedBy = "announcement")
    private List<Booking> bookings;

    private LocalDate createdAt;

    private double rating;

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

    public int incrementLikes() {
        return like++;
    }

    public int incrementBookmark() {
        return bookmark++;
    }

    public int decrementLikes() {
        return like--;
    }

    public int decrementBookmark() {
        return bookmark--;
    }

    public int incrementViewCount() {
        return viewAnnouncementHistoryCount++;
    }

    public void addBlockedDateByUser(LocalDate date) {
        this.blockedDatesByUser.add(date);
    }

    public void removeBlockedDateByUser(LocalDate date) {
        this.blockedDatesByUser.remove(date);
    }

    @Override
    public int compareTo(Announcement o) {
        int a = 0;
        int b = 0;

        for (Feedback e : this.feedbacks) {
            a = a + e.getRating();
        }
        for (Feedback f : o.getFeedbacks()) {
            b = b + f.getRating();
        }
        return b - a;
    }

    public void setRating(double rating) {
        double a = 0;
        double b = 0;
        for (Feedback f : this.feedbacks) {
            a = a + f.getRating();
            b++;
        }
        a = a + rating;
        b++;
        this.rating = a / b;
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
