package kg.airbnb.airbnb.models;

import kg.airbnb.airbnb.enums.Status;
import kg.airbnb.airbnb.enums.Type;
import kg.airbnb.airbnb.models.auth.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "announcements")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "announcement_id_generator")
    @SequenceGenerator(name = "announcement_id_generator", sequenceName = "announcement_seq", allocationSize = 1, initialValue = 28)
    private Long id;

    private String title;

    private String description;

    @ElementCollection
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<String> images;

    private Status status;

    private BigDecimal price;

    @OneToMany(cascade = ALL, mappedBy = "announcement")
    private List<Feedback> feedbacks;

    private Integer maxGuests;

    @Enumerated(EnumType.STRING)
    private Type houseType;

    @ManyToOne(cascade = {DETACH, MERGE, PERSIST, REFRESH},fetch = EAGER)
    private User owner;

    @OneToOne(cascade = ALL, orphanRemoval = true)
    private Address location;

    @ManyToMany(cascade = ALL, fetch = LAZY)
    private List<User> guests;


    @OneToMany(cascade = ALL, mappedBy = "announcement")
    private List<Booking> bookings;

    private LocalDate createdAt;

    @Column(name = "likes")
    private AtomicInteger like = new AtomicInteger(0);

    @Column(name = "bookmarks")
    private AtomicInteger bookmark = new AtomicInteger(0);

    @Column(name = "viewAnnouncements")
    private AtomicInteger viewAnnouncement = new AtomicInteger(0);

    private String colorOfLike;

    private String colorOfBookmark;

    public void incrementLikes(){like.incrementAndGet();}
    public void incrementBookmark(){bookmark.incrementAndGet();}
    public void decrementLikes() {
        like.decrementAndGet();
    }
    public void decrementBookmark(){bookmark.decrementAndGet();}
    public void incrementViewCount(){viewAnnouncement.incrementAndGet();}
    public void addFeedback(Feedback feedback) {
        feedbacks.add(feedback);
    }
}
