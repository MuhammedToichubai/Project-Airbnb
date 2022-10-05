package kg.airbnb.airbnb.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class BookedResponse {

    private Long bookingId;
    private BigDecimal price;
    @JsonFormat(pattern="dd.MM.yy")
    private LocalDate checkIn;
    @JsonFormat(pattern="dd.MM.yy")
    private LocalDate checkOut;
    private String userName;
    private String userEmail;
    private String userImage;
    private String userPhoneNumber;
    private String status;

}
