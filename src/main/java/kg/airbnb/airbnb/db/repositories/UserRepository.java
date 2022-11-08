package kg.airbnb.airbnb.db.repositories;

import kg.airbnb.airbnb.db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE  u.role = 'USER'")
    List<User> findAllUsers();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_messages_from_admin m WHERE m.user_id = :userId", nativeQuery = true)
    void clearMessages(Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.id = ?1")
    void customDeleteById(Long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM feedbacks f WHERE f.owner_id = ?1", nativeQuery = true)
    void clearFeedbacks(Long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM bookings b WHERE b.user_id = :userId", nativeQuery = true)
    void clearBookings(Long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM announcements a WHERE a.owner_id = :userId ", nativeQuery = true)
    void clearAnnouncements(Long userId);

}