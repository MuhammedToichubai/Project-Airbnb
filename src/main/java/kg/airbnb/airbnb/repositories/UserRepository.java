package kg.airbnb.airbnb.repositories;

import kg.airbnb.airbnb.models.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}