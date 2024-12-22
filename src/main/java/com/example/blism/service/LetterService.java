package com.example.blism.service;

import com.example.blism.domain.Letter;
import com.example.blism.domain.Mailbox;
import com.example.blism.domain.Member;
import com.example.blism.dto.request.CreateLetterRequestDTO;
import com.example.blism.dto.response.LetterResponseDTO;
import com.example.blism.repository.LetterRepository;
import com.example.blism.repository.MailboxRepository;
import com.example.blism.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LetterService {

    private final MemberRepository memberRepository;
    private final LetterRepository letterRepository;
    private final MailboxRepository mailboxRepository;
    private final S3Service s3Service;

    public boolean createLetter(MultipartFile image, CreateLetterRequestDTO dto) {

        String photoUrl = s3Service.upload(image);

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
                .photoUrl(photoUrl)
                .content(dto.getContent())
                .font(dto.getFont())
                .visibility(dto.getVisibility())
                .build();

        letterRepository.save(letter);

        return true;
    }

    public Letter getLetter(Long letterId) {
        return letterRepository.findById(letterId).orElse(null);

    }

    public List<LetterResponseDTO> getSentLetters(Long userId) {
        return letterRepository.findAllBySenderId(userId).stream()
                .flatMap(Collection::stream) // 중첩 리스트를 평탄화
                .map(letter -> LetterResponseDTO.builder()
                        .letterId(letter.getId())
                        .content(letter.getContent())
                        .photoUrl(letter.getPhotoUrl())
                        .font(letter.getFont())
                        .senderId(letter.getSender().getId())
                        .senderNickname(letter.getSender().getNickname())
                        .receiverId(letter.getReceiver().getId())
                        .receiverNickname(letter.getReceiver().getNickname())
                        .visibility(letter.getVisibility())
                        .build()
                )
                .toList();
    }

    public List<LetterResponseDTO> getReceivedLetters(Long userId) {
        return letterRepository.findAllByReceiverId(userId).stream()
                .flatMap(Collection::stream) // 중첩 리스트를 평탄화
                .map(letter -> LetterResponseDTO.builder()
                        .letterId(letter.getId())
                        .content(letter.getContent())
                        .photoUrl(letter.getPhotoUrl())
                        .font(letter.getFont())
                        .senderId(letter.getSender().getId())
                        .senderNickname(letter.getSender().getNickname())
                        .receiverId(letter.getReceiver().getId())
                        .receiverNickname(letter.getReceiver().getNickname())
                        .visibility(letter.getVisibility())
                        .build()
                )
                .toList();
    }


    public void updateLetter(Letter letter) {
        letterRepository.save(letter);
    }
}