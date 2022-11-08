package kg.airbnb.airbnb.db.repositories;

import kg.airbnb.airbnb.db.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}