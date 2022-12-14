package kg.airbnb.airbnb.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.airbnb.airbnb.dto.responses.RegionResponse;
import kg.airbnb.airbnb.db.service.RegionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/regions")
@CrossOrigin
@Tag(name = "This is API is for Region")
public class RegionAPI {

    private final RegionService regionService;

    public RegionAPI(RegionService regionService) {
        this.regionService = regionService;
    }

    @Operation(summary = "Get all Regions", description = "Any user can view the entire regions")
    @GetMapping
    public List<RegionResponse> getAllRegions() {
        return regionService.getAllRegions();
    }

}