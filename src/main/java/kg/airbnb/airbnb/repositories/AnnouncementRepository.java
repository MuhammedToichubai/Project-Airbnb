package kg.airbnb.airbnb.repositories;
import kg.airbnb.airbnb.enums.Type;
import kg.airbnb.airbnb.models.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Query("select a from Announcement a where a.location.region.id = :regionId and a.status = 'ACCEPTED'")
    Page<Announcement> findByRegion(@Param("regionId") Long regionId, Pageable pageable);

    @Query("select a from Announcement a where a.location.region.id = :regionId and upper(a.houseType) = :type and a.status = 'ACCEPTED'")
    Page<Announcement> findByRegionAndType(@Param("regionId") Long regionId, @Param("type") Type type, Pageable pageable);

    @Query("select a from Announcement a where a.status = 'ACCEPTED' AND a.location.region.id = :regionId and upper(a.houseType) = :type order by a.price asc")
    Page<Announcement> findByRegionAndTypeAndPriceLow(@Param("regionId") Long regionId, @Param("type") Type type, Pageable pageable);

    @Query("select a from Announcement a where a.status = 'ACCEPTED' AND a.location.region.id = :regionId and upper(a.houseType) = :type order by a.price desc")
    Page<Announcement> findByRegionAndTypeAndPriceHigh(@Param("regionId") Long regionId, @Param("type") Type type, Pageable pageable);

    @Query("select a from Announcement a where upper(a.houseType) = :type and a.status = 'ACCEPTED'")
    Page<Announcement> findByType(@Param("type") Type type, Pageable pageable);

    @Query("select a from Announcement a WHERE a.status = 'ACCEPTED' order by a.price asc")
    Page<Announcement> findByPriceLow(Pageable pageable);

    @Query("select a from Announcement a WHERE a.status = 'ACCEPTED' order by a.price desc")
    Page<Announcement> findByPriceHigh(Pageable pageable);

    @Query("select a from Announcement a where a.status = 'ACCEPTED' AND upper(a.houseType) = :type order by a.price asc ")
    Page<Announcement> findByTypeAndPriceLow(@Param("type") Type type, Pageable pageable);

    @Query("select a from Announcement a where a.status = 'ACCEPTED' AND upper(a.houseType) = :type order by a.price desc ")
    Page<Announcement> findByTypeAndPriceHigh(@Param("type") Type type, Pageable pageable);

    @Query("select a from Announcement a where a.status = 'ACCEPTED' AND  a.location.region.id = :regionId order by a.price asc ")
    Page<Announcement> findByRegionAndPriceLow(@Param("regionId") Long regionId, Pageable pageable);

    @Query("select a from Announcement a where a.status = 'ACCEPTED' AND  a.location.region.id = :regionId order by a.price desc ")
    Page<Announcement> findByRegionAndPriceHigh(@Param("regionId") Long regionId, Pageable pageable);

    @Query(value = "SELECT a FROM Announcement a WHERE a.status = 'ACCEPTED' AND a.location.region.regionName LIKE %?1%"
            + " OR a.location.city LIKE %?1%"
            + " OR a.location.address LIKE %?1%"
            + "OR a.description LIKE %?1%"
            + " OR CONCAT(a.houseType, '') LIKE %?1% "

    )
    List<Announcement> globalSearch(String keyword, Pageable pageable);

    @Query(value = "SELECT a FROM Announcement a WHERE a.status = 'ACCEPTED' AND a.location.region.regionName LIKE %?1%")
    List<Announcement> searchByRegion(String region, Pageable pageable);

    @Query(value = "SELECT a FROM Announcement a WHERE a.status = 'ACCEPTED' AND a.location.city LIKE %?1%")
    List<Announcement> searchByCity(String city, Pageable pageable);

    @Query(value = "SELECT a FROM Announcement a WHERE a.status = 'ACCEPTED' AND  a.location.address LIKE %?1%")
    List<Announcement> searchByAddress(String address, Pageable pageable);

    @Query("SELECT a FROM Announcement a WHERE a.status = 'ACCEPTED'")
    Page<Announcement> findAllAccepted(Pageable pageable);

    @Modifying
    @Transactional
    @Query(
            "delete from Announcement a where a.id = ?1"
    )
    void customDeleteById(Long announcementId);


    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "delete from announcement_images i where i.announcement_id = ?1"
    )
    void clearImages(Long announcementId);

    @Query(value = "SELECT a FROM Announcement a WHERE a.status = 0 or a.status=1")
    Page<Announcement> findAllNewAndAccepted(Pageable pageable);

    @Query(value = "SELECT a FROM Announcement a WHERE a.status = 0 or a.status=1")
    List<Announcement> findAllNewAndAccepted();
}
