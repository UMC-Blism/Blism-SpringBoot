package com.example.blism.controller;

import com.example.blism.apiPayload.ApiResponse;
import com.example.blism.domain.Member;
import com.example.blism.domain.Reply;
import com.example.blism.dto.request.MemberRequestDTO;

import com.example.blism.dto.request.RepliesRequestDTO;
import com.example.blism.service.RepliesServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/replies")
public class ReplyRestController {
    private RepliesServiceImpl repliesService;

    @PostMapping("/")
    public ApiResponse<String> addreplies(@RequestBody RepliesRequestDTO.addreplyDTO request){
        Reply reply = repliesService.addreplies(request);
        return ApiResponse.onSuccess("편지 작성 완료");

    }

}
