package kg.airbnb.airbnb.dto.requests;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BlockBookDateRequest {

    private Long announcementId;
    private List<LocalDate> datesToBlock;

}
