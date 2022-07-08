package com.example.task2.service;

import com.example.task2.entity.*;
import com.example.task2.exception.ResourceNotFoundException;
import com.example.task2.payload.ApiResponse;
import com.example.task2.payload.GroupsStudentAmount;
import com.example.task2.payload.StudentAverageMark;
import com.example.task2.payload.StudentGroupAndFacultyDto;
import com.example.task2.repository.*;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdditionService {

    private final StudentRepository studentRepository;
    private final GuruhRepository groupRepository;

    private final FacultyRepository facultyRepository;



    /**Qo`shimcha 1-task**/
    public ApiResponse getStudentSubject(Integer studentId){
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student topilmadi"));
        List<Subject> subjectList = new ArrayList<>(student.getGuruh().getJournal().getSubjectList());
        return new ApiResponse(true, "Talabaning o`qiydigan fanlari", subjectList.stream().map(Subject::getName).collect(Collectors.toList()));
    }


    /**Qo`shimcha 2-task**/
    public ApiResponse getGroupsAndSumAmountStudent(Integer facultyId){
        Faculty faculty = facultyRepository.findById(facultyId).orElseThrow(() -> new ResourceNotFoundException("Faculty topilmadi"));
        List<GroupsStudentAmount> groupsStudentAmountList=faculty.getGuruhList().stream().map(group ->
                new GroupsStudentAmount(group.getId(), group.getName(), group.getStudentList().size())).collect(Collectors.toList());
        return new ApiResponse(false, "", groupsStudentAmountList);
    }

    /**Qo`shimcha 3-task**/
    public ApiResponse getSearchStudentNameGroupAndFaculty1(String name){
        Student student = studentRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Student mavjud emas"));
        return new ApiResponse(true, "Student : ",generateStudentGroupAndFacultyDto(student));
    }


    private StudentGroupAndFacultyDto generateStudentGroupAndFacultyDto(@NotNull Student student){
        return new StudentGroupAndFacultyDto(
                student.getId(), student.getName(), student.getGuruh().getId(), student.getGuruh().getName(),
                student.getGuruh().getFaculty().getId(), student.getGuruh().getFaculty().getName());
    }

    /**Qo`shimcha 3-task ikkinchi usul**/
    public ApiResponse getSearchStudentNameGroupAndFaculty2(String searchText){
        List<Student> allByNameContainingIgnoreCase = studentRepository.findAllByNameContainingIgnoreCase(searchText);
        List<StudentGroupAndFacultyDto> studentGroupAndFacultyDtoList=
                allByNameContainingIgnoreCase.stream().map(this::generateStudentGroupAndFacultyDto).collect(Collectors.toList());
        return new ApiResponse(true, "All Students", studentGroupAndFacultyDtoList);
    }




    /**Qo`shimcha 4-task ikkinchi usul**/
    public ApiResponse getStudentNameAverageMark(Integer groupId){
        Guruh groups = groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Group topilmadi"));
        List<StudentAverageMark> studentAverageMarkList=groups.getStudentList().stream().map(this::generateStudentAverageMark).
                sorted((o1, o2) -> o1.getMark()>o2.getMark()?-1:0).collect(Collectors.toList());

        return new ApiResponse(true, "Student", studentAverageMarkList);
    }

    private StudentAverageMark generateStudentAverageMark(Student student){
        int average = student.getMarkList().stream().mapToInt(Mark::getMark).sum()/(student.getMarkList().size());
        return new StudentAverageMark(student.getName(),average);
    }






}
