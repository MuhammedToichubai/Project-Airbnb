package kg.airbnb.airbnb.repositories;

import kg.airbnb.airbnb.models.Feedback;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long>, PagingAndSortingRepository<Feedback,Long> {

    @Query(value = "select distinct f from Feedback f where f.announcement.id = ?1")
    List<Feedback> findAnnouncementFeedback(@Param("announcementId")Long announcementId, Pageable pageable);
}