package kg.airbnb.airbnb.services.impl;

import kg.airbnb.airbnb.dto.response.RegionResponse;
import kg.airbnb.airbnb.mappers.region.RegionViewMapper;
import kg.airbnb.airbnb.models.Region;
import kg.airbnb.airbnb.repositories.RegionRepository;
import kg.airbnb.airbnb.services.RegionService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;
    private final RegionViewMapper regionViewMapper;


    public RegionServiceImpl(RegionRepository regionRepository, RegionViewMapper regionViewMapper) {
        this.regionRepository = regionRepository;
        this.regionViewMapper = regionViewMapper;
    }

    @Override
    public List<RegionResponse> getAllRegions() {
        List<Region> allRegions = regionRepository.findAll();
        return regionViewMapper.entityListToDtoList(allRegions);
    }
}
