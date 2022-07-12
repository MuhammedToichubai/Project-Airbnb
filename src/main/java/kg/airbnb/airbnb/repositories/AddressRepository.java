package kg.airbnb.airbnb.repositories;

import kg.airbnb.airbnb.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}