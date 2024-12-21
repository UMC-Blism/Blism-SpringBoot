//package com.example.blism.controller;
//
//import com.example.blism.apiPayload.ApiResponse;
//import com.example.blism.domain.Member;
//import com.example.blism.domain.Reply;
//import com.example.blism.dto.request.MemberRequestDTO;
//
//import com.example.blism.dto.request.RepliesRequestDTO;
//import com.example.blism.dto.response.MemberResponseDTO;
//import com.example.blism.dto.response.RepliesResponseDTO;
//import com.example.blism.service.RepliesServiceImpl;
//import io.swagger.v3.oas.annotations.Operation;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/replies")
//public class ReplyRestController {
//    private RepliesServiceImpl repliesService;
//
//    @PostMapping("/")
//    public ApiResponse<String> addreplies(@RequestBody RepliesRequestDTO.addreplyDTO request){
//        Reply reply = repliesService.addreplies(request);
//        return ApiResponse.onSuccess("편지 작성 완료");
//    }
//
//    @GetMapping("/sent")
//    @Operation(summary = "유저의 답장 전체 조회",description = "특정 멤버의 답장을 전체 조회하는 API 입니다")
//    public ApiResponse<List<RepliesResponseDTO.allrepliesDTO>> getAllReplies(
//            @RequestParam(name = "nickname") Long sender_id
//    ){
//        return ApiResponse.onSuccess(repliesService.getAllReplies(sender_id));
//    }
//
//
//}
