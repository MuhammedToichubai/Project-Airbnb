package kg.airbnb.airbnb.db.repositories;

import kg.airbnb.airbnb.db.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {

}