package com.example.blism.service;

import com.example.blism.domain.Letter;
import com.example.blism.domain.Mailbox;
import com.example.blism.domain.Member;
import com.example.blism.domain.Reply;
import com.example.blism.dto.request.MemberRequestDTO;
import com.example.blism.dto.request.RepliesRequestDTO;
import com.example.blism.dto.response.MemberResponseDTO;
import com.example.blism.dto.response.RepliesResponseDTO;
import com.example.blism.repository.LetterRepository;
import com.example.blism.repository.MailboxRepository;
import com.example.blism.repository.MemberRepository;
import com.example.blism.repository.RepliesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepliesServiceImpl {
    private final RepliesRepository repliesRepository;

    private final MailboxRepository mailboxRepository;
    private final MemberRepository memberRepository;
    private final LetterRepository letterRepository;

    @Transactional
    public Reply addreplies(RepliesRequestDTO.addreplyDTO request) {

        Member sender = memberRepository.findById(request.getSender_id()).get();
        Letter letter = letterRepository.findById(request.getLetter_id()).get();
        Member receiver = memberRepository.findById(request.getReceiver_id()).get();
        Mailbox mailbox = mailboxRepository.findById(request.getMailbox_id()).get();

        Reply newReply = Reply.builder()
                .id(request.getId())
                .content(request.getContent())
                .letter(letter)
                .sender(sender)
                .receiver(receiver)
                .mailbox(mailbox)
                .build();
        return repliesRepository.save(newReply);
    }

    @Transactional
    public List<RepliesResponseDTO.allrepliesDTO> getAllReplies(Long sender_id) {
        Member sender = memberRepository.findById(sender_id).get();
        Pageable pageable = PageRequest.of(0, 10);

        List<Reply> allReplies = repliesRepository.findAllBySenderIdContaining(sender_id, pageable);

        // json 형식으로 변환
        Letter letter = letterRepository.findById(sender_id).get();


        return allReplies.stream()
                .map(reply -> RepliesResponseDTO.allrepliesDTO.builder()
                        .content(reply.getContent())
                        .letter_id(reply.getLetter().getId())
                        .receiver_id(reply.getReceiver().getId())
                        .receiver_name(reply.getReceiver().getNickname())
                        .build())
                .collect(Collectors.toList());
    }
        //return repliesRepository.save(allReplies);


    // ----------------------------- <리스트 부분> ---------------------------






}
