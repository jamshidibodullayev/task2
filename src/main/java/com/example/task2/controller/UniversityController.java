package com.example.task2.controller;


import com.example.task2.payload.ApiResponse;
import com.example.task2.payload.UniversityDto;
import com.example.task2.service.UniversityService;
import com.example.task2.utilits.BaseUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/university")
@RequiredArgsConstructor
public class UniversityController {

    private final UniversityService universityService;

    @PostMapping
    public HttpEntity<?> addUniversity(@Valid @RequestBody UniversityDto universityDto){
        ApiResponse apiResponse=universityService.addUniversity(universityDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @PutMapping("/{id}")
    public HttpEntity<?> editUniversity (@PathVariable Integer id, @Valid @RequestBody UniversityDto universityDto){
        ApiResponse apiResponse = universityService.editUniversity(id, universityDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PatchMapping("/{id}")
    public HttpEntity<?> enabledOrDisabled(@PathVariable Integer id, @RequestParam boolean active){
        ApiResponse apiResponse = universityService.enabledOrDisabled(id, active);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteUniversity(@PathVariable Integer id){
        ApiResponse apiResponse = universityService.deleteUniversity(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> getAllUniversity(@RequestParam (defaultValue = BaseUrl.MIN_PAGE) int page,
                                          @RequestParam (defaultValue = BaseUrl.PAGE_SIZE) int size){
        ApiResponse apiResponse = universityService.getAllUniversity(page, size);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getByIdUniversity(@PathVariable Integer id){
        ApiResponse apiResponse = universityService.getByIdUniversity(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @GetMapping("/getAllUniversityIsActive")
    public HttpEntity<?> getAllUniversityIsActive(@RequestParam (defaultValue = BaseUrl.MIN_PAGE) int page,
                                          @RequestParam (defaultValue = BaseUrl.PAGE_SIZE) int size){
        ApiResponse apiResponse = universityService.getAllUniversityIsActiveTrue(page, size);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/getByIdUniversityIsActive/{id}")
    public HttpEntity<?> getByIdUniversityIsActive(@PathVariable Integer id){
        ApiResponse apiResponse = universityService.getByIdUniversityIsActiveTrue(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/getAllUniversityByRegionId/{regionId}")
    public HttpEntity<?> getAllUniversityByRegionId(@PathVariable Integer regionId){
        ApiResponse apiResponse = universityService.getAllUniversityByRegionId(regionId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/getAllUniversityByDistrictId/{districtId}")
    public HttpEntity<?> getAllUniversityByDistrictId(@PathVariable Integer districtId){
        ApiResponse apiResponse = universityService.getAllUniversityByDistrictId(districtId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }




}
