package com.example.task2.controller;

import com.example.task2.payload.AddressDto;
import com.example.task2.payload.ApiResponse;
import com.example.task2.service.AddressService;
import com.example.task2.utilits.BaseUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public HttpEntity<?> addAddress(@Valid @RequestBody AddressDto addressDto){
        ApiResponse apiResponse = addressService.addAddress(addressDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @PutMapping("/{id}")
    public HttpEntity<?> editAddress(@PathVariable Integer id, @Valid @RequestBody AddressDto addressDto){
        ApiResponse apiResponse = addressService.editAddress(id, addressDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteAddress(@PathVariable Integer id){
        ApiResponse apiResponse = addressService.deleteAddress(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @GetMapping
    public HttpEntity<?> getAllAddressByDistrictId(@RequestParam (defaultValue = BaseUrl.MIN_PAGE) int page,
                                                   @RequestParam (defaultValue = BaseUrl.PAGE_SIZE) int size,
                                                   @RequestParam Integer districtId){
        ApiResponse apiResponse = addressService.getAllAddressByDistrict(districtId, page, size);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getByIdAddress(@PathVariable Integer id){
        ApiResponse apiResponse=addressService.getByIdAddress(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }











}
