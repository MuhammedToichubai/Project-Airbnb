package kg.airbnb.airbnb.db.service;

import kg.airbnb.airbnb.dto.responses.RegionResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RegionService {

    List<RegionResponse> getAllRegions();

}

