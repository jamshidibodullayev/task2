package com.example.task2.controller;


import com.example.task2.payload.ApiResponse;
import com.example.task2.service.AdditionService;
import com.example.task2.utilits.BaseUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/addition")
@RequiredArgsConstructor
public class AdditionController {

    private final AdditionService additionService;

    @GetMapping("/StudentSubject/{studentId}")
    public HttpEntity<?> StudentSubject (@PathVariable Integer studentId){
        ApiResponse apiResponse = additionService.getStudentSubject(studentId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/getGroupsAndSumAmountStudent/{facultyId}")
    public HttpEntity<?> getGroupsAndSumAmountStudent (@PathVariable Integer facultyId){
        ApiResponse apiResponse = additionService.getGroupsAndSumAmountStudent(facultyId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/getStudentGroupAndFaculty1")
    public HttpEntity<?> getSearchStudentNameGroupAndFaculty1(@RequestParam @NotNull(message = "Bo`sh bo`lishi mumkin emas")
                                                                  String name){
        ApiResponse api = additionService.getSearchStudentNameGroupAndFaculty1(name);
        return ResponseEntity.status(api.isSuccess()?200:409).body(api);
    }

    @GetMapping("/getStudentGroupAndFaculty2")
    public HttpEntity<?> getSearchStudentNameGroupAndFaculty2(@RequestParam @NotNull(message = "Bo`sh bo`lishi mumkin emas")
                                                              String text){
        ApiResponse api = additionService.getSearchStudentNameGroupAndFaculty2(text);
        return ResponseEntity.status(api.isSuccess()?200:409).body(api);
    }


    @GetMapping("/getStudentNameAverageMark/{groupId}")
    public HttpEntity<?> getStudentNameAverageMark(@PathVariable Integer groupId){
        ApiResponse api = additionService.getStudentNameAverageMark(groupId);
        return ResponseEntity.status(api.isSuccess() ? 200 : 409).body(api);
    }


}
