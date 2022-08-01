package kg.airbnb.airbnb.models;

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
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_id_generator")
    @SequenceGenerator(name = "address_id_generator", sequenceName = "address_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(cascade = {REFRESH, PERSIST, DETACH, MERGE}, fetch = EAGER)
    private Region region;

    private String city;

    private String address;

    @OneToOne(cascade = {ALL}, fetch = EAGER)
    private Announcement announcement;
}
