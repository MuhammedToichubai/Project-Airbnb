package kg.airbnb.airbnb.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Getter
@Setter
public class GoogleMapService {

    private final RestTemplate restTemplate;

    @Value("${google.api-key}")
    private String googleApikey;

    public GoogleMapService() {
        this.restTemplate = new RestTemplate();
    }

    public String findPlace(String lat, String lng) {
        Object object = restTemplate.getForObject(
                String.format("%s?latlng=%s, %s&key=%s",
                        "https://maps.googleapis.com/maps/api/geocode/json",
                        lat,
                        lng,
                        googleApikey),
                Object.class
        );
        return object.toString();
    }

}
