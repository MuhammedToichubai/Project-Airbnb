package kg.airbnb.airbnb.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleResponse {
    private String status;
    private String message;

    public SimpleResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public SimpleResponse() {

    }
}
