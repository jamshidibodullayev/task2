package com.example.task2.service;

import com.example.task2.entity.District;
import com.example.task2.entity.Region;
import com.example.task2.exception.ResourceNotFoundException;
import com.example.task2.payload.ApiResponse;
import com.example.task2.payload.DistrictDto;
import com.example.task2.repository.DistrictRepository;
import com.example.task2.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;
    private final RegionRepository regionRepository;


    public ApiResponse addDistrict(DistrictDto districtDto){
        boolean exists = districtRepository.existsByName(districtDto.getName());
        if (exists){
            return new ApiResponse(false, "Siz kiritgan "+districtDto.getName()+" nomli District mavjud");
        }

        District district=new District(districtDto.getName(), districtDto.getDescription(),
                districtDto.isActive(), regionRepository.findById(districtDto.getRegionId()).orElseThrow(() -> new ResourceNotFoundException("Region topilmadi")));
        districtRepository.save(district);
        return new ApiResponse(true, "District saqlandi");
    }

    public ApiResponse editDistrict(Integer id, DistrictDto districtDto){
        District district = districtRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("District topilmadi"));
        district.setRegion(regionRepository.findById(districtDto.getRegionId()).orElseThrow(() -> new ResourceNotFoundException("Region topilmadi")));
        district.setActive(districtDto.isActive());
        district.setDescription(district.getDescription());
        district.setName(district.getName());
        try {
            districtRepository.save(district);
            return new ApiResponse(true, "District tahrirlandi");
        }catch (Exception e){
            return new ApiResponse(false, "Siz kiritgan "+district.getName()+"noli district mavjud");
        }
    }

    public ApiResponse deleteDistrict(Integer id){
        boolean existsById = districtRepository.existsById(id);
        if (existsById){
            try {
                districtRepository.deleteById(id);
                return new ApiResponse(true, "O`chirildi");
            }catch (Exception e){
                return new ApiResponse(false, "O`chirishning imkoni yo`q");
            }
        }return new ApiResponse(false, "District mavjud emas");
    }

    public ApiResponse getAllDistrict(int page, int size){
        Pageable pageable= PageRequest.of(page, size);
        Page<District> all = districtRepository.findAll(pageable);
        List<DistrictDto> districtDtoList=all.stream().map(this::generateDistrictDto).collect(Collectors.toList());
        return new ApiResponse(true, "Districtlar ", districtDtoList);
    }

    private DistrictDto generateDistrictDto(District district){
        return new DistrictDto(district.getId(), district.getName(), district.getDescription(),
                district.getRegion().isActive(), district.getRegion().getId());
    }

    public ApiResponse getByIdDistrict(Integer id){
        Optional<District> optionalDistrict = districtRepository.findById(id);
        return optionalDistrict.map(district -> new ApiResponse(true, "District: ", generateDistrictDto(district))).
                orElseGet(() -> new ApiResponse(false, "District mavjud emas"));


    }

    public ApiResponse enabledOrDisabled(Integer id, boolean active){
        District district = districtRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("District topilmadi"));
            district.setActive(active);
            districtRepository.save(district);
            return new ApiResponse(true, "District "+(active?"aktivlashtirildi":"bloklandi"));
    }

    public ApiResponse getByIdDistrictIsActive(Integer id){
        Optional<District> optionalDistrict = districtRepository.findById(id);
        if (optionalDistrict.isPresent()){
            if (optionalDistrict.get().isActive()){
                return new ApiResponse(true, "District: ",
                        generateDistrictDto(optionalDistrict.get()));
            }
        }return new ApiResponse(false, "District mavjud emas");
    }

    public ApiResponse getAllDistrictIsActive(){
        List<District> allByActive = districtRepository.findAllByActiveOrderByName(true);
        List<DistrictDto> districtDtoList=allByActive.stream().map(this::generateDistrictDto).collect(Collectors.toList());
        return new ApiResponse(true, "Aktiv Districtlar: ", districtDtoList);
    }

    public ApiResponse getAllDistrictByRegionId(Integer regionId){
        List<District> allByRegion_id = districtRepository.getAllByRegion_Id(regionId);
        List<DistrictDto> districtDtoList=allByRegion_id.stream().map(this::generateDistrictDto).collect(Collectors.toList());
        return new ApiResponse(true, "Districtlar viloyat bo`yicha", districtDtoList);

    }

    public ApiResponse getAllDistrictByRegionIdIsActive(Integer regionId){
        Optional<Region> optionalRegion = regionRepository.findById(regionId);
        List<DistrictDto> districtDtoList;
        if (optionalRegion.isPresent()){
            if (optionalRegion.get().isActive()){
                List<District> allByRegion_id = districtRepository.getAllByRegion_Id(regionId);
                districtDtoList=allByRegion_id.stream().map(this::generateDistrictDto).collect(Collectors.toList());
                return new ApiResponse(true, "Aktive Districts: ", districtDtoList);
            } return new ApiResponse(false, "Region bloklangan");
        }
        return new ApiResponse(false, "Region mavjud emas");
    }





}
