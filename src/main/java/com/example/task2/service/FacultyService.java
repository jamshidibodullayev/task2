package com.example.task2.service;


import com.example.task2.entity.Faculty;
import com.example.task2.exception.ResourceNotFoundException;
import com.example.task2.payload.ApiResponse;
import com.example.task2.payload.FacultyDto;
import com.example.task2.repository.FacultyRepository;
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
public class FacultyService {

    private final FacultyRepository facultyRepository;

    private final UniversityRepository universityRepository;

    public ApiResponse addFaculty (FacultyDto facultyDto){
        boolean existsAllByNameAndUniversityId =
                facultyRepository.existsAllByNameAndUniversityId(facultyDto.getName(), facultyDto.getUniversityId());
        if (existsAllByNameAndUniversityId) {
            return new ApiResponse(false, "Siz kiritgan nomli Facultet ushbu inuversitetda mavjud");
        }


        Faculty faculty=new Faculty(facultyDto.getName(), facultyDto.getDescription(), facultyDto.isActive(),
                universityRepository.findById(facultyDto.getUniversityId()).orElseThrow(() -> new ResourceNotFoundException("University mavjud emas"))
        );
        facultyRepository.save(faculty);
        return new ApiResponse(true, "Faculty saqlandi");
    }

    public ApiResponse editFaculty(Integer id, FacultyDto facultyDto){
        Faculty faculty = facultyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Faculty mavjud emas"));
        faculty.setActive(facultyDto.isActive());
        faculty.setName(faculty.getName());
        faculty.setUniversity(universityRepository.findById(facultyDto.getUniversityId()).orElseThrow(() -> new ResourceNotFoundException("University mavjud emas")));
        faculty.setDescription(facultyDto.getDescription());
        try {
            facultyRepository.save(faculty);
            return new ApiResponse(false, "Faculty saqlandi");
        }catch (Exception e){
            return new ApiResponse(false, "Siz kiritgan "+facultyDto.getName()+
                    " nomli fakulty siz tanlagan universityda mavjud");
        }

    }

    public ApiResponse enabledOrDisabledFaculty(Integer id, boolean active){
        Faculty faculty = facultyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Faculty mavjud emas"));
        faculty.setActive(active);
        facultyRepository.save(faculty);
        return new ApiResponse(true, "Faculty "+(active?"aktivlashtirildi":"bloklandi"));
    }

    public ApiResponse getAllFaculty(int page, int size){
        Pageable pageable= PageRequest.of(page, size);
        Page<Faculty> all = facultyRepository.findAll(pageable);
        List<FacultyDto> facultyDtoList=all.stream().map(this::generateFacultyDto).collect(Collectors.toList());
        return new ApiResponse(true, "All faculty", facultyDtoList);
    }

    public ApiResponse getByIdFaculty(Integer id){
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        return optionalFaculty.map(faculty -> new ApiResponse(true, "Faculty get By Id", generateFacultyDto(faculty))).orElseGet(() -> new ApiResponse(false, "Faculty mavjud emas"));
    }

    public ApiResponse getAllFacultyIsActiveTrue(int page, int size){
        Pageable pageable=PageRequest.of(page, size);
        Page<Faculty> allByActiveIsTrue = facultyRepository.findAllByActiveIsTrue(pageable);
        List<FacultyDto> facultyDtoList=allByActiveIsTrue.stream().map(this::generateFacultyDto).collect(Collectors.toList());
        return new ApiResponse(true, "All Active Faculty", facultyDtoList);
    }

    public ApiResponse getByIdFacultyIsActiveTrue(Integer id){

        Faculty faculty = facultyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Faculty topilmadi"));
        if (faculty.isActive()){
            return new ApiResponse(true, "Faculty is Activ");
        }
        return new ApiResponse(false, "Faculty bloklangan");

    }

    public ApiResponse getAllFacultyByUniversityId(Integer universityId){
        List<Faculty> allByUniversityId = facultyRepository.findAllByUniversityId(universityId);
        List<FacultyDto> facultyDtoList=allByUniversityId.stream().map(this::generateFacultyDto).collect(Collectors.toList());
        return new ApiResponse(true, "All Faculty By University id", facultyDtoList);

    }

    public ApiResponse getAllFacultyIsActiveTrueByUniversityId(Integer universityId){
        List<Faculty> allByUniversityIdAndActiveIsTrue = facultyRepository.findAllByUniversityIdAndActiveIsTrue(universityId);
        List<FacultyDto> facultyDtoList=allByUniversityIdAndActiveIsTrue.stream().map(this::generateFacultyDto).collect(Collectors.toList());
        return new ApiResponse(true, "All activ Faculty ", facultyDtoList);
    }

    private FacultyDto generateFacultyDto(Faculty faculty){
        return new FacultyDto(faculty.getId(), faculty.getName(), faculty.getDescription(), faculty.isActive(),
                faculty.getUniversity().getId());
    }

    public ApiResponse deleteFaculty(Integer id) {
        boolean existsById = facultyRepository.existsById(id);
        if (existsById){
            try {
                facultyRepository.deleteById(id);
                return new ApiResponse(true, "O`chirildi");
            }catch (Exception e){
                return new ApiResponse(false, "O`chirishni imkoni mavjud emas");
            }
        }
        return new ApiResponse(false, "Faculty mavjud emas");
    }
}
