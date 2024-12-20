package com.example.blism.service;

import com.example.blism.domain.Letter;
import com.example.blism.domain.Mailbox;
import com.example.blism.domain.Member;
import com.example.blism.dto.request.CreateLetterRequestDTO;
import com.example.blism.repository.LetterRepository;
import com.example.blism.repository.MailboxRepository;
import com.example.blism.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LetterService {

    private final MemberRepository memberRepository;
    private final LetterRepository letterRepository;
    private final MailboxRepository mailboxRepository;

    public boolean createLetter(CreateLetterRequestDTO dto) {

        Optional<Member> sender = memberRepository.findById(dto.getSenderId());

        Optional<Member> receiver = memberRepository.findById(dto.getReceiverId());

        Optional<Mailbox> mailbox = mailboxRepository.findById(dto.getMailboxId());

        if (sender.isEmpty()) {
            return false;
        }

        if (receiver.isEmpty()) {
            return false;
        }

        if(mailbox.isEmpty()){
            return false;
        }

        Letter letter = Letter.builder()
                .sender(sender.get())
                .receiver(receiver.get())
                .mailbox(mailbox.get())
                .doorNum(dto.getDoorDesign())
                .colorNum(dto.getColorDesign())
                .decorationNum(dto.getDecorationDesign())
                .photoUrl(dto.getPhotoUrl())
                .content(dto.getContent())
                .font(dto.getFont())
                .visibility(dto.getVisibility())
                .build();

        letterRepository.save(letter);

        return true;
    }
}
