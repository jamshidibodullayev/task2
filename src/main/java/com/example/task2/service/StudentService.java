package com.example.task2.service;

import com.example.task2.entity.Address;
import com.example.task2.entity.Guruh;
import com.example.task2.entity.Student;
import com.example.task2.exception.ResourceNotFoundException;
import com.example.task2.payload.ApiResponse;
import com.example.task2.payload.StudentDto;
import com.example.task2.repository.AddressRepository;
import com.example.task2.repository.GuruhRepository;
import com.example.task2.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    private final GuruhRepository groupRepository;

    private final AddressRepository addressRepository;


    public ApiResponse addStudent(StudentDto studentDto){
        Address address = address(studentDto.getAddressId());
        Guruh guruh = guruh(studentDto.getGroupId());
        Student student=new Student(studentDto.getName(), studentDto.isActive(),guruh, address);
        studentRepository.save(student);
        return new ApiResponse(true, "Student saqlandi");
    }

    public ApiResponse editStudent(Integer id, StudentDto studentDto){
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student mavjud emas"));
        student.setActive(studentDto.isActive());
        student.setName(studentDto.getName());
        student.setGuruh(groupRepository.findById(studentDto.getGroupId()).orElseThrow(() -> new ResourceNotFoundException("Group topilmadi")));
        student.setAddress(addressRepository.findById(studentDto.getAddressId()).orElseThrow(() -> new ResourceNotFoundException("Address topilmadi")));
        studentRepository.save(student);
        return new ApiResponse(true, "Student tahrirlandi");
    }

    public ApiResponse deleteStudent(Integer id){
        boolean existsById = studentRepository.existsById(id);
        if (existsById){
            try {
                studentRepository.deleteById(id);
                return new ApiResponse(true, "Student o`chirildi");
            }catch (Exception e){
                throw new ResourceNotFoundException("O`chirishda xatolik");
            }
        } return new ApiResponse(false, "Student mavjud emas");
    }

    public ApiResponse getAllStudent(int page, int size){
        Pageable pageable= PageRequest.of(page, size);
        Page<Student> all = studentRepository.findAll(pageable);
        List<StudentDto> studentDtoList=all.stream().map(this::generateStudentDto).collect(Collectors.toList());
        return new ApiResponse(true, "Page Students", studentDtoList);
    }

    public ApiResponse getByIdStudent(Integer id){
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student mavjud ems"));
        return new ApiResponse(true, "Student by Id", student);
    }

    public ApiResponse getAllStudentByGroupId (Integer groupId){
        Guruh guruh = groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Guruh mavjud emas"));
        List<StudentDto> studentDtoList=guruh.getStudentList().stream().map(this::generateStudentDto).collect(Collectors.toList());
        return new ApiResponse(true, "All student By Group Id", studentDtoList);
    }

    public ApiResponse getAllStudentIsActiveTrue(int page, int size){
        Pageable pageable=PageRequest.of(page, size);
        Page<Student> allByActiveIsTrue = studentRepository.findAllByActiveIsTrue(pageable);
        List<StudentDto> studentDtoList=allByActiveIsTrue.stream().map(this::generateStudentDto).collect(Collectors.toList());
        return new ApiResponse(true, "All active Student", studentDtoList);
    }

    public ApiResponse getByIdStudentIsActiveTrue(Integer id){
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student mavjud ems"));
        return new ApiResponse(true, "Student by Id Is active true", student.isActive()?generateStudentDto(student):"Student bloklangan");
    }

    public StudentDto generateStudentDto(Student student){
        return new StudentDto(student.getId(), student.getName(), student.isActive(), student.getGuruh().getId(),
                student.getAddress().getId());
    }


    public ApiResponse enabledOrDisabled(Integer id, boolean active) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student topilmadi"));
        student.setActive(active);
        studentRepository.save(student);
        return new ApiResponse(true, "Student "+(active?"aktivlashtirildi":"bloklandi"));
    }

    public Address address(Integer id){
      return addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address topilmadi"));
    }

    public Guruh guruh(Integer id){
        return groupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Guruh mavjud emas"));
    }
}
