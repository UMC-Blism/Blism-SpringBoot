package com.example.blism.controller;

import com.example.blism.apiPayload.ApiResponse;
import com.example.blism.domain.Member;
import com.example.blism.domain.Reply;
import com.example.blism.dto.request.MemberRequestDTO;

import com.example.blism.dto.request.RepliesRequestDTO;
import com.example.blism.dto.response.MemberResponseDTO;
import com.example.blism.dto.response.RepliesResponseDTO;
import com.example.blism.service.RepliesServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/replies")
public class ReplyRestController {
    @Autowired
    private RepliesServiceImpl repliesService;



    @PostMapping("/")
    public ApiResponse<String> addreplies(@RequestBody RepliesRequestDTO.addreplyDTO request){
        repliesService.addreplies(request);
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
