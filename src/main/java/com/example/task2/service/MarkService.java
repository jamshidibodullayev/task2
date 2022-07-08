package com.example.task2.service;


import com.example.task2.entity.Mark;
import com.example.task2.entity.Student;
import com.example.task2.entity.Subject;
import com.example.task2.exception.ResourceNotFoundException;
import com.example.task2.payload.ApiResponse;
import com.example.task2.payload.MarkDto;
import com.example.task2.repository.JournalRepository;
import com.example.task2.repository.MarkRepository;
import com.example.task2.repository.StudentRepository;
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
public class MarkService {

    private final MarkRepository markRepository;

    private final SubjectRepository subjectRepository;

    private final StudentRepository studentRepository;

    private final JournalRepository journalRepository;
    public ApiResponse addMark(MarkDto markDto){
        boolean existsByStudentIdAndSubjectId = markRepository.existsByStudentIdAndSubjectId(markDto.getStudentId(), markDto.getSubjectId());
        if (existsByStudentIdAndSubjectId){
            return new ApiResponse(false, "Siz kiritgan talaba siz kiritgan fandan baholangan");

        }
        Subject subject = subjectRepository.findById(markDto.getSubjectId()).orElseThrow(() -> new ResourceNotFoundException("Subject topilmadi"));
        Student student = studentRepository.findById(markDto.getStudentId()).orElseThrow(() -> new ResourceNotFoundException("Student topilmadi"));

        List<Subject> collect = student.getGuruh().getJournal().getSubjectList().stream().filter(subject1 -> subject.getId().equals(subject1.getId())).collect(Collectors.toList());
        if (collect.size()==0){
            return new ApiResponse(false, "Gu guruhda siz kiritgan fan o`tilmaydi");
        }
        if (!student.isActive() || !student.getGuruh().isActive() || !student.getGuruh().getFaculty().isActive()||
        !student.getGuruh().getFaculty().getUniversity().isActive() || !student.getGuruh().getFaculty().getUniversity().getAddress().getDistrict().isActive()
                ||!student.getGuruh().getFaculty().getUniversity().getAddress().getDistrict().getRegion().isActive()){
            return new ApiResponse(false, "Student, group, faculty, university, district yoki region aktiv emas");
        }

        if (!subject.isActive()){
            return new ApiResponse(false, "Subject aktiv emas");
        }

        Mark mark=new Mark(markDto.getMark(), student,
                journalRepository.findById(markDto.getJournalId()).orElseThrow(() -> new ResourceNotFoundException("Journal topilmadi")),
                subject);
        markRepository.save(mark);
        return new ApiResponse(true, "Mark saqlandi");
    }

    public ApiResponse editMark(Integer id, MarkDto markDto){
        Mark mark = markRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Mark topilmadi"));

        Subject subject = subjectRepository.findById(markDto.getSubjectId()).orElseThrow(() -> new ResourceNotFoundException("Subject topilmadi"));
        Student student = studentRepository.findById(markDto.getStudentId()).orElseThrow(() -> new ResourceNotFoundException("Student topilmadi"));
        List<Subject> collect = student.getGuruh().getJournal().getSubjectList().stream().filter(subject1 -> subject.getId().equals(subject1.getId())).collect(Collectors.toList());
        if (collect.size()==0){
            return new ApiResponse(false, "Gu guruhda siz kiritgan fan o`tilmaydi");
        }
        if (!student.isActive() && !student.getGuruh().isActive() && !student.getGuruh().getFaculty().isActive()&&
                !student.getGuruh().getFaculty().getUniversity().isActive() && !student.getGuruh().getFaculty().getUniversity().getAddress().getDistrict().isActive()
                &&!student.getGuruh().getFaculty().getUniversity().getAddress().getDistrict().getRegion().isActive()){
            return new ApiResponse(false, "Student, group, faculty, university, district yoki region aktiv emas");
        }

        if (!subject.isActive()){
            return new ApiResponse(false, "Subject aktiv emas");
        }

        mark.setMark(mark.getMark());
        mark.setSubject(subject);
        mark.setStudent(student);
        mark.setJournal(journalRepository.findById(markDto.getJournalId()).orElseThrow(() -> new ResourceNotFoundException("Journal topilmadi")));
        try {
            markRepository.save(mark);
            return new ApiResponse(true, "Mark tahrirlandi");
        }catch (Exception e){
            return new ApiResponse(false, "Bu studentga bu fandan baho qo`yilgan");
        }
    }

    public ApiResponse getAllMark(int page, int size){
        Pageable pageable= PageRequest.of(page, size);
        Page<Mark> markPage = markRepository.findAll(pageable);
        List<MarkDto> markDtoList=markPage.stream().map(this::generateMarkDto).collect(Collectors.toList());
        return new ApiResponse(true, "Mark page", markDtoList);
    }

    public ApiResponse getByIdMark(Integer id){
        Mark mark = markRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Mark topilmadi"));
        return new ApiResponse(true, "Mark by Id", mark);
    }

    public ApiResponse deleteMark(Integer id){
        boolean existsById = markRepository.existsById(id);
        if (existsById){
            try {
                markRepository.deleteById(id);
                return new ApiResponse(true, "Mark o`chirildi");
            }catch (Exception e){
                return new ApiResponse(false, "O`chirilmadi");
            }
        }return new ApiResponse(false, "Mark mavjud emas");

    }

    public MarkDto generateMarkDto (Mark mark){
        return new MarkDto(mark.getId(), mark.getMark(), mark.getStudent().getId(),
                mark.getJournal().getId(), mark.getSubject().getId());

    }





}
