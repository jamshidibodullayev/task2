package com.example.task2.controller;


import com.example.task2.payload.ApiResponse;
import com.example.task2.payload.FacultyDto;
import com.example.task2.service.FacultyService;
import com.example.task2.utilits.BaseUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/faculty")
@RequiredArgsConstructor
public class FacultyController {

    private final FacultyService facultyService;


    @PostMapping
    public HttpEntity<?> addFaculty(@Valid @RequestBody FacultyDto facultyDto){
        ApiResponse apiResponse = facultyService.addFaculty(facultyDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editFaculty(@PathVariable Integer id, @Valid @RequestBody FacultyDto facultyDto){
        ApiResponse apiResponse = facultyService.editFaculty(id, facultyDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PatchMapping("/{id}")
    public HttpEntity<?> enabledOrDisabledFaculty(@PathVariable Integer id, @RequestParam boolean active){
        ApiResponse apiResponse = facultyService.enabledOrDisabledFaculty(id, active);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteFaculty(@PathVariable Integer id){
        ApiResponse apiResponse = facultyService.deleteFaculty(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> gelAllFaculty(@RequestParam (defaultValue = BaseUrl.MIN_PAGE) int page,
                                       @RequestParam (defaultValue = BaseUrl.PAGE_SIZE) int size){
        ApiResponse apiResponse = facultyService.getAllFaculty(page, size);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getByIdFaculty(@PathVariable Integer id){
        ApiResponse apiResponse = facultyService.getByIdFaculty(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }



    @GetMapping("/getAllFacultyIsActiveTrue")
    public HttpEntity<?> getAllFacultyIsActiveTrue(@RequestParam (defaultValue = BaseUrl.MIN_PAGE) int page,
                                                   @RequestParam (defaultValue = BaseUrl.PAGE_SIZE) int size){
        ApiResponse apiResponse = facultyService.getAllFacultyIsActiveTrue(page, size);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/getByIdFacultyIsActiveTrue/{id}")
    public HttpEntity<?> getByIdFacultyIsActiveTrue(@PathVariable Integer id){
        ApiResponse apiResponse = facultyService.getByIdFacultyIsActiveTrue(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("getAllFacultyByUniversityId/{universityId}")
    public HttpEntity<?> getAllFacultyByUniversityId(@PathVariable Integer universityId){
        ApiResponse apiResponse = facultyService.getAllFacultyByUniversityId(universityId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("getAllFacultyIsActiveTrueByUniversityId/{universityId}")
    public HttpEntity<?> getAllFacultyIsActiveTrueByUniversityId(@PathVariable Integer universityId ){
        ApiResponse apiResponse = facultyService.getAllFacultyIsActiveTrueByUniversityId(universityId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


}
