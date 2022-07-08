package com.example.task2.service;


import com.example.task2.entity.Guruh;
import com.example.task2.entity.Journal;
import com.example.task2.entity.Subject;
import com.example.task2.entity.template.AbsIntegerEntity;
import com.example.task2.exception.ResourceNotFoundException;
import com.example.task2.payload.ApiResponse;
import com.example.task2.payload.JournalDto;
import com.example.task2.repository.GuruhRepository;
import com.example.task2.repository.JournalRepository;
import com.example.task2.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JournalService {

    private final JournalRepository journalRepository;

    private final GuruhRepository groupRepository;

    private final SubjectRepository subjectRepository;

    public ApiResponse addJournal(JournalDto journalDto){
        boolean existsByGroupsId = journalRepository.existsByGuruhId(journalDto.getGroupId());
        if (existsByGroupsId){
            return new ApiResponse(false, "Bu guruh uchun journal mavjud");
        }
        Guruh guruh = guruh(journalDto.getGroupId());
        if (guruh.isActive()) {
            Set<Subject> subjectSet=subjectSet(journalDto.getSubjectList()).stream().filter(Subject::isActive).collect(Collectors.toSet());
            Journal journal = new Journal(journalDto.getName(), guruh, subjectSet);
            journalRepository.save(journal);
            return new ApiResponse(true, "Journal saqlandi");
        }
        return new ApiResponse(false, "Group bloklangan");
    }

    public ApiResponse editJournal(Integer id, JournalDto journalDto){
        Journal journal = journalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Journal topilmadi"));
        Guruh guruh = guruh(journalDto.getGroupId());
        if (guruh.isActive()) {
            Set<Subject> subjectSet=subjectSet(journalDto.getSubjectList()).stream().filter(Subject::isActive).collect(Collectors.toSet());
            journal.setGuruh(guruh);
            journal.setSubjectList(subjectSet);
            try {
                journalRepository.save(journal);
                return new ApiResponse(true, "Journal tahrirlandi");
            }catch (Exception e){
                return new ApiResponse(false, "Xatolik");
            }
        }
        return new ApiResponse(false, "Group bloklangan");
    }

    public ApiResponse deleteJournal (Integer id){
        boolean existsById = journalRepository.existsById(id);
        if (existsById){
            try {
                journalRepository.deleteById(id);
                return new ApiResponse(true, "O`chirildi");
            }catch (Exception e){
                return new ApiResponse(false, "O`chirishni imkoni yo`q");
            }
        }
        return new ApiResponse(false, "Mavjud emas");

    }

    public ApiResponse journalAddSubject(Integer id, Integer subjectId){
        Journal journal = journalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Journal topilmadi"));
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new ResourceNotFoundException("Subject topilmadi"));
        if (subject.isActive()){
            boolean add = journal.getSubjectList().add(subject);
            if (add){
                journalRepository.save(journal);
                return new ApiResponse(true, "Fan qo`shildi");
            }return new ApiResponse(false, "Journalga ushbu fan oldindan mavjud");
        }return new ApiResponse(false, "Subject bloklangan. " +
                "Siz bloklangan Subjectni Journalga qo`sh olmaysiz");
    }

    public ApiResponse journalDeleteSubject(Integer id, Integer subjectId){
        Journal journal = journalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Journal topilmadi"));
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new ResourceNotFoundException("Subject topilmadi"));
        boolean remove = journal.getSubjectList().remove(subject);
        if (remove){
            journalRepository.save(journal);
            return new ApiResponse(true, "Subject o`chirildi");
        }return new ApiResponse(false, "Journalga subject mavjud emas");
    }

    public ApiResponse getAllJournal(int page, int size){
        Pageable pageable= PageRequest.of(page, size);
        Page<Journal> journalPage = journalRepository.findAll(pageable);
        List<JournalDto> journalDtoList=journalPage.stream().map(this::generateJournalDto).collect(Collectors.toList());
        return new ApiResponse(true, "Journal All", journalDtoList);
    }

    public ApiResponse getByIdJournal(Integer id){
        Journal journal = journalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Journal topilmadi"));
        return new ApiResponse(true, "Journal Get By Id", journal);
    }

    public ApiResponse getByJournalByGroupId(Integer groupId){
        Journal journal = journalRepository.findByGuruhId(groupId).orElseThrow(() -> new ResourceNotFoundException("Journal topilmadi"));
        return new ApiResponse(true, "Journal", journal);
    }

    public List<Subject> subjectSet(Set<Integer> integerSet){
       return subjectRepository.findAllById(integerSet);
    }

    public JournalDto generateJournalDto(Journal journal){
        return new JournalDto(journal.getId(), journal.getName(), journal.getGuruh().getId(),
                journal.getSubjectList().stream().map(AbsIntegerEntity::getId).collect(Collectors.toSet()));
    }

    public Guruh guruh (Integer id){
        return groupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Group topilmadi"));
    }


}
