package kg.airbnb.airbnb.repositories;

import kg.airbnb.airbnb.dto.responses.AnnouncementCardResponse;
import kg.airbnb.airbnb.models.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {


    @Query("select a from Announcement a where upper(a.location.region.regionName) = :region " +
            "and upper(a.houseType) = :type order by :price")
    Page<Announcement> getAnnouncementsByFilter(
            String region, String type, String price, Pageable pageable);
}