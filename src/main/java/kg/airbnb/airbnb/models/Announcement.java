package kg.airbnb.airbnb.models;

import kg.airbnb.airbnb.enums.Status;
import kg.airbnb.airbnb.enums.Type;
import kg.airbnb.airbnb.models.auth.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "announcement_id_generator")
    @SequenceGenerator(name = "announcement_id_generator", sequenceName = "announcement_seq", allocationSize = 1)
    private Long id;

    private String title;

    private String description;

    @ElementCollection
    private List<String> images;

    private Status status;

    private BigDecimal price;

    @OneToMany(cascade = ALL, mappedBy = "announcement", orphanRemoval = true)
    private List<Feedback> feedbacks;

    private Integer maxGuests;

    private Type houseType;

    @ManyToOne(cascade = {DETACH, MERGE, PERSIST, REFRESH})
    private User owner;

    @OneToOne(cascade = ALL, fetch = EAGER, mappedBy = "announcement", orphanRemoval = true)
    private Address location;

    @ManyToMany(cascade = ALL, fetch = LAZY)
    private List<User> guests;

    @OneToMany(cascade = {REFRESH, PERSIST, MERGE, DETACH}, fetch = EAGER, mappedBy = "announcement")
    private List<Booking> bookings;

    private LocalDate createdAt;
}
