package com.example.blism.controller;

import com.example.blism.apiPayload.ApiResponse;
import com.example.blism.domain.Member;
import com.example.blism.domain.Reply;
import com.example.blism.dto.request.CreateLetterRequestDTO;
import com.example.blism.dto.request.MemberRequestDTO;

import com.example.blism.dto.request.RepliesRequestDTO;
import com.example.blism.dto.response.MemberResponseDTO;
import com.example.blism.dto.response.RepliesResponseDTO;
import com.example.blism.service.RepliesServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/replies")
public class ReplyRestController {
    @Autowired
    private RepliesServiceImpl repliesService;
    // ---------------------------- 여기 부터


    @PostMapping(path = "/" , consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "답장 생성", description = "이미지와 내용을 포함하여 새로운 답장을 생성합니다.")
    public ApiResponse<String> addreplies(@RequestPart RepliesRequestDTO.addreplyDTO request, @RequestPart("image") MultipartFile image){
        repliesService.addreplies(image, request);
        return ApiResponse.onSuccess("답장 작성 완료");
    }

    @GetMapping("/{memberid}/sent")
    @Operation(summary = "멤버가 보낸 답장 전체 조회",description = "특정 멤버가 보낸 답장을 전체 조회하는 API 입니다")
    public ApiResponse<List<RepliesResponseDTO.allsentrepliesDTO>> getAllReplies(
            @RequestParam(name = "memberid") Long sender_id
    ){
        return ApiResponse.onSuccess(repliesService.getAllSentReplies(sender_id));
    }

    @GetMapping("/{memberid}/received")
    @Operation(summary = "유저가 받은 답장 전체 조회",description = "특정 멤버가 받은 답장을 전체 조회하는 API 입니다")
    public ApiResponse<List<RepliesResponseDTO.allreceivedrepliesDTO>> getAllReceivedReplies(
            @RequestParam(name = "memberid") Long member_id
    ){
        return ApiResponse.onSuccess(repliesService.getAllReceivedReplies(member_id));
    }

}
