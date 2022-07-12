package kg.airbnb.airbnb.repositories;

import kg.airbnb.airbnb.models.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}