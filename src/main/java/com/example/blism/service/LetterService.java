package com.example.blism.service;

import com.example.blism.apiPayload.code.BaseErrorCode;
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

    public String createLetter(MultipartFile image, CreateLetterRequestDTO dto) {


        Optional<Member> sender = memberRepository.findById(dto.getSenderId());
        Optional<Member> receiver = memberRepository.findById(dto.getReceiverId());
        Optional<Mailbox> mailbox = mailboxRepository.findById(dto.getMailboxId());

        if (sender.isEmpty()) {
            return "보내는 사람이 없습니다.";
        }

        if (receiver.isEmpty()) {
            return "받는 사람이 없습니다.";
        }

        if(mailbox.isEmpty()){
            return "우체통이 존재하지 않습니다.";
        }

        String photoUrl = s3Service.upload(image);

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

        return "편지 생성 성공";
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
                        .doorDesign(letter.getDoorNum())
                        .colorDesign(letter.getColorNum())
                        .decorationDesign(letter.getDecorationNum())
                        .senderId(letter.getSender().getId())
                        .senderNickname(letter.getSender().getNickname())
                        .receiverId(letter.getReceiver().getId())
                        .receiverNickname(letter.getReceiver().getNickname())
                        .visibility(letter.getVisibility())
                        .createdAt(letter.getCreatedAt())
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
                        .doorDesign(letter.getDoorNum())
                        .colorDesign(letter.getColorNum())
                        .decorationDesign(letter.getDecorationNum())
                        .senderId(letter.getSender().getId())
                        .senderNickname(letter.getSender().getNickname())
                        .receiverId(letter.getReceiver().getId())
                        .receiverNickname(letter.getReceiver().getNickname())
                        .visibility(letter.getVisibility())
                        .createdAt(letter.getCreatedAt())
                        .build()
                )
                .toList();
    }


    public void updateLetter(Letter letter) {
        letterRepository.save(letter);
    }
}
