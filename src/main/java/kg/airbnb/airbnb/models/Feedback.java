package kg.airbnb.airbnb.models;

import kg.airbnb.airbnb.models.auth.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "feedbacks")
@NoArgsConstructor
@Getter
@Setter
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedback_id_generator")
    @SequenceGenerator(name = "feedback_id_generator", sequenceName = "feedback_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(cascade = {REFRESH, PERSIST, DETACH, MERGE}, fetch = EAGER)
    private User owner;

    @ElementCollection
    private List<String> images;

    private String description;

    @Column(name = "likes")
    private AtomicInteger likeCount;


    private AtomicInteger dislikeCount;

    private Integer rating;

    @ManyToOne(cascade = {REFRESH, DETACH, PERSIST, MERGE})
    private Announcement announcement;

    private LocalDate createdAt;
}

