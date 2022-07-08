package com.example.task2.controller;


import com.example.task2.entity.Subject;
import com.example.task2.payload.ApiResponse;
import com.example.task2.service.SubjectService;
import com.example.task2.utilits.BaseUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/subject")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public HttpEntity<?> addSubject(@Valid @RequestBody Subject subject){
        ApiResponse apiResponse = subjectService.addSubject(subject);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editSubject(@PathVariable Integer id, @Valid @RequestBody Subject subject){
        ApiResponse apiResponse = subjectService.editSubject(id, subject);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteSubject(@PathVariable Integer id){
        ApiResponse apiResponse = subjectService.deleteSubject(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PatchMapping("/{id}")
    public HttpEntity<?> enabledOrDisabled(@PathVariable Integer id, @RequestParam boolean active){
        ApiResponse apiResponse = subjectService.enabledOrDisabled(id, active);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> getAllSubject(@RequestParam (defaultValue = BaseUrl.MIN_PAGE) int page,
                                       @RequestParam (defaultValue = BaseUrl.PAGE_SIZE) int size){
        ApiResponse apiResponse = subjectService.getAllSubject(page, size);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);

    }

    @GetMapping("/{id}")
    public HttpEntity<?> getByIdSubject(@PathVariable Integer id){
        ApiResponse apiResponse = subjectService.getByIdSubject(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/getAllSubjectIsActiveTrue")
    public HttpEntity<?> getAllSubjectIsActiveTrue(@RequestParam (defaultValue = BaseUrl.MIN_PAGE) int page,
                                                   @RequestParam (defaultValue = BaseUrl.PAGE_SIZE) int size){
        ApiResponse apiResponse = subjectService.getAllSubjectIsActiveTrue(page, size);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("getByIdSubjectIsActiveTrue/{id}")
    public HttpEntity<?> getByIdSubjectIsActiveTrue(@PathVariable Integer id){
        ApiResponse apiResponse = subjectService.getByIdSubjectIsActiveTrue(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

}
