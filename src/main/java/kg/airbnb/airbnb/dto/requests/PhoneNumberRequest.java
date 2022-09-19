package kg.airbnb.airbnb.dto.requests;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter @Setter
public class PhoneNumberRequest {

    @Column(length = 14, unique = true)
    private String phoneNumber;
}
