package kg.airbnb.airbnb.repositories;

import kg.airbnb.airbnb.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.announcement.id = ?1 AND b.status = 1 ORDER BY b.createdAt")
    List<Booking> findAcceptedByAnnouncementId(Long announcementId);

    @Query("SELECT b FROM Booking b WHERE b.announcement.id = ?1 ORDER BY b.createdAt")
    List<Booking> findAllByAnnouncementId(Long announcementId);

}