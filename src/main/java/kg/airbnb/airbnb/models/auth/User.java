package kg.airbnb.airbnb.models.auth;

import kg.airbnb.airbnb.models.Announcement;
import kg.airbnb.airbnb.models.Booking;
import kg.airbnb.airbnb.models.Feedback;
import kg.airbnb.airbnb.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
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
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_generator")
    @SequenceGenerator(name = "users_id_generator", sequenceName = "user_seq", allocationSize = 1)

    private Long id;

    private String fullName;

    private String email;

    private String password;

    private String image;

    @OneToMany(cascade = ALL)
    private List<Announcement> announcements;

    @OneToMany(cascade = {DETACH, REFRESH, MERGE, PERSIST}, fetch = LAZY, mappedBy = "owner")
    private List<Feedback> feedbacks;

    @OneToMany(cascade = {REFRESH, PERSIST, DETACH, MERGE}, mappedBy = "user")
    private List<Booking> bookings;

    //@OneToOne(cascade = ALL, fetch = EAGER)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

