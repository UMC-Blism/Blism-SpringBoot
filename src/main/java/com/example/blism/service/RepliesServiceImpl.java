package com.example.blism.service;

import com.example.blism.domain.Letter;
import com.example.blism.domain.Mailbox;
import com.example.blism.domain.Member;
import com.example.blism.domain.Reply;
import com.example.blism.dto.request.MemberRequestDTO;
import com.example.blism.dto.request.RepliesRequestDTO;
import com.example.blism.repository.LetterRepository;
import com.example.blism.repository.MailboxRepository;
import com.example.blism.repository.MemberRepository;
import com.example.blism.repository.RepliesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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




}
