package com.example.blism.controller;

import com.example.blism.apiPayload.ApiResponse;
import com.example.blism.domain.Member;
import com.example.blism.dto.request.MemberRequestDTO;
;
import com.example.blism.dto.response.MemberResponseDTO;
import com.example.blism.service.MemberServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberRestController {

    private final MemberServiceImpl memberService;

    @PostMapping("/signup")
    public ApiResponse<String> join(@RequestBody MemberRequestDTO.signupDTO request){
        Member member = memberService.joinMember(request);

        return ApiResponse.onSuccess("회원가입 완료");
    }

    @GetMapping("/{nickname}")
    @Operation(summary = "멤버의 닉네임 중복체크",description = "특정 멤버의 닉네임 중복 유무를 조회하는 API 입니다 query String 멤버의 닉네임을 주세요")
    public ApiResponse<MemberResponseDTO.CheckMemberDTO> checkduplicatenickname(
            @RequestParam(name = "nickname") String request
    ){

        return ApiResponse.onSuccess(memberService.checkduplicatenickname(request));
    }

    @PatchMapping("/change")
    public ApiResponse<String> changenickname( String original_nickname, String new_nickname){
        Member member = memberService.changenickname(original_nickname,new_nickname);
        return ApiResponse.onSuccess("변경완료");
    }

    // -- 멤버 리스트 api --
    @GetMapping("/search")
    @Operation(summary = "멤버의 검색", description = "특정 멤버의 닉네임을 입력 받아서 닉네임이 존재하는 멤버 닉네임과 id 리스트를 반환합니다")
    public ApiResponse<List<MemberResponseDTO.SearchMemberDTO>> searchNickname(
           String nickname) {
        MemberRequestDTO.searchDTO request = new MemberRequestDTO.searchDTO(nickname);
        return ApiResponse.onSuccess(memberService.findNicknameList(request));
    }

    // -- 멤버 닉네임과 체크코드를 입력받고 메일박스 id 반환하는 API
    @GetMapping("/{nickname}/{checkcode}")
    @Operation(summary = "멤버의 우체통 아이디", description = "특정 멤버의 닉네임과 체크 코드를 입력해서 우체통 아이디를 반환합니다.")
    public ApiResponse<MemberResponseDTO.ValidateMemberDTO> validateCheckcode(
            @RequestParam(name = "nickname") String nickname,
            @RequestParam(name = "checkcode") String checkcode
    ) {
        MemberRequestDTO.validateDTO request = new MemberRequestDTO.validateDTO(nickname, checkcode);
        return ApiResponse.onSuccess(memberService.validateCheckcode(request));
    }


}