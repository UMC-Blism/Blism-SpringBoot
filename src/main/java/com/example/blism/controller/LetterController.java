package com.example.blism.controller;

import com.example.blism.apiPayload.ApiResponse;
import com.example.blism.dto.request.CreateLetterRequestDTO;
import com.example.blism.service.LetterService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/letters/")
public class LetterController {

    private final LetterService letterService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> createLetter(@RequestBody CreateLetterRequestDTO createLetterRequestDTO){
        boolean logicStatus = letterService.createLetter(createLetterRequestDTO);
        if(logicStatus){
            return ResponseEntity.ok().body(ApiResponse.onSuccess(null));
        }
        else{
            return ResponseEntity.badRequest().body(ApiResponse.onFailure( "LETTER401", "로직 수행 간 문제 발생", null));
        }
    }

}
