package kg.airbnb.airbnb.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "regions")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "region_id_generator")
    @SequenceGenerator(name = "region_id_generator", sequenceName = "region_seq", allocationSize = 1)
    private Long id;

    private String regionName;

    @OneToMany(mappedBy = "region")
    private List<Address> addresses;

    public Region(String regionName) {
        this.regionName = regionName;
    }

}
