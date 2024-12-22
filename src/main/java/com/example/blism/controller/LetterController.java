package com.example.blism.controller;

import com.example.blism.apiPayload.ApiResponse;
import com.example.blism.domain.Letter;
import com.example.blism.dto.request.CreateLetterRequestDTO;
import com.example.blism.dto.response.LetterResponseDTO;
import com.example.blism.repository.LetterRepository;
import com.example.blism.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final LetterRepository letterRepository;

    @PostMapping(path = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "편지 생성", description = "편지를 생성합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "편지 생성 성공",
            content = @Content(
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(
                            name = "successExample",
                            value = "{\n" +
                                    "  \"isSuccess\": true,\n" +
                                    "  \"code\": 200,\n" +
                                    "  \"message\": \"성공입니다.\",\n" +
                                    "  \"result\": [\n" +
                                    "  ]\n" +
                                    "}"
                    )
            )
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "편지 생성 실패",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "failureSenderExample",
                                            value = "{\n" +
                                                    "  \"isSuccess\": false,\n" +
                                                    "  \"code\": 401,\n" +
                                                    "  \"message\": \"보내는 사람이 없습니다.\",\n" +
                                                    "  \"result\": null\n" +
                                                    "}"
                                    ),
                                    @ExampleObject(
                                            name = "failureReceiverExample",
                                            value = "{\n" +
                                                    "  \"isSuccess\": false,\n" +
                                                    "  \"code\": 401,\n" +
                                                    "  \"message\": \"받는 사람이 없습니다.\",\n" +
                                                    "  \"result\": null\n" +
                                                    "}"
                                    ),
                                    @ExampleObject(
                                            name = "failureMailboxExample",
                                            value = "{\n" +
                                                    "  \"isSuccess\": false,\n" +
                                                    "  \"code\": 401,\n" +
                                                    "  \"message\": \"우체통이 존재하지 않습니다.\",\n" +
                                                    "  \"result\": null\n" +
                                                    "}"
                                    )
                            }
                    )
            )
    })

    public ResponseEntity<ApiResponse> createLetter(@RequestPart("image") MultipartFile image,
                                                    @RequestPart CreateLetterRequestDTO createLetterRequestDTO) {
        String logicStatus = letterService.createLetter(image, createLetterRequestDTO);
        if (logicStatus.equals("보내는 사람이 없습니다.")) {
            return ResponseEntity.ok().body(ApiResponse.onFailure(401, "보내는 사람이 없습니다.", null));
        }
        else if (logicStatus.equals("받는 사람이 없습니다.")){
            return ResponseEntity.ok().body(ApiResponse.onFailure(401, "받는 사람이 없습니다.", null));
        }
        else if (logicStatus.equals("우체통이 존재하지 않습니다.")){
            return ResponseEntity.ok().body(ApiResponse.onFailure(401, "우체통이 존재하지 않습니다.", null));
        }

        return ResponseEntity.ok().body(ApiResponse.onSuccess(null));
    }

    @GetMapping("/{letterId}")
    @Operation(summary = "편지 조회", description = "특정 ID의 편지를 조회합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "편지 조회 성공",
            content = @Content(
                    schema = @Schema(implementation = LetterResponseDTO.class),
                    examples = @ExampleObject(
                            name = "successExample",
                            value = "{\n" +
                                    "  \"isSuccess\": true,\n" +
                                    "  \"code\": 200,\n" +
                                    "  \"message\": \"성공입니다.\",\n" +
                                    "  \"result\": {\n" +
                                    "    \"letterId\": 1,\n" +
                                    "    \"senderId\": 1,\n" +
                                    "    \"receiverId\": 2,\n" +
                                    "    \"content\": \"Hello!\",\n" +
                                    "    \"photoUrl\": \"https://example.com/photo.jpg\",\n" +
                                    "    \"font\": 1,\n" +
                                    "    \"visibility\": 1,\n" +
                                    "    \"createdAt\": \"2024-12-22T04:35:08.367236\"\n" +
                                    "  }\n" +
                                    "}"
                    )
            )
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "401",
            description = "편지 조회 실패",
            content = @Content(
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(
                            name = "failureExample",
                            value = "{\n" +
                                    "  \"isSuccess\": false,\n" +
                                    "  \"code\": 401,\n" +
                                    "  \"message\": \"편지가 존재하지 않습니다.\",\n" +
                                    "  \"result\": null\n" +
                                    "}"
                    )
            )
    )
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
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "보낸 편지 조회 성공",
            content = @Content(
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(
                            name = "successExample",
                            value = "{\n" +
                                    "  \"isSuccess\": true,\n" +
                                    "  \"code\": 200,\n" +
                                    "  \"message\": \"성공입니다.\",\n" +
                                    "  \"result\": [\n" +
                                    "    {\n" +
                                    "      \"letterId\": 1,\n" +
                                    "      \"senderId\": 1,\n" +
                                    "      \"receiverId\": 2,\n" +
                                    "      \"content\": \"Hello!\",\n" +
                                    "      \"photoUrl\": \"https://example.com/photo.jpg\",\n" +
                                    "      \"font\": 1,\n" +
                                    "      \"visibility\": 1,\n" +
                                    "      \"createdAt\": \"2024-12-22T04:35:08.367236\"\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"letterId\": 2,\n" +
                                    "      \"senderId\": 1,\n" +
                                    "      \"receiverId\": 3,\n" +
                                    "      \"content\": \"How are you?\",\n" +
                                    "      \"photoUrl\": \"https://example.com/photo2.jpg\",\n" +
                                    "      \"font\": 2,\n" +
                                    "      \"visibility\": 1,\n" +
                                    "      \"createdAt\": \"2024-12-22T05:10:15.123456\"\n" +
                                    "    }\n" +
                                    "  ]\n" +
                                    "}"
                    )
            )
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "401",
            description = "보낸 편지 조회 실패",
            content = @Content(
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(
                            name = "failureExample",
                            value = "{\n" +
                                    "  \"isSuccess\": false,\n" +
                                    "  \"code\": 401,\n" +
                                    "  \"message\": \"사용자를 찾을 수 없습니다.\",\n" +
                                    "  \"result\": null\n" +
                                    "}"
                    )
            )
    )
    public ResponseEntity<ApiResponse> getSentLetters(@PathVariable Long userId) {

        boolean memberExist = memberRepository.existsById(userId);

        if (memberExist) {
            List<LetterResponseDTO> letters = letterService.getSentLetters(userId);
            return ResponseEntity.ok().body(ApiResponse.onSuccess(letters));
        }

        return ResponseEntity.ok().body(ApiResponse.onFailure(401, "사용자를 찾을 수 없습니다.", null));
    }

    @GetMapping("/{userId}/received")
    @Operation(summary = "받은 편지 목록 조회", description = "특정 사용자가 받은 모든 편지를 조회합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "받은 편지 조회 성공",
            content = @Content(
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(
                            name = "successExample",
                            value = "{\n" +
                                    "  \"isSuccess\": true,\n" +
                                    "  \"code\": 200,\n" +
                                    "  \"message\": \"성공입니다.\",\n" +
                                    "  \"result\": [\n" +
                                    "    {\n" +
                                    "      \"letterId\": 1,\n" +
                                    "      \"senderId\": 1,\n" +
                                    "      \"receiverId\": 2,\n" +
                                    "      \"content\": \"Hello!\",\n" +
                                    "      \"photoUrl\": \"https://example.com/photo.jpg\",\n" +
                                    "      \"font\": 1,\n" +
                                    "      \"visibility\": 1,\n" +
                                    "      \"createdAt\": \"2024-12-22T04:35:08.367236\"\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"letterId\": 2,\n" +
                                    "      \"senderId\": 1,\n" +
                                    "      \"receiverId\": 3,\n" +
                                    "      \"content\": \"How are you?\",\n" +
                                    "      \"photoUrl\": \"https://example.com/photo2.jpg\",\n" +
                                    "      \"font\": 2,\n" +
                                    "      \"visibility\": 1,\n" +
                                    "      \"createdAt\": \"2024-12-22T05:10:15.123456\"\n" +
                                    "    }\n" +
                                    "  ]\n" +
                                    "}"
                    )
            )
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "401",
            description = "받은 편지 조회 실패",
            content = @Content(
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(
                            name = "failureExample",
                            value = "{\n" +
                                    "  \"isSuccess\": false,\n" +
                                    "  \"code\": 401,\n" +
                                    "  \"message\": \"사용자를 찾을 수 없습니다.\",\n" +
                                    "  \"result\": null\n" +
                                    "}"
                    )
            )
    )
    public ResponseEntity<ApiResponse> getReceivedLetters(@PathVariable Long userId) {

        boolean memberExist = memberRepository.existsById(userId);

        if (memberExist) {
            List<LetterResponseDTO> letters = letterService.getReceivedLetters(userId);
            return ResponseEntity.ok().body(ApiResponse.onSuccess(letters));
        }

        return ResponseEntity.ok().body(ApiResponse.onFailure(401, "사용자를 찾을 수 없습니다.", null));
    }

    @PutMapping(value = "/{letterId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "편지 수정", description = "편지를 수정합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "편지 수정 성공",
            content = @Content(
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(
                            name = "successExample",
                            value = "{\n" +
                                    "  \"isSuccess\": true,\n" +
                                    "  \"code\": 200,\n" +
                                    "  \"message\": \"성공입니다.\",\n" +
                                    "  \"result\": [\n" +
                                    "  ]\n" +
                                    "}"
                    )
            )
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "401",
            description = " 편지 수정 실패",
            content = @Content(
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(
                            name = "failureExample",
                            value = "{\n" +
                                    "  \"isSuccess\": false,\n" +
                                    "  \"code\": 401,\n" +
                                    "  \"message\": \"편지를 찾을 수 없습니다.\",\n" +
                                    "  \"result\": null\n" +
                                    "}"
                    )
            )
    )
    public ResponseEntity<ApiResponse> updateLetter(@RequestPart("image") MultipartFile image,
                                                    @PathVariable Long letterId,
                                                    @RequestPart CreateLetterRequestDTO createLetterRequestDTO) {
        String photoUrl = null;

        boolean letterExist = letterRepository.existsById(letterId);

        if (letterExist) {
            Letter letter = letterService.getLetter(letterId);

            if (!image.isEmpty()) {
                photoUrl = s3Service.upload(image);
            }

            letter = letter.update(photoUrl, createLetterRequestDTO);
            letterService.updateLetter(letter);

            return ResponseEntity.ok().body(ApiResponse.onSuccess(null));
        }

        return ResponseEntity.ok().body(ApiResponse.onFailure(401, "편지를 찾을 수 없습니다.", null));

    }
}
