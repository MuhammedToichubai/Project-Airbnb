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

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "announcements")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Announcement implements Comparable<Announcement> {

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

    public void addFeedback(Feedback feedback) {
        this.feedbacks.add(feedback);
    }

    @Override
    public int compareTo(Announcement o) {

        int a = 0;
        int b = 0;

        for (Feedback c: this.feedbacks) {
            a = a + c.getRating();
        }
        for (Feedback d: o.getFeedbacks()) {
            b = b + d.getRating();
        }

        return b - a;
    }
}
