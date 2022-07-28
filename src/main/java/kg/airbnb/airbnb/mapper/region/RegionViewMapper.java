package kg.airbnb.airbnb.mapper.region;

import kg.airbnb.airbnb.dto.response.RegionResponse;
import kg.airbnb.airbnb.models.Region;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RegionViewMapper {
    public RegionResponse entityToDtoConverting(Region region) {
        if (region == null) {
            return null;
        }
        RegionResponse response = new RegionResponse();
        response.setId(region.getId());
        response.setRegionName(region.getRegionName());
        return response;
    }

    public List<RegionResponse> entityListToDtoList(List<Region> regions) {
        List<RegionResponse> responses = new ArrayList<>();
        for (Region region : regions) {
            responses.add(entityToDtoConverting(region));
        }
        return responses;
    }
}
