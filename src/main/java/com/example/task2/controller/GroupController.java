package com.example.task2.controller;


import com.example.task2.payload.ApiResponse;
import com.example.task2.payload.GroupDto;
import com.example.task2.service.GroupService;
import com.example.task2.utilits.BaseUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public HttpEntity<?> addGroup(@Valid @RequestBody GroupDto groupDto){
        ApiResponse apiResponse = groupService.addGroup(groupDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editGroup(@PathVariable Integer id, @Valid @RequestBody GroupDto groupDto){
        ApiResponse apiResponse = groupService.editGroup(id, groupDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PatchMapping("/{id}")
    public HttpEntity<?> enabledOrDisabled(@PathVariable Integer id, @RequestParam boolean active){
        ApiResponse apiResponse = groupService.enabledOrDisabled(id, active);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> enabledOrDisabled(@PathVariable Integer id){
        ApiResponse apiResponse = groupService.deleteGroup(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> getAllGroup(@RequestParam(defaultValue = BaseUrl.MIN_PAGE) int page,
                                     @RequestParam (defaultValue = BaseUrl.PAGE_SIZE) int size){
        ApiResponse apiResponse = groupService.getAllGroup(page, size);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getAllGroup(@PathVariable Integer id){
        ApiResponse apiResponse = groupService.getByIdGroup(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("getByIdGroupIsActiveTrue/{id}")
    public HttpEntity<?> getByIdGroupIsActiveTrue(@PathVariable Integer id){
        ApiResponse apiResponse = groupService.getByIdGroupIsActiveTrue(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/getAllGroupByFacultyId/{facultyId}")
    public HttpEntity<?> getAllGroupByFacultyId(@PathVariable Integer facultyId){
        ApiResponse apiResponse = groupService.getAllGroupByFacultyId(facultyId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/getAllGroupIsActiveTrue")
    public HttpEntity<?> getAllGroupIsActiveTrue(@RequestParam(defaultValue = BaseUrl.MIN_PAGE) int page,
                                     @RequestParam (defaultValue = BaseUrl.PAGE_SIZE) int size){
        ApiResponse apiResponse = groupService.getAllGroupIsActiveTrue(page, size);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }







}
