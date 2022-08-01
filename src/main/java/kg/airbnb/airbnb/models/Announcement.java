package kg.airbnb.airbnb.models;

import kg.airbnb.airbnb.enums.Status;
import kg.airbnb.airbnb.enums.Type;
import kg.airbnb.airbnb.models.auth.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "announcement_id_generator")
    @SequenceGenerator(name = "announcement_id_generator", sequenceName = "announcement_seq", allocationSize = 1)
    private Long id;

    private String title;

    private String description;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn
    private List<String> images;

    private Status status;

    private BigDecimal price;

    @OneToMany(cascade = ALL, mappedBy = "announcement")
    private List<Feedback> feedbacks;

    private Integer maxGuests;

    @Enumerated(EnumType.STRING)
    private Type houseType;

    @ManyToOne(cascade = {PERSIST, MERGE, DETACH, REFRESH})
    private User owner;

    @OneToOne(cascade = {REMOVE, DETACH})
    private Address location;

    @ManyToMany(cascade = ALL, fetch = LAZY)
    private List<User> guests;

    @OneToMany(cascade = {ALL}, mappedBy = "announcement")
    private List<Booking> bookings;

    private LocalDate createdAt;
}
