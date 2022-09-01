package kg.airbnb.airbnb.models;

import kg.airbnb.airbnb.models.auth.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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
    @SequenceGenerator(name = "feedback_id_generator", sequenceName = "feedback_seq", allocationSize = 1, initialValue = 137)
    private Long id;

    @ManyToOne(cascade = {REFRESH, PERSIST, DETACH, MERGE}, fetch = EAGER)
    private User owner;

    @ElementCollection
    private List<String> images;

    @Column(name = "descriptions",length = 10000)
    private String description;

    @Column(name = "likes")
    private volatile int like;

    @Column(name = "disLikes")
    private volatile int dislike;

    private Integer rating;

    @ManyToOne(cascade = {REFRESH, DETACH, PERSIST, MERGE})
    private Announcement announcement;

    private LocalDate createdAt;

    private String colorOfLike;

    private String colorOfDisLike;

    public int incrementLikes(){
        return like++;
    }

    public int decrementLikes(){
        return like--;
    }

    public int incrementDisLikes(){
         return dislike++;
    }

    public int decrementDisLikes(){
        return dislike--;
    }

}

