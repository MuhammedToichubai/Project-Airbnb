package kg.airbnb.airbnb.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class LikeRequest {

    private Long feedbackId;
    private AtomicInteger likeCount = new AtomicInteger(0);
    private AtomicInteger dislikeCount = new AtomicInteger(0);
}
