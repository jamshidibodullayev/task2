package com.example.task2.controller;


import com.example.task2.payload.ApiResponse;
import com.example.task2.payload.JournalDto;
import com.example.task2.service.JournalService;
import com.example.task2.utilits.BaseUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/journal")
@RequiredArgsConstructor
public class JournalController {

    private final JournalService journalService;

    @PostMapping
    public HttpEntity<?> addJournal(@Valid @RequestBody JournalDto journalDto){
        ApiResponse apiResponse = journalService.addJournal(journalDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editJournal(@PathVariable Integer id, @Valid @RequestBody JournalDto journalDto){
        ApiResponse apiResponse = journalService.editJournal(id, journalDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteJournal(@PathVariable Integer id){
        ApiResponse apiResponse = journalService.deleteJournal(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/journalAddSubject/{id}")
    public HttpEntity<?> journalAddSubject(@PathVariable Integer id,
                                           @RequestParam  Integer subjectId){
        ApiResponse apiResponse = journalService.journalAddSubject(id, subjectId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/journalDeleteSubject/{id}")
    public HttpEntity<?> journalDeleteSubject(@PathVariable Integer id,
                                           @RequestParam  Integer subjectId){
        ApiResponse apiResponse = journalService.journalDeleteSubject(id, subjectId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> getByIdJournal(@RequestParam (defaultValue = BaseUrl.MIN_PAGE) int page,
                                        @RequestParam (defaultValue = BaseUrl.PAGE_SIZE) int size){
        ApiResponse apiResponse = journalService.getAllJournal(page, size);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getByIdJournal(@PathVariable Integer id){
        ApiResponse apiResponse = journalService.getByIdJournal(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/getByJournalByGroupId/{groupId}")
    public HttpEntity<?> getByJournalByGroupId(@PathVariable Integer groupId){
        ApiResponse apiResponse = journalService.getByJournalByGroupId(groupId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }





}
