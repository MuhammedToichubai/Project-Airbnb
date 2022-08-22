package kg.airbnb.airbnb.models;

import kg.airbnb.airbnb.models.auth.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "feedbacks")
@NoArgsConstructor
@Getter
@Setter
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedback_id_generator")
    @SequenceGenerator(name = "feedback_id_generator", sequenceName = "feedback_seq", allocationSize = 1, initialValue = 5)
    private Long id;

    @ManyToOne(cascade = {REFRESH, PERSIST, DETACH, MERGE}, fetch = EAGER)
    private User owner;

    @ElementCollection
    private List<String> images;

    @Length(max = 2048, message = "Message too long (more than 2 kB)")
    private String description;

    @Column(name = "likes")
    private AtomicInteger like = new AtomicInteger(0);

    @Column(name = "disLikes")
    private AtomicInteger dislike = new AtomicInteger(0);

    private Integer rating;

    @ManyToOne(cascade = {REFRESH, DETACH, PERSIST, MERGE})
    private Announcement announcement;

    private LocalDate createdAt;

    private String colorOfLike;

    private String colorOfDisLike;

    public void incrementLikes(){
        like.incrementAndGet();
    }

    public void decrementLikes(){
        like.decrementAndGet();
    }

    public void incrementDisLikes(){
        dislike.incrementAndGet();
    }

    public void decrementDisLikes(){
        dislike.decrementAndGet();
    }

}

