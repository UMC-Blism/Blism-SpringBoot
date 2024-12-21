package com.example.blism.controller;

import com.example.blism.apiPayload.ApiResponse;
import com.example.blism.domain.Letter;
import com.example.blism.dto.request.CreateLetterRequestDTO;
import com.example.blism.dto.response.LetterResponseDTO;
import com.example.blism.service.LetterService;
import com.example.blism.service.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/letters")
public class LetterController {

    private final LetterService letterService;
    private final S3Service s3Service;

    @PostMapping(path = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse> createLetter(@RequestPart("image") MultipartFile image,
                                                    @RequestPart CreateLetterRequestDTO createLetterRequestDTO){
        boolean logicStatus = letterService.createLetter(image, createLetterRequestDTO);
        if(logicStatus){
            return ResponseEntity.ok().body(ApiResponse.onSuccess(null));
        }
        else{
            return ResponseEntity.ok().body(ApiResponse.onFailure(401, "로직 수행 간 문제 발생", null));
        }
    }

    @GetMapping("/{letterId}")
    public ResponseEntity<ApiResponse> getLetters(@PathVariable Long letterId){
        Letter letter = letterService.getLetter(letterId);

        if(letter == null){
            return ResponseEntity.ok().body(ApiResponse.onFailure(401, "편지가 존재하지 않습니다.", null));
        }

        return ResponseEntity.ok().body(ApiResponse.onSuccess(letter));


    }

    @GetMapping("/{userId}/sent")
    public ResponseEntity<ApiResponse> getSentLetters(@PathVariable Long userId){
        List<LetterResponseDTO> letters = letterService.getSentLetters(userId);

        return ResponseEntity.ok().body(ApiResponse.onSuccess(letters));
    }

    @GetMapping("/{userId}/received")
    public ResponseEntity<ApiResponse> getReceivedLetters(@PathVariable Long userId){
        List<LetterResponseDTO> letters = letterService.getReceivedLetters(userId);

        return ResponseEntity.ok().body(ApiResponse.onSuccess(letters));
    }

    @PutMapping(path = "/{letterId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse> updateLetter(@RequestPart("image") MultipartFile image,
                                                    @PathVariable Long letterId,
                                                    @RequestBody CreateLetterRequestDTO createLetterRequestDTO){
        String photoUrl = null;


        Letter letter = letterService.getLetter(letterId);

        if(!image.isEmpty()){
            photoUrl = s3Service.upload(image);
        }

        letter.update(photoUrl, createLetterRequestDTO);


        return ResponseEntity.ok().body(ApiResponse.onSuccess(null));
    }

}
