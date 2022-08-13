package kg.airbnb.airbnb.repositories;
import kg.airbnb.airbnb.models.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Query("select a from Announcement a where upper(a.location.region.regionName) = :region")
    Page<Announcement> findByRegion(@Param("region") String region, Pageable pageable);

    @Query("select a from Announcement a where upper(a.location.region.regionName) = :region and upper(a.houseType) = :type ")
    Page<Announcement> findByRegionAndType(@Param("region") String region, @Param("type") String type, Pageable pageable);

    @Query("select a from Announcement a where upper(a.location.region.regionName) = :region and upper(a.houseType) = :type order by a.price asc ")
    Page<Announcement> findByRegionAndTypeAndPriceLow(@Param("region") String region, @Param("type") String type, Pageable pageable);

    @Query("select a from Announcement a where upper(a.location.region.regionName) = :region and upper(a.houseType) = :type order by a.price desc ")
    Page<Announcement> findByRegionAndTypeAndPriceHigh(@Param("region") String region, @Param("type") String type, Pageable pageable);

    @Query("select a from Announcement a where upper(a.houseType) = :type")
    Page<Announcement> findByType(@Param("type") String type, Pageable pageable);

    @Query("select a from Announcement a order by a.price asc ")
    Page<Announcement> findByPriceLow(Pageable pageable);

    @Query("select a from Announcement a order by a.price desc ")
    Page<Announcement> findByPriceHigh(Pageable pageable);

    @Query("select a from Announcement a where upper(a.houseType) = :type order by a.price asc ")
    Page<Announcement> findByTypeAndPriceLow(@Param("type") String type, Pageable pageable);

    @Query("select a from Announcement a where upper(a.houseType) = :type order by a.price desc ")
    Page<Announcement> findByTypeAndPriceHigh(@Param("type") String type, Pageable pageable);

    @Query("select a from Announcement a where upper(a.location.region.regionName) = :region order by a.price asc ")
    Page<Announcement> findByRegionAndPriceLow(@Param("region") String region, Pageable pageable);

    @Query("select a from Announcement a where upper(a.location.region.regionName) = :region order by a.price desc ")
    Page<Announcement> findByRegionAndPriceHigh(@Param("region") String region, Pageable pageable);
}