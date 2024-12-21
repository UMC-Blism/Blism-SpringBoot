package com.example.blism.controller;

import com.example.blism.apiPayload.ApiResponse;
import com.example.blism.domain.Letter;
import com.example.blism.dto.request.CreateLetterRequestDTO;
import com.example.blism.dto.response.LetterResponseDTO;
import com.example.blism.service.LetterService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/letters")
public class LetterController {

    private final LetterService letterService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> createLetter(@RequestBody CreateLetterRequestDTO createLetterRequestDTO){
        boolean logicStatus = letterService.createLetter(createLetterRequestDTO);
        if(logicStatus){
            return ResponseEntity.ok().body(ApiResponse.onSuccess(null));
        }
        else{
            return ResponseEntity.ok().body(ApiResponse.onFailure( "LETTER401", "로직 수행 간 문제 발생", null));
        }
    }

    @GetMapping("/{letterId}")
    public ResponseEntity<ApiResponse> getLetters(@PathVariable Long letterId){
        Letter letter = letterService.getLetter(letterId);

        return ResponseEntity.ok().body(ApiResponse.onSuccess(letter));

    }

    @GetMapping("/{userId}/sent")
    public ResponseEntity<ApiResponse> getSentLetters(@PathVariable Long userId){
        List<LetterResponseDTO> letters = letterService.getLetters(userId);

        return ResponseEntity.ok().body(ApiResponse.onSuccess(letters));
    }

    @GetMapping("/{userId}/received")
    public ResponseEntity<ApiResponse> getReceivedLetters(@PathVariable Long userId){
        List<LetterResponseDTO> letters = letterService.getLetters(userId);

        return ResponseEntity.ok().body(ApiResponse.onSuccess(letters));
    }

    @PutMapping("/{letterId}")
    public ResponseEntity<ApiResponse> updateLetter(@PathVariable Long letterId, @RequestBody CreateLetterRequestDTO createLetterRequestDTO){
        Letter letter = letterService.getLetter(letterId);
        letter.update(createLetterRequestDTO);

        return ResponseEntity.ok().body(ApiResponse.onSuccess(null));
    }

}
