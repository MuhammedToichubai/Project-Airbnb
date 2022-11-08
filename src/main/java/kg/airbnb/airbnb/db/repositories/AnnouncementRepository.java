package kg.airbnb.airbnb.db.repositories;

import kg.airbnb.airbnb.enums.Type;
import kg.airbnb.airbnb.db.model.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Query("SELECT a FROM Announcement a WHERE a.location.region.id = :regionId AND a.status = 1")
    List<Announcement> findByRegion(@Param("regionId") Long regionId);

    @Query("SELECT a FROM Announcement a WHERE a.location.region.id = :regionId AND UPPER(a.houseType) = :type AND a.status = 1")
    List<Announcement> findByRegionAndType(@Param("regionId") Long regionId, @Param("type") Type type);

    @Query("SELECT a FROM Announcement a WHERE UPPER(a.houseType) = :type AND a.status = 1")
    List<Announcement> findByType(@Param("type") Type type);

    @Query("SELECT a FROM Announcement a WHERE UPPER(a.houseType) = :type")
    List<Announcement> findByTypeAll(@Param("type") Type type);

    @Query(value = "SELECT a FROM Announcement a WHERE a.status = 1 AND a.location.region.regionName LIKE %?1%"
            + " OR a.location.city LIKE %?1%"
            + " OR a.location.address LIKE %?1%"
            + "OR a.description LIKE %?1%"
            + " OR CONCAT(a.houseType, '') LIKE %?1% "
    )
    List<Announcement> globalSearch(String keyword, Pageable pageable);

    @Query(value = "SELECT a FROM Announcement a WHERE a.status = 1 AND a.location.region.regionName LIKE %?1%")
    List<Announcement> searchByRegion(String region, Pageable pageable);

    @Query(value = "SELECT a FROM Announcement a WHERE a.status = 1 AND a.location.city LIKE %?1%")
    List<Announcement> searchByCity(String city, Pageable pageable);

    @Query(value = "SELECT a FROM Announcement a WHERE a.status = 1 AND  a.location.address LIKE %?1%")
    List<Announcement> searchByAddress(String address, Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM Announcement a WHERE a.id = ?1")
    void customDeleteById(Long announcementId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM announcement_images i WHERE i.announcement_id = ?1", nativeQuery = true)
    void clearImages(Long announcementId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM feedbacks f WHERE f.announcement_id = ?1", nativeQuery = true)
    void clearFeedback(Long announcementId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM bookings b WHERE b.announcement_id = :announcementId", nativeQuery = true)
    void clearBooking(Long announcementId);

    @Query(value = "SELECT a FROM Announcement a WHERE a.status = 0 OR a.status=4 ORDER BY a.createdAt DESC")
    Page<Announcement> findAllNewAndSeen(Pageable pageable);

    @Query(value = "SELECT a FROM Announcement a WHERE a.status = 0 OR a.status=4 ORDER BY a.createdAt DESC")
    List<Announcement> findAllNewAndSeen();

    @Query("SELECT a FROM Announcement a WHERE a.owner.id = ?1")
    List<Announcement> findUserAllAnnouncement(Long userId);

    @Query("SELECT a FROM Announcement a WHERE a.status = 1 AND a.location.region.id = :regionId " +
            "AND UPPER(a.location.city) LIKE :city% AND a.houseType = :type ")
    List<Announcement> findByAddress(@Param("regionId") Long regionId, @Param("city") String city, @Param("type") Type type);

    @Query("SELECT a FROM Announcement a WHERE a.status = 1 AND a.location.region.id = :regionId " +
            "AND UPPER(a.location.city) LIKE :city%")
    List<Announcement> findByAddress(Long regionId, String city);

    @Query("SELECT a FROM Announcement a WHERE a.status = 1 AND UPPER(a.location.city) LIKE :city% AND a.houseType = :type")
    List<Announcement> findByAddress(String city, Type type);

    @Query("SELECT a FROM Announcement a WHERE a.status = 1")
    List<Announcement> findAllAccepted();

    @Query("SELECT a FROM Announcement a WHERE a.status = 1 AND UPPER(a.location.city) LIKE :city%")
    List<Announcement> findByCity(@Param("city") String city);

}
