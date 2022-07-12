package kg.airbnb.airbnb.repositories;

import kg.airbnb.airbnb.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}