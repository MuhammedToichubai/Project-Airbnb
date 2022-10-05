package kg.airbnb.airbnb.services;

import kg.airbnb.airbnb.dto.responses.RegionResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RegionService {

    List<RegionResponse> getAllRegions();

}

