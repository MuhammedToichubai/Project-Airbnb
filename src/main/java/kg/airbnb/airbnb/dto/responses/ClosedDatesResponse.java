package kg.airbnb.airbnb.dto.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ClosedDatesResponse {

    private List<LocalDate> takenDates;
    private List<LocalDate> datesBlockedByVendor;
}
