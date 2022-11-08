package kg.airbnb.airbnb.db.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.FetchType.EAGER;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "addresses")
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
