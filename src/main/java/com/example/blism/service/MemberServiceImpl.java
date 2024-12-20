package com.example.blism.service;

import com.example.blism.domain.Mailbox;
import com.example.blism.domain.Member;
import com.example.blism.dto.request.MemberRequestDTO;
import com.example.blism.dto.response.MemberResponseDTO;
import com.example.blism.repository.MailboxRepository;
import com.example.blism.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl {
    private final MemberRepository memberRepository;
    private final MailboxRepository mailboxRepository;

    @Transactional
    public Member joinMember(MemberRequestDTO.signupDTO request) {

        Member newMember = Member.builder()
                .checkCode(request.getCheck_code())
                .nickname(request.getNickname())
                .build();

        Mailbox newMailbox = Mailbox.builder()
                .owner(newMember)
                .build();

        memberRepository.save(newMember);
        mailboxRepository.save(newMailbox);
        return newMember;
    }

    @Transactional
    public MemberResponseDTO.CheckMemberDTO checkduplicatenickname(String request) {
        Member search_member = memberRepository.findByNickname(request);

        if(search_member == null) {
            return null;
        }
        else{
         return MemberResponseDTO.CheckMemberDTO.builder()
                 .id(search_member.getId())
                 .nickname(search_member.getNickname())
                 .build();
        }
    }

    @Transactional
    public Member changenickname(String original_nickname, String new_nickname) {
        Member member = memberRepository.getMembersByNickname(original_nickname);
        member.setNickname(new_nickname);
        return member;
    }


    @Transactional
    public MemberResponseDTO.SearchMemberDTO searchmember(MemberRequestDTO.searchDTO request) {
        String nickname = request.getNickname();
        Integer checkCode = request.getCheck_code();

        Member member =  memberRepository.findByNicknameAndCheckCode(nickname, checkCode);

        return  MemberResponseDTO.SearchMemberDTO.builder()
                .mailboxId(member.getMailboxes().get(0).getId())
                .userId(member.getId())
                .build();

    }

}
