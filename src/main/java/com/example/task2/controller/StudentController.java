package com.example.task2.controller;

import com.example.task2.payload.ApiResponse;
import com.example.task2.payload.StudentDto;
import com.example.task2.service.StudentService;
import com.example.task2.utilits.BaseUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public HttpEntity<?> addStudent(@Valid @RequestBody StudentDto studentDto){
        ApiResponse apiResponse = studentService.addStudent(studentDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editStudent(@PathVariable Integer id, @Valid @RequestBody StudentDto studentDto){
        ApiResponse apiResponse = studentService.editStudent(id, studentDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteStudent(@PathVariable Integer id){
        ApiResponse apiResponse = studentService.deleteStudent(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PatchMapping("/{id}")
    public HttpEntity<?> enabledOrDisabled(@PathVariable Integer id, @RequestParam  boolean active){
        ApiResponse apiResponse = studentService.enabledOrDisabled(id, active);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> getAllStudent(@RequestParam (defaultValue = BaseUrl.MIN_PAGE) int page,
                                       @RequestParam (defaultValue = BaseUrl.PAGE_SIZE) int size){
        ApiResponse apiResponse = studentService.getAllStudent(page, size);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getByIdStudent(@PathVariable Integer id){
        ApiResponse apiResponse = studentService.getByIdStudent(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @GetMapping("/getAllStudentByGroupId/{groupId}")
    public HttpEntity<?> getAllStudentByGroupId(@PathVariable Integer groupId){
        ApiResponse apiResponse = studentService.getAllStudentByGroupId(groupId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("getByIdStudentIsActiveTrue/{id}")
    public HttpEntity<?> getByIdStudentIsActiveTrue(@PathVariable Integer id){
        ApiResponse apiResponse = studentService.getByIdStudentIsActiveTrue(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/getAllStudentIsActiveTrue")
    public HttpEntity<?> getAllStudentIsActiveTrue(@RequestParam (defaultValue = BaseUrl.MIN_PAGE) int page,
                                       @RequestParam (defaultValue = BaseUrl.PAGE_SIZE) int size){
        ApiResponse apiResponse = studentService.getAllStudentIsActiveTrue(page, size);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }










}
