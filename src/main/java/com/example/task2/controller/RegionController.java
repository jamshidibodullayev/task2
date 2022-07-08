package com.example.task2.controller;

import com.example.task2.entity.Region;
import com.example.task2.payload.ApiResponse;
import com.example.task2.service.RegionService;
import com.example.task2.utilits.BaseUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/region")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @PostMapping
    public HttpEntity<?> addRegion(@Valid @RequestBody Region region) {
        ApiResponse apiResponse = regionService.addRegion(region);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editRegion(@PathVariable Integer id, @Valid @RequestBody Region region){
        ApiResponse apiResponse=regionService.editRegion(id, region);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteRegion(@PathVariable Integer id){
        ApiResponse apiResponse=regionService.deleteRegion(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PatchMapping("/{id}")
    public HttpEntity<?> enabledOrDisabled(@PathVariable Integer id, @RequestParam boolean active){
        ApiResponse apiResponse=regionService.enabledOrDisabled(id, active);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> getAllRegion(@RequestParam (defaultValue = BaseUrl.MIN_PAGE) int  page,
                                      @RequestParam (defaultValue = BaseUrl.PAGE_SIZE) int size){
        ApiResponse apiResponse=regionService.getAllRegionForAdmin(page, size);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getByIdRegion(@PathVariable Integer id){
        ApiResponse apiResponse=regionService.getByIdRegion(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/isActive")
    public HttpEntity<?> getAllRegionIsActive(@RequestParam (defaultValue = BaseUrl.MIN_PAGE) int  page,
                                              @RequestParam (defaultValue = BaseUrl.PAGE_SIZE) int size){
        ApiResponse apiResponse=regionService.getAllRegionIsActive(page, size);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("isActive/{id}")
    public HttpEntity<?> getAllRegionIsActive(@PathVariable Integer id){
        ApiResponse apiResponse=regionService.getByIdRegionIsActive(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


}
