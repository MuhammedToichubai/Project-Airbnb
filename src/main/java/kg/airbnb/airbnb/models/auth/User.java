package kg.airbnb.airbnb.models.auth;

import kg.airbnb.airbnb.enums.Role;
import kg.airbnb.airbnb.models.Announcement;
import kg.airbnb.airbnb.models.Booking;
import kg.airbnb.airbnb.models.Feedback;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_generator")
    @SequenceGenerator(name = "users_id_generator", sequenceName = "user_seq", allocationSize = 1, initialValue = 4)

    private Long id;

    private String fullName;

    private String email;

    private String password;

    private String image;

    @OneToMany(cascade = ALL, mappedBy = "owner", fetch = EAGER)
    private List<Announcement> announcements = new ArrayList<>();

    @OneToMany(cascade = {DETACH, REFRESH, MERGE, PERSIST}, fetch = LAZY, mappedBy = "owner")
    private List<Feedback> feedbacks = new ArrayList<>();

    @OneToMany(cascade = {REFRESH, PERSIST, DETACH, MERGE}, mappedBy = "user")
    private List<Booking> bookings = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String email) {
        this.email = email;
    }

    public User(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public User(String fullName, String email, String password, Role role ) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role=role;
    }


}

