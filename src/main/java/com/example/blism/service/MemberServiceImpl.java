package com.example.blism.service;

import com.example.blism.domain.Member;
import com.example.blism.repository.MemberRepository;
import com.example.blism.dto.requestdto.signupDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl {
    private final MemberRepository memberRepository;

    @Transactional
    public Member joinMember(signupDTO request) {

        Member newMember = Member.builder()
                .checkCode(request.getPassword())
                .nickname(request.getNickname())
                .build();


        return memberRepository.save(newMember);
    }

    @Transactional
    public String checkduplicatenickname(String request) {


        if(memberRepository.existsMembersByNickname(request)) {
            return "true";
        }
        else{
         return "false";
        }
    }

    @Transactional
    public Member changenickname(String original_nickname, String new_nickname) {
        Member member = memberRepository.getMembersByNickname(original_nickname);
        member.setNickname(new_nickname);
        return member;
    }

}
