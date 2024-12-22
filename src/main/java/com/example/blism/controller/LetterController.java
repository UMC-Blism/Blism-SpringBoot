package com.example.blism.controller;

import com.example.blism.apiPayload.ApiResponse;
import com.example.blism.domain.Letter;
import com.example.blism.dto.request.CreateLetterRequestDTO;
import com.example.blism.dto.response.LetterResponseDTO;
import com.example.blism.service.LetterService;
import com.example.blism.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/letters")
@Tag(name = "Letter Controller", description = "편지 관련 API")
public class LetterController {

    private final LetterService letterService;
    private final S3Service s3Service;

    @PostMapping(path = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "편지 생성", description = "이미지와 내용을 포함하여 새로운 편지를 생성합니다.",
            parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "image", description = "이미지 파일", required = true, content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)),
                    @io.swagger.v3.oas.annotations.Parameter(name = "createLetterRequestDTO", description = "편지 생성 요청 데이터", required = true,
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CreateLetterRequestDTO.class),
                                    examples = @ExampleObject(name = "createLetterRequestExample",
                                            value = "{\"senderId\":1,\"receiverId\":2,\"mailboxId\":3,\"doorDesign\":1,\"colorDesign\":2,\"decorationDesign\":3,\"content\":\"Hello World!\",\"font\":1,\"visibility\":1}")))
            })
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "편지 생성 성공",
            content = @Content(schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(name = "successExample", value = "{\"status\":\"success\",\"data\":null}")))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "편지 생성 실패",
            content = @Content(schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(name = "failureExample", value = "{\"status\":\"failure\",\"message\":\"로직 수행 간 문제 발생\"}")))
    public ResponseEntity<ApiResponse> createLetter(@RequestPart("image") MultipartFile image,
                                                    @RequestPart CreateLetterRequestDTO createLetterRequestDTO) {
        boolean logicStatus = letterService.createLetter(image, createLetterRequestDTO);
        if (logicStatus) {
            return ResponseEntity.ok().body(ApiResponse.onSuccess(null));
        } else {
            return ResponseEntity.ok().body(ApiResponse.onFailure(401, "로직 수행 간 문제 발생", null));
        }
    }

    @GetMapping("/{letterId}")
    @Operation(summary = "편지 조회", description = "특정 ID의 편지를 조회합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "편지 조회 성공",
            content = @Content(schema = @Schema(implementation = LetterResponseDTO.class),
                    examples = @ExampleObject(name = "successExample", value = "{\"letterId\":1,\"senderId\":1,\"receiverId\":2,\"content\":\"Hello!\",\"photoUrl\":\"https://example.com/photo.jpg\",\"font\":1,\"visibility\":1}")))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "편지 조회 실패",
            content = @Content(schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(name = "failureExample", value = "{\"status\":\"failure\",\"message\":\"편지가 존재하지 않습니다.\"}")))
    public ResponseEntity<ApiResponse> getLetters(@PathVariable Long letterId) {

        Letter letter = letterService.getLetter(letterId);

        if (letter == null) {
            return ResponseEntity.ok().body(ApiResponse.onFailure(401, "편지가 존재하지 않습니다.", null));
        }

        LetterResponseDTO letterResponseDTO = LetterResponseDTO.builder()
                .letterId(letter.getId())
                .content(letter.getContent())
                .photoUrl(letter.getPhotoUrl())
                .font(letter.getFont())
                .senderId(letter.getSender().getId())
                .senderNickname(letter.getSender().getNickname())
                .receiverId(letter.getReceiver().getId())
                .receiverNickname(letter.getReceiver().getNickname())
                .visibility(letter.getVisibility())
                .build();

        return ResponseEntity.ok().body(ApiResponse.onSuccess(letterResponseDTO));
    }

    @GetMapping("/{userId}/sent")
    @Operation(summary = "보낸 편지 목록 조회", description = "특정 사용자가 보낸 모든 편지를 조회합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "보낸 편지 조회 성공",
            content = @Content(schema = @Schema(implementation = List.class),
                    examples = @ExampleObject(name = "successExample", value = "[{\"letterId\":1,\"senderId\":1,\"receiverId\":2,\"content\":\"Hello!\",\"photoUrl\":\"https://example.com/photo.jpg\",\"font\":1,\"visibility\":1}]")))
    public ResponseEntity<ApiResponse> getSentLetters(@PathVariable Long userId) {
        List<LetterResponseDTO> letters = letterService.getSentLetters(userId);

        return ResponseEntity.ok().body(ApiResponse.onSuccess(letters));
    }

    @GetMapping("/{userId}/received")
    @Operation(summary = "받은 편지 목록 조회", description = "특정 사용자가 받은 모든 편지를 조회합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "받은 편지 조회 성공",
            content = @Content(schema = @Schema(implementation = List.class),
                    examples = @ExampleObject(name = "successExample", value = "[{\"letterId\":1,\"senderId\":1,\"receiverId\":2,\"content\":\"Hello!\",\"photoUrl\":\"https://example.com/photo.jpg\",\"font\":1,\"visibility\":1}]")))
    public ResponseEntity<ApiResponse> getReceivedLetters(@PathVariable Long userId) {
        List<LetterResponseDTO> letters = letterService.getReceivedLetters(userId);

        return ResponseEntity.ok().body(ApiResponse.onSuccess(letters));
    }

    @PutMapping("/{letterId}")
    @Operation(summary = "편지 수정", description = "특정 ID의 편지를 수정합니다.",
            parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "image", description = "이미지 파일", required = true, content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)),
                    @io.swagger.v3.oas.annotations.Parameter(name = "createLetterRequestDTO", description = "편지 수정 요청 데이터", required = true,
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CreateLetterRequestDTO.class),
                                    examples = @ExampleObject(name = "updateLetterRequestExample",
                                            value = "{\"senderId\":1,\"receiverId\":2,\"mailboxId\":3,\"doorDesign\":2,\"colorDesign\":1,\"decorationDesign\":3,\"content\":\"Updated Content!\",\"font\":2,\"visibility\":0}")))
            })

    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "편지 수정 성공",
            content = @Content(schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(name = "successExample", value = "{\"status\":\"success\",\"data\":null}")))
    public ResponseEntity<ApiResponse> updateLetter(@RequestPart("image") MultipartFile image,
                                                    @PathVariable Long letterId,
                                                    @RequestPart CreateLetterRequestDTO createLetterRequestDTO) {
        String photoUrl = null;

        Letter letter = letterService.getLetter(letterId);

        if (!image.isEmpty()) {
            photoUrl = s3Service.upload(image);
        }

        letter = letter.update(photoUrl, createLetterRequestDTO);

        letterService.updateLetter(letter);

        return ResponseEntity.ok().body(ApiResponse.onSuccess(null));
    }
}
