package kg.airbnb.airbnb.repositories;

import kg.airbnb.airbnb.models.Announcement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Query(value = "SELECT a FROM Announcement a WHERE a.location.region.regionName LIKE %?1%"
            + " OR a.location.city LIKE %?1%"
            + " OR a.location.address LIKE %?1%"
            + "OR a.description LIKE %?1%"
            + " OR CONCAT(a.price, '') LIKE %?1% "
            + " OR CONCAT(a.maxGuests, '') LIKE %?1% "
            + " OR CONCAT(a.houseType, '') LIKE %?1% "
    )
    List<Announcement> search(String keyword, Pageable pageable);

}
