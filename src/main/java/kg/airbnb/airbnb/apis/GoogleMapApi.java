package kg.airbnb.airbnb.apis;

import kg.airbnb.airbnb.services.googlemap.GoogleMapService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
public class GoogleMapApi {

    private final GoogleMapService googleMapService;

    @GetMapping("/find/place")
    Map<String, String> findPlaceByLatAndLng(@RequestParam String lat,
                                             @RequestParam String lng){
     return Map.of("place", googleMapService.findPlace(lat, lng));
    }

}
