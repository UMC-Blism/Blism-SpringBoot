package com.example.blism.service;

import com.example.blism.domain.Mailbox;
import com.example.blism.domain.Member;
import com.example.blism.dto.request.MemberRequestDTO;
import com.example.blism.dto.response.MemberResponseDTO;
import com.example.blism.repository.MailboxRepository;
import com.example.blism.repository.MemberRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl {
    private final MemberRepository memberRepository;
    private final MailboxRepository mailboxRepository;

    @Transactional
    public Long joinMember(MemberRequestDTO.signupDTO request) {

        Member newMember = Member.builder()
                .checkCode(request.getCheck_code())
                .nickname(request.getNickname())
                .build();

        Mailbox newMailbox = Mailbox.createMailbox(newMember);

        memberRepository.save(newMember);
        mailboxRepository.save(newMailbox);
        return newMember.getId();
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


    // ------ List 형태 ----
    @Transactional
    public List<MemberResponseDTO.SearchMemberDTO> findNicknameList(MemberRequestDTO.searchDTO request) {
        Pageable pageable = PageRequest.of(0, 10);

        List<Member> members = memberRepository.findByNicknameContaining(request.getNickname(), pageable);

        return members.stream()
                .map(member -> MemberResponseDTO.SearchMemberDTO.builder()
                        .nickname(member.getNickname())
                        .member_id(member.getId())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public MemberResponseDTO.ValidateMemberDTO validateCheckcode(MemberRequestDTO.validateDTO request) {
        String checkcode1;

        String checkcode2 = request.getCheck_code();
        Member member = memberRepository.findByNickname(request.getNickname());
        checkcode1 = member.getCheckCode();

        if (checkcode1.equals(checkcode2)) {
            Long mailbox_id = mailboxRepository.findByOwner_Id(member.getId()).getId(); // findByUser_Id -> findByOwner_Id

            return MemberResponseDTO.ValidateMemberDTO.builder()
                    .mailbox_id(mailbox_id)
                    .build();
        } else {
            return null;
        }
    }

//    @Transactional
//    public MemberResponseDTO.ValidateMemberDTO validateCheckcode(MemberRequestDTO.validateDTO request) {
//        String checkcode1;
//
//        String checkcode2 = request.getCheck_code();
//        Member member = memberRepository.findByNickname(request.getNickname());
//        checkcode1 = member.getCheckCode();
//
//        if (checkcode1.equals(checkcode2)) {
//            Long mailbox_id = mailboxRepository.findByOwner_Id(member.getId()).getId(); // findByUser_Id -> findByOwner_Id
//
//            return MemberResponseDTO.ValidateMemberDTO.builder()
//                    .mailbox_id(mailbox_id)
//                    .build();
//        } else {
//            return null;
//        }
//    }


}
