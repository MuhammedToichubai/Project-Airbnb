package kg.airbnb.airbnb.dto.responses;

import kg.airbnb.airbnb.enums.Status;
import kg.airbnb.airbnb.models.auth.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class BookingResponse {

    private Long id;

    private User ownerBooking;

    private LocalDate checkin;

    private LocalDate checkout;

    private Status status;

    public BookingResponse(Long id, User user, LocalDate checkin, LocalDate checkout, Status status, Status status1) {
    }
}
