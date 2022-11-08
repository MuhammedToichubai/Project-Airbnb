package kg.airbnb.airbnb.mappers.region;

import kg.airbnb.airbnb.dto.responses.RegionResponse;
import kg.airbnb.airbnb.model.Region;
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
