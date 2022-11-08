package kg.airbnb.airbnb.repositories;

import kg.airbnb.airbnb.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {

}