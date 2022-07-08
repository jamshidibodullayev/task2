package com.example.task2.controller;


import com.example.task2.payload.ApiResponse;
import com.example.task2.payload.MarkDto;
import com.example.task2.service.MarkService;
import com.example.task2.utilits.BaseUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/mark")
@RequiredArgsConstructor
public class MarkController {

    private final MarkService markService;

    @PostMapping
    public HttpEntity<?> addMark(@Valid @RequestBody MarkDto markDto){
        ApiResponse apiResponse = markService.addMark(markDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editMark(@PathVariable Integer id, @Valid @RequestBody MarkDto markDto){
        ApiResponse apiResponse = markService.editMark(id, markDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteMark(@PathVariable Integer id){
        ApiResponse apiResponse = markService.deleteMark(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> getAllMark(@RequestParam (defaultValue = BaseUrl.MIN_PAGE) int page,
                                    @RequestParam (defaultValue = BaseUrl.PAGE_SIZE) int size){
        ApiResponse apiResponse = markService.getAllMark(page, size);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getByIdMark(@PathVariable Integer id){
        ApiResponse apiResponse = markService.getByIdMark(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


}
