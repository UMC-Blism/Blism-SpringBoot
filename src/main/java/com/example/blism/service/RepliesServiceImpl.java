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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepliesServiceImpl {
    private final RepliesRepository repliesRepository;

    private final MailboxRepository mailboxRepository;
    private final MemberRepository memberRepository;
    private final LetterRepository letterRepository;
    private final S3Service s3Service;


    @Transactional
    public Reply addreplies(MultipartFile image, RepliesRequestDTO.addreplyDTO request) {

        String photoUrl = s3Service.upload(image);

        Letter letter = letterRepository.findById(request.getLetter_id()).get();
        Mailbox mailbox = mailboxRepository.findById(request.getMailbox_id()).get();
        Member sender = memberRepository.findById(request.getSender_id()).get();
        Member receiver = memberRepository.findById(request.getReceiver_id()).get();


        Reply newReply = Reply.builder()
                .content(request.getContent())
                .letter(letter)
                .photoUrl(photoUrl)
                .font(request.getFont())
                .mailbox(mailbox)
                .sender(sender)
                .receiver(receiver)
                .photoUrl(photoUrl)
                .build();


        return repliesRepository.save(newReply);
    }



    @Transactional
    public List<RepliesResponseDTO.allsentrepliesDTO> getAllSentReplies(Long senderId) {
        Pageable pageable = PageRequest.of(0, 10);

        // senderId로 검색
        List<Reply> allReplies = repliesRepository.findAllBySenderId(senderId, pageable);

        // JSON 형식으로 변환
        return allReplies.stream()
                .map(reply -> RepliesResponseDTO.allsentrepliesDTO.builder()
                        .reply_id(reply.getId())
                        .content(reply.getContent())
                        .letter_id(reply.getLetter().getId())
                        .receiver_id(reply.getReceiver().getId())
                        .receiver_name(reply.getReceiver().getNickname())
                        .created_at(reply.getCreatedAt())
                        .font(reply.getFont())
                        .build())
                .collect(Collectors.toList());
    }






    @Transactional
    public List<RepliesResponseDTO.allreceivedrepliesDTO> getAllReceivedReplies(Long member_id) {
        Pageable pageable = PageRequest.of(0, 10);

        List<Reply> allReplies = repliesRepository.findAllByReceiverId(member_id, pageable);
        // json 형식으로 변

        return allReplies.stream()
                .map(reply -> RepliesResponseDTO.allreceivedrepliesDTO.builder()
                        .reply_id(reply.getId())
                        .content(reply.getContent())
                        .letter_id(reply.getLetter().getId())
                        .sender_name(reply.getSender().getNickname())
                        .sender_id(reply.getSender().getId())
                        .created_at(reply.getCreatedAt())
                        .font(reply.getFont())
                        .build())
                .collect(Collectors.toList());
    }


    @Transactional
    public RepliesResponseDTO.getreplyDTO getreply(Long request) {
        Reply reply = repliesRepository.findById(request).get();


        return RepliesResponseDTO.getreplyDTO.builder()
                .content(reply.getContent())
                .letter_id(reply.getLetter().getId())
                .sender_id(reply.getSender().getId())
                .sender_name(reply.getSender().getNickname())
                .font(reply.getFont())
                .receiver_id(reply.getReceiver().getId())
                .receiver_name(reply.getReceiver().getNickname())
                .created_at(reply.getCreatedAt())
                .photo_url(reply.getPhotoUrl())
                .build();
        }



}
