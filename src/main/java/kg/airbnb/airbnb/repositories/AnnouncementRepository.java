package kg.airbnb.airbnb.repositories;
import kg.airbnb.airbnb.enums.Type;
import kg.airbnb.airbnb.models.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Query("select a from Announcement a where a.location.region.id = :regionId")
    Page<Announcement> findByRegion(@Param("regionId") Long regionId, Pageable pageable);

    @Query("select a from Announcement a where a.location.region.id = :regionId and upper(a.houseType) = :type ")
    Page<Announcement> findByRegionAndType(@Param("regionId") Long regionId, @Param("type") Type type, Pageable pageable);

    @Query("select a from Announcement a where a.location.region.id = :regionId and upper(a.houseType) = :type order by a.price asc ")
    Page<Announcement> findByRegionAndTypeAndPriceLow(@Param("regionId") Long regionId, @Param("type") Type type, Pageable pageable);

    @Query("select a from Announcement a where a.location.region.id = :regionId and upper(a.houseType) = :type order by a.price desc ")
    Page<Announcement> findByRegionAndTypeAndPriceHigh(@Param("regionId") Long regionId, @Param("type") Type type, Pageable pageable);

    @Query("select a from Announcement a where upper(a.houseType) = :type")
    Page<Announcement> findByType(@Param("type") Type type, Pageable pageable);

    @Query("select a from Announcement a order by a.price asc ")
    Page<Announcement> findByPriceLow(Pageable pageable);

    @Query("select a from Announcement a order by a.price desc ")
    Page<Announcement> findByPriceHigh(Pageable pageable);

    @Query("select a from Announcement a where upper(a.houseType) = :type order by a.price asc ")
    Page<Announcement> findByTypeAndPriceLow(@Param("type") Type type, Pageable pageable);

    @Query("select a from Announcement a where upper(a.houseType) = :type order by a.price desc ")
    Page<Announcement> findByTypeAndPriceHigh(@Param("type") Type type, Pageable pageable);

    @Query("select a from Announcement a where a.location.region.id = :regionId order by a.price asc ")
    Page<Announcement> findByRegionAndPriceLow(@Param("regionId") Long regionId, Pageable pageable);

    @Query("select a from Announcement a where a.location.region.id = :regionId order by a.price desc ")
    Page<Announcement> findByRegionAndPriceHigh(@Param("regionId") Long regionId, Pageable pageable);
    
    @Query(value = "SELECT a FROM Announcement a WHERE a.location.region.regionName LIKE %?1%"
            + " OR a.location.city LIKE %?1%"
            + " OR a.location.address LIKE %?1%"
            + "OR a.description LIKE %?1%"
            + " OR CONCAT(a.houseType, '') LIKE %?1% "

    )
    List<Announcement> globalSearch(String keyword, Pageable pageable);

    @Query(value = "SELECT a FROM Announcement a WHERE a.location.region.regionName LIKE %?1%")
    List<Announcement> searchByRegion(String region, Pageable pageable);

    @Query(value = "SELECT a FROM Announcement a WHERE a.location.city LIKE %?1%")
    List<Announcement> searchByCity(String city, Pageable pageable);

    @Query(value = "SELECT a FROM Announcement a WHERE a.location.address LIKE %?1%")
    List<Announcement> searchByAddress(String address, Pageable pageable);
}
