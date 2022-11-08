package kg.airbnb.airbnb.model;

import kg.airbnb.airbnb.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.FetchType.EAGER;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bookings")
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
