package com.example.task2.service;

import com.example.task2.entity.University;
import com.example.task2.exception.ResourceNotFoundException;
import com.example.task2.payload.ApiResponse;
import com.example.task2.payload.UniversityDto;
import com.example.task2.repository.AddressRepository;
import com.example.task2.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UniversityService {

    private final UniversityRepository universityRepository;

    private final AddressRepository addressRepository;

    public ApiResponse addUniversity(UniversityDto universityDto) {
        boolean existsAllByName = universityRepository.existsAllByName(universityDto.getName());
        if (existsAllByName){
            return new ApiResponse(false, "Siz kiritgan nomli University mavjud");
        }

        University university=new University(universityDto.getName(),
                addressRepository.findById(universityDto.getAddressId()).orElseThrow(() -> new ResourceNotFoundException("Address mavjud emas")),
                universityDto.getOpenYear(), universityDto.getDescription(), universityDto.isActive());
        universityRepository.save(university);
        return new ApiResponse(true, "University saqlandi");
    }


    public ApiResponse editUniversity(Integer id, UniversityDto universityDto){
        Optional<University> optionalUniversity = universityRepository.findById(id);
        if (optionalUniversity.isEmpty()){
            return new ApiResponse(false, "Siz so`ragan University topilmadi");
        }
        University university=optionalUniversity.get();
        university.setAddress(addressRepository.findById(universityDto.getAddressId()).orElseThrow(() -> new ResourceNotFoundException("Address mavjud emas")));
        university.setDescription(universityDto.getDescription());
        university.setName(universityDto.getName());
        university.setActive(university.isActive());
        university.setOpenYear(university.getOpenYear());
        try {
            universityRepository.save(university);
            return new ApiResponse(false, "University tahrirlandi");
        }catch (Exception e){
            return new ApiResponse(false, "Siz kiritgan "+universityDto.getName()+" nomli University mavjud");
        }
    }

    public ApiResponse enabledOrDisabled(Integer id, boolean active){
        Optional<University> optionalUniversity = universityRepository.findById(id);
        if (optionalUniversity.isPresent()){
            return new ApiResponse(true, "University "+(active?"aktivlashtirildi":"bloklandi"));
        }return new ApiResponse(false, "University topilmadi");
    }

    public ApiResponse getAllUniversity (int page, int size){
        Pageable pageable= PageRequest.of(page, size);
        Page<University> all = universityRepository.findAll(pageable);
        List<UniversityDto> universityDtoList=all.stream().map(this::generateUniversityDto).collect(Collectors.toList());
        return new ApiResponse(true, "All university", universityDtoList);
    }

    public ApiResponse getByIdUniversity(Integer id){
        Optional<University> optionalUniversity = universityRepository.findById(id);
        return optionalUniversity.map(university -> new ApiResponse(true, "University Get By Id", generateUniversityDto(university))).
                orElseGet(() -> new ApiResponse(false, "University mavjud emas"));
    }


    public ApiResponse getAllUniversityIsActiveTrue(int page, int size){
        Pageable pageable=PageRequest.of(page, size);
        Page<University> all = universityRepository.findAllByActiveIsTrue(pageable);
        List<UniversityDto> universityDtoList=all.stream().map(this::generateUniversityDto).collect(Collectors.toList());
        return new ApiResponse(true, "All University IsActive", universityDtoList);
    }

    public ApiResponse getByIdUniversityIsActiveTrue(Integer id){
        University university = universityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("University mavjud emas"));
        return new ApiResponse(true, "University is active", generateUniversityDto(university));
    }


    public ApiResponse getAllUniversityByRegionId(Integer regionId){
        List<University> allByAddress_district_regionId = universityRepository.findAllByAddress_District_RegionId(regionId);
        List<UniversityDto> universityDtoList=allByAddress_district_regionId.stream().
                map(this::generateUniversityDto).collect(Collectors.toList());
        return new ApiResponse(true, "University By Region Id", universityDtoList);
    }

    public ApiResponse getAllUniversityByDistrictId(Integer districtId){
        List<University> allByAddress_districtId = universityRepository.findAllByAddress_DistrictId(districtId);
        List<UniversityDto> universityDtoList=allByAddress_districtId.stream().map(this::generateUniversityDto).collect(Collectors.toList());
        return new ApiResponse(true, "University By District Id", universityDtoList);
    }

    public ApiResponse deleteUniversity(Integer id){
        boolean existsById = universityRepository.existsById(id);
        if (existsById){
            try {
                universityRepository.deleteById(id);
                return new ApiResponse(true, "O`chrildi");
            }catch (Exception e){
                return new ApiResponse(false, "O`chirishdagi xatolik");
            }
        }return new ApiResponse(false, "University mavjus emas");
    }

    private UniversityDto generateUniversityDto(University university){
        return new UniversityDto(university.getId(), university.getName(), university.getAddress().getId(),
                university.getOpenYear(), university.getDescription(), university.isActive());
    }

}
