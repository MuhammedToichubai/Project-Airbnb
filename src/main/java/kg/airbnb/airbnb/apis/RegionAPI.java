package kg.airbnb.airbnb.apis;

import kg.airbnb.airbnb.dto.response.RegionResponse;
import kg.airbnb.airbnb.services.RegionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/regions")
@CrossOrigin
public class RegionAPI {
    private final RegionService regionService;

    public RegionAPI(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping
    public List<RegionResponse> getAllRegions() {
        return regionService.getAllRegions();
    }

}