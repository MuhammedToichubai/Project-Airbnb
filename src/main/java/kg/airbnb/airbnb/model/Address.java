package kg.airbnb.airbnb.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "addresses")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_id_generator")
    @SequenceGenerator(name = "address_id_generator", sequenceName = "address_seq", allocationSize = 1, initialValue = 28)
    private Long id;

    @ManyToOne(cascade = {REFRESH, PERSIST, DETACH, MERGE}, fetch = EAGER)
    private Region region;

    private String city;

    private String address;

    @OneToOne(mappedBy = "location")
    private Announcement announcement;


    public String getFullAddress() {
        return String.format("%s %s %s", region.getRegionName(), city, address);
    }
}
