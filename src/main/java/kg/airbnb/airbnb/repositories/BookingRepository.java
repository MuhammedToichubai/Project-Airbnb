package kg.airbnb.airbnb.repositories;

import kg.airbnb.airbnb.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}