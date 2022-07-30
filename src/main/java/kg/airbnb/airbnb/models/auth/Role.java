package kg.airbnb.airbnb.models.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "role_id_generator")
    @SequenceGenerator(
            name = "role_id_generator",
            sequenceName = "role_seq",
            allocationSize = 1)

    private Long id;

    private String roleName;
}
