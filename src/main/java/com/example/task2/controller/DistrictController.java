package com.example.task2.controller;


import com.example.task2.payload.ApiResponse;
import com.example.task2.payload.DistrictDto;
import com.example.task2.service.DistrictService;
import com.example.task2.utilits.BaseUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/district")
@RequiredArgsConstructor
public class DistrictController {

    private final DistrictService districtService;

    @PostMapping
    public HttpEntity<?> addDistrict(@Valid @RequestBody DistrictDto districtDto){
        ApiResponse apiResponse = districtService.addDistrict(districtDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("{id}")
    public HttpEntity<?> editDistrict(@PathVariable Integer id, @Valid @RequestBody DistrictDto districtDto){
        ApiResponse apiResponse=districtService.editDistrict(id, districtDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteDistrict(@PathVariable Integer id){
        ApiResponse apiResponse=districtService.deleteDistrict(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @PatchMapping("/{id}")
    public HttpEntity<?> enabledOrDisabled(@PathVariable Integer id, @RequestParam boolean active){
        ApiResponse apiResponse=districtService.enabledOrDisabled(id, active);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> getAllDistrict (@RequestParam (defaultValue = BaseUrl.MIN_PAGE) int page,
                                         @RequestParam (defaultValue = BaseUrl.PAGE_SIZE) int size){
        ApiResponse apiResponse=districtService.getAllDistrict(page, size);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @GetMapping("/{id}")
    public HttpEntity<?> getByIdDistrict(@PathVariable Integer id){
        ApiResponse apiResponse=districtService.getByIdDistrict(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/getByIdDistrictIsActive/{id}")
    public HttpEntity<?> getByIdDistrictIsActive (@PathVariable Integer id){
        ApiResponse apiResponse=districtService.getByIdDistrictIsActive(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/getAllDistrictIsActive")
    public HttpEntity<?> getAllDistrictIsActive(){
        ApiResponse apiResponse=districtService.getAllDistrictIsActive();
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/getAllDistrictByRegionId/{regionId}")
    public HttpEntity<?> getAllDistrictByRegionId(@PathVariable Integer regionId){
        ApiResponse apiResponse=districtService.getAllDistrictByRegionId(regionId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @GetMapping("/getAllDistrictByRegionIdIsActive/{regionId}")
    public HttpEntity<?> getAllDistrictByRegionIdIsActive(@PathVariable Integer regionId){
        ApiResponse apiResponse=districtService.getAllDistrictByRegionIdIsActive(regionId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}
