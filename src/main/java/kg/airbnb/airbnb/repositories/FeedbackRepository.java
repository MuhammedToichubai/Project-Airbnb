package kg.airbnb.airbnb.repositories;

import kg.airbnb.airbnb.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long>, PagingAndSortingRepository<Feedback,Long> {

}