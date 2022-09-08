package kg.airbnb.airbnb.dto.requests;

import kg.airbnb.airbnb.models.Booking;
import kg.airbnb.airbnb.models.auth.User;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BookedRequest {

    private Booking booking;
    private BigDecimal price;
}
