package kg.airbnb.airbnb.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "regions")
@NoArgsConstructor
@Getter
@Setter
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "region_id_generator")
    @SequenceGenerator(name = "region_id_generator", sequenceName = "region_seq", allocationSize = 1)
    private Long id;

    private String regionName;

    @OneToMany(cascade = ALL, fetch = EAGER, mappedBy = "region")
    private List<Address> addresses;

    public Region(String regionName) {
        this.regionName = regionName;
    }
}
