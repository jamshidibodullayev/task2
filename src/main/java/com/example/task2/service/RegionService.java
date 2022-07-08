package com.example.task2.service;

import com.example.task2.entity.Region;
import com.example.task2.exception.ResourceNotFoundException;
import com.example.task2.payload.ApiResponse;
import com.example.task2.payload.RegionDto;
import com.example.task2.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;


    public ApiResponse addRegion(Region region) {
        boolean exists = regionRepository.existsByName(region.getName());
        if (exists) {
            return new ApiResponse(false, "Siz kiritgan "+region.getName()+" nomli Region mavjud");
        }
        regionRepository.save(region);
        return new ApiResponse(true, "Region saqlandi");
    }


    public ApiResponse editRegion(Integer id, Region region){
        Region savedRegion = regionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Region topilmadi"));

        savedRegion.setActive(region.isActive());
        savedRegion.setName(region.getName());
        savedRegion.setDescription(region.getDescription());
        try {
            regionRepository.save(savedRegion);
            return new ApiResponse(true, "Region tahrirlandi.");
        }catch (Exception e){
            return new ApiResponse(false, "Siz kiritgan "+
                    region.getName()+" lik Region mavjud");
        }
    }

    public ApiResponse deleteRegion(Integer id){
        boolean existsById = regionRepository.existsById(id);
        if (existsById){
            regionRepository.deleteById(id);
            return new ApiResponse(true, "Muvaffiyaqatli o`chirildi");
        }return new ApiResponse(false, "Mavjud emas");
    }

    public ApiResponse enabledOrDisabled(Integer id, boolean active){
        Region savedRegion = regionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Region topilmadi"));
        savedRegion.setActive(active);
        return new ApiResponse(true, "Region "+(active?"aktivlashtirildi":"bloklandi"));
    }

    public ApiResponse getAllRegionForAdmin(int page, int size){
        Pageable pageable= PageRequest.of(page, size);
        Page<Region> all = regionRepository.findAll(pageable);
        List<RegionDto> regionList=all.stream().map(this::generateRegionDto).collect(Collectors.toList());
        return new ApiResponse(true, "Barcha Regionlar ", regionList);

    }

    public ApiResponse getByIdRegion(Integer id){
        Optional<Region> optionalRegion = regionRepository.findById(id);
        return optionalRegion.map(region -> new ApiResponse(true, "Region: ", region)).orElseGet(() -> new ApiResponse(false, "Region mavjud emas"));
    }


    public ApiResponse getByIdRegionIsActive(Integer id){
        Region savedRegion = regionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Region topilmadi"));
        if (savedRegion.isActive()){
            return new ApiResponse(true, "Region Get By Id", generateRegionDto(savedRegion));
        }
        return new ApiResponse(false, "Region blocked");
    }

    public ApiResponse getAllRegionIsActive(int page, int size){
        Pageable pageable=PageRequest.of(page, size);
        Page<Region> all = regionRepository.findAll(pageable);
        List<RegionDto> regionDtoList=all.stream().map(this::generateRegionDto).collect(Collectors.toList());
        return new ApiResponse(true, "Faol Regionlar ", regionDtoList);
    }

    public RegionDto generateRegionDto(Region region){
        return new RegionDto(region.getId(), region.getName(), region.getDescription(),
                region.isActive());
    }



}
