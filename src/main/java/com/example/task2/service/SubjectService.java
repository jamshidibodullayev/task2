package com.example.task2.service;


import com.example.task2.entity.Subject;
import com.example.task2.exception.ResourceNotFoundException;
import com.example.task2.payload.ApiResponse;
import com.example.task2.payload.SubjectDto;
import com.example.task2.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public ApiResponse addSubject(Subject subject) {
        boolean existsAllByName = subjectRepository.existsAllByName(subject.getName());
        if (existsAllByName){
            return new ApiResponse(false, "Siz kiritgan nomli fan mavjud");
        }
        subjectRepository.save(subject);
        return new ApiResponse(true, "Subject saqlandi");
    }

    public ApiResponse editSubject(Integer id, Subject subject) {
        Subject saveSubject = subjectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Subject topilmadi"));
        saveSubject.setDescription(subject.getDescription());
        saveSubject.setActive(subject.isActive());
        saveSubject.setName(subject.getName());
        try {
            subjectRepository.save(saveSubject);
            return new ApiResponse(true, "Tahrirlandi");
        }catch (Exception e){
            return new ApiResponse(false, "Siz kiritgan nomli subject mavjud");
        }


    }

    public ApiResponse deleteSubject(Integer id) {
        boolean existsById = subjectRepository.existsById(id);
        if (existsById){
            try {
                subjectRepository.deleteById(id);
                return new ApiResponse(true, "Siz kiritgan fan o`chirildi");
            }catch (Exception e){
                return new ApiResponse(false, "O`chirilmadi");
            }
        }return new ApiResponse(false, "Siz so`ragan Subject topilmadi");
    }

    public ApiResponse enabledOrDisabled(Integer id, boolean active) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Subject topilmadi"));
        subject.setActive(active);
        return new ApiResponse(true, "Subject "+(active?"aktivlashtirildi":"bloklandi"));
    }

    public ApiResponse getAllSubject(int page, int size) {
        Pageable pageable= PageRequest.of(page, size);
        Page<Subject> subjectPage = subjectRepository.findAll(pageable);
        List<SubjectDto> subjectList=subjectPage.stream().map(this::generateSubjectDto).collect(Collectors.toList());
        return new ApiResponse(true, "All subject by page", subjectList);
    }

    public ApiResponse getByIdSubject(Integer id) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Subject topilmadi"));
        return new ApiResponse(true, "Subject get By Id", generateSubjectDto(subject));
    }


    public ApiResponse getAllSubjectIsActiveTrue(int page, int size) {
        Pageable pageable=PageRequest.of(page, size);
        Page<Subject> subjectPage = subjectRepository.findAllByActiveIsTrue(pageable);
        List<SubjectDto> subjectList = subjectPage.stream().map(this::generateSubjectDto).collect(Collectors.toList());
        return new ApiResponse(true, "Activ Subjectlar", subjectList);
    }


    public ApiResponse getByIdSubjectIsActiveTrue(Integer id) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Subject topilmadi"));
        return new ApiResponse(true, "Subject get By Id", subject.isActive()?generateSubjectDto(subject):"Subject bloklangan");
    }

    private SubjectDto generateSubjectDto(Subject subject){
        return new SubjectDto(subject.getId(), subject.getName(), subject.getDescription(), subject.isActive());
    }
}
