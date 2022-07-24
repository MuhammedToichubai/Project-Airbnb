package kg.airbnb.airbnb.apis;

import kg.airbnb.airbnb.dto.response.RegionResponse;
import kg.airbnb.airbnb.services.RegionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/regions")
public class RegionAPI {
    private final RegionService regionService;

    public RegionAPI(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping
    public List<RegionResponse> getAllRegions() {
        return regionService.getAllRegions();
    }

    @GetMapping("find/{regionId}")
    public RegionResponse findById(@PathVariable Long regionId) {
        return regionService.findByIdRegion(regionId);
    }
}