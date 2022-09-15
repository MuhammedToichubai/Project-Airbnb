package kg.airbnb.airbnb.models;

import kg.airbnb.airbnb.enums.Status;
import kg.airbnb.airbnb.models.auth.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "bookings")
@NoArgsConstructor
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_id_generator")
    @SequenceGenerator(name = "booking_id_generator", sequenceName = "booking_seq", allocationSize = 1, initialValue = 28)
    private Long id;

    @ManyToOne(cascade = {DETACH, MERGE, PERSIST, REFRESH}, fetch = EAGER)
    private User user;

    @ManyToOne(cascade = {DETACH, MERGE, PERSIST, REFRESH}, fetch = EAGER)
    private Announcement announcement;

    private LocalDate checkin;

    private LocalDate checkout;

    private BigDecimal pricePerDay;

    private Status status;

    private LocalDate createdAt;
}
