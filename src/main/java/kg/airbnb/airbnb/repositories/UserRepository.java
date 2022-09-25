package kg.airbnb.airbnb.repositories;

import kg.airbnb.airbnb.models.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query("select u from User u where  u.role = 'USER' ")
    List<User> findAllUsers();

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "delete from user_messages_from_admin m where m.user_id = :userId"

    )
    void clearMessages(Long userId);

    @Modifying
    @Transactional
    @Query(
            "delete from User u where u.id = ?1"
    )
    void customDeleteById(Long userId);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "delete from feedbacks f where f.owner_id = ?1"

    )
    void clearFeedbacks(Long userId);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "delete from bookings b where b.user_id = :userId"
    )
    void clearBookings(Long userId);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "delete from announcements a where a.owner_id = :userId "
    )
    void clearAnnouncements(Long userId);

}