package kg.airbnb.airbnb.repositories;

import kg.airbnb.airbnb.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select b from Booking b where b.announcement.id = ?1 and b.status = 1 order by b.createdAt")
    List<Booking> findAcceptedByAnnouncementId(Long announcementId);

    @Query("select b from Booking b where b.announcement.id = ?1 order by b.createdAt")
    List<Booking> findAllByAnnouncementId(Long announcementId);
}