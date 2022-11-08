package kg.airbnb.airbnb.dto.requests;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateBookRequest {

    private Long announcementId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Long bookingId;

}
