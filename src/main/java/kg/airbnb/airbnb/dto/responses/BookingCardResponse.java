package kg.airbnb.airbnb.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import kg.airbnb.airbnb.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BookingCardResponse {

    private Long id;
    private Long bookingId;
    private String title;
    private String description;
    private List<String> images = new ArrayList<>();
    private BigDecimal price;
    private Integer maxGuests;
    private String location;
    private double rating;
    private String type;
    private Status status;
    @JsonFormat(pattern="dd.MM.yy")
    private LocalDate checkIn;
    @JsonFormat(pattern="dd.MM.yy")
    private LocalDate checkOut;

//    public void setRating(double rating) {
//        String result = String.format("%.1f",rating);
//        this.rating = result;
//    }
}
