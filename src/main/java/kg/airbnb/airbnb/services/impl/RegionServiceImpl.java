package kg.airbnb.airbnb.services.impl;

import kg.airbnb.airbnb.dto.responses.RegionResponse;
import kg.airbnb.airbnb.mappers.region.RegionViewMapper;
import kg.airbnb.airbnb.models.Region;
import kg.airbnb.airbnb.repositories.RegionRepository;
import kg.airbnb.airbnb.services.RegionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;
    private final RegionViewMapper regionViewMapper;

    @Override
    public List<RegionResponse> getAllRegions() {
        List<Region> allRegions = regionRepository.findAll();
        return regionViewMapper.entityListToDtoList(allRegions);
    }

    @PostConstruct
    public void isEmptyRegions() {
        if (getAllRegions().isEmpty()) {
            log.fatal("There is no default region in the database!");
        }
    }

}
