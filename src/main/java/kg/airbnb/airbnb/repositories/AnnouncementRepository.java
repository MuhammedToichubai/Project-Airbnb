package kg.airbnb.airbnb.repositories;

import kg.airbnb.airbnb.models.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Query("SELECT a FROM Announcement a WHERE a.location.region.regionName LIKE %?1%"
            + " OR a.location.city LIKE %?1%"
            + " OR a.location.address LIKE %?1%"
            + "OR a.description LIKE %?1%"
            + " OR CONCAT(a.price, '') LIKE %?1%")

    List<Announcement> search(String keyword);

    @Query("select a from Announcement a where " +
            "(?1 is null or upper(a.location.region.regionName) like concat('%', upper(?1), '%')) " +
//            "(?1 is null or upper(a.location.address) like concat('%', upper(?1), '%')) " +
//            "(?1 is null or upper(a.description) like concat('%', upper(?1), '%')) " +
            "and (?2 is null or a.createdAt >= ?2) " +
            "and (?3 is null or a.createdAt <= ?3)")
    Page<Announcement> getByQuery(String name, final LocalDate from, final LocalDate to, final Pageable pageable);
}
