package kg.airbnb.airbnb.dto.requests;

import kg.airbnb.airbnb.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeBookingsStatusRequest {

    private Long vendorId;
    private Long announcementId;
    private Long bookingId;
    private Status status;
}
