package kg.airbnb.airbnb.repositories;

import kg.airbnb.airbnb.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}