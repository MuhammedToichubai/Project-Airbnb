package kg.airbnb.airbnb.services;

import kg.airbnb.airbnb.dto.response.RegionResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RegionService {

    List<RegionResponse> getAllRegions();
}

