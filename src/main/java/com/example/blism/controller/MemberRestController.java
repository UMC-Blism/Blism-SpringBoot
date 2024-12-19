package com.example.blism.controller;

import com.example.blism.apiPayload.ApiResponse;
import com.example.blism.domain.Member;
import com.example.blism.service.MemberServiceImpl;
import com.example.blism.dto.requestdto.signupDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberRestController {

    private final MemberServiceImpl memberService;

    @PostMapping("/signup")
    public ApiResponse<String> join(@RequestBody signupDTO request){
        Member member = memberService.joinMember(request);
        return ApiResponse.onSuccess("회원가입 완료");
    }

    @GetMapping("/{nickname}")
    @Operation(summary = "멤버의 닉네임 중복체크",description = "특정 멤버의 닉네임 중복 유무를 조회하는 API 입니다 query String 멤버의 닉네임을 주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<String> checkduplicatenickname(
            @RequestParam(name = "nickname") String nickname

    ){
        return ApiResponse.onSuccess(memberService.checkduplicatenickname(nickname));
    }

    @PatchMapping("/change")
    public ApiResponse<String> changenickname( String original_nickname, String new_nickname){
        Member member = memberService.changenickname(original_nickname,new_nickname);
        return ApiResponse.onSuccess("변경완료");
    }

}