package com.example.task2.service;


import com.example.task2.entity.Guruh;
import com.example.task2.exception.ResourceNotFoundException;
import com.example.task2.payload.ApiResponse;
import com.example.task2.payload.GroupDto;
import com.example.task2.repository.FacultyRepository;
import com.example.task2.repository.GuruhRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GuruhRepository groupRepository;

    private final FacultyRepository facultyRepository;

    public ApiResponse addGroup(GroupDto groupDto){
        boolean existsByNameAndFacultyId = groupRepository.existsByNameAndFacultyId(groupDto.getName(), groupDto.getFacultyId());
        if (existsByNameAndFacultyId){
            return new ApiResponse(false, "Bu fakultetda bunday guruh mavjud");
        }
        Guruh guruh=new Guruh(groupDto.getName(), groupDto.isActive(), groupDto.getDescription(),
                facultyRepository.findById(groupDto.getFacultyId()).orElseThrow(() -> new ResourceNotFoundException("Faculty topilmadi")),
                groupDto.getYear());
        groupRepository.save(guruh);
        return new ApiResponse(true, "Saqlandi");
    }


    public ApiResponse editGroup(Integer id, GroupDto groupDto){
        Guruh guruh = groupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Group topilmadi"));
        guruh.setDescription(groupDto.getDescription());
        guruh.setName(groupDto.getName());
        guruh.setYear(groupDto.getYear());
        guruh.setActive(groupDto.isActive());
        guruh.setFaculty(facultyRepository.findById(groupDto.getFacultyId()).
                orElseThrow(() -> new ResourceNotFoundException("Faculty topilmadi")));
        try {
            groupRepository.save(guruh);
            return new ApiResponse(true, "Tahrirlandi");
        }catch (Exception e){
            return new ApiResponse(false, "Siz kiritgan nomli guruh Faculty da bor");
        }
    }

    public ApiResponse deleteGroup(Integer id){
        boolean existsById = groupRepository.existsById(id);
        if (existsById){
            try {
                groupRepository.deleteById(id);
                return new ApiResponse(true, "O`chirildi");
            }catch (Exception e){
                return new ApiResponse(false, "O`chirishni imkoni yo`q");
            }
        }
        return new ApiResponse(false, "Mavjud emas");

    }

    public ApiResponse getAllGroup(int page, int size){
        Pageable pageable= PageRequest.of(page, size);
        Page<Guruh> groupPage = groupRepository.findAll(pageable);
        List<GroupDto> groupDtoList=groupPage.stream().map(this::generateGroupDto).collect(Collectors.toList());
        return new ApiResponse(true, "Group Page ", groupDtoList);
    }

    public ApiResponse getAllGroupByFacultyId(Integer facultyId){
        List<Guruh> allByFacultyId = groupRepository.findAllByFacultyId(facultyId);
        List<GroupDto> groupDtoList=allByFacultyId.stream().map(this::generateGroupDto).collect(Collectors.toList());
        return new ApiResponse(true, "Groups Get By Faculty Id", groupDtoList);
    }

    public ApiResponse getByIdGroup(Integer id){
        Guruh group = groupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Group topilmadi"));
        return new ApiResponse(true, "Group By Id", group);
    }

    public ApiResponse getAllGroupIsActiveTrue(int page, int size){
        Pageable pageable= PageRequest.of(page, size);
        Page<Guruh> groupPage = groupRepository.findAllByActiveIsTrue(pageable);
        List<GroupDto> groupDtoList=groupPage.stream().map(this::generateGroupDto).collect(Collectors.toList());
        return new ApiResponse(true, "Group Page Is active", groupDtoList);
    }

    private GroupDto generateGroupDto(Guruh guruh){
        return new GroupDto(guruh.getId(), guruh.getName(), guruh.isActive(),
                guruh.getDescription(),guruh.getFaculty().getId(), guruh.getYear());
    }


    public ApiResponse getByIdGroupIsActiveTrue(Integer id){
        Guruh group = groupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Group topilmadi"));
        return new ApiResponse(true, "Group By Id", group.isActive()?group:"Group bloklangan");
    }

    public ApiResponse enabledOrDisabled(Integer id, boolean active) {
        Guruh guruh = groupRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Group topilmadi"));
        guruh.setActive(active);
        groupRepository.save(guruh);
        return new ApiResponse(true, "Group "+(active?"aktivlashtirildi":"bloklandi"));
    }
}
