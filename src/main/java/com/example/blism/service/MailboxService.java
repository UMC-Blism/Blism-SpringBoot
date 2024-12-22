package com.example.blism.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.blism.domain.Letter;
import com.example.blism.domain.Mailbox;
import com.example.blism.dto.request.MailboxVisibilityRequestDTO;
import com.example.blism.dto.response.LetterDesignResponseDTO;
import com.example.blism.dto.response.MailboxResponseDTO;
import com.example.blism.dto.response.PastMailboxListResponseDTO;
import com.example.blism.dto.response.PastMailboxResponseDTO;
import com.example.blism.repository.LetterRepository;
import com.example.blism.repository.MailboxRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MailboxService{
	private final MailboxRepository mailboxRepository;
	private final LetterRepository letterRepository;
	private final S3Service s3Service;

	public MailboxResponseDTO getMailbox(Long memberId) {
		String year = LocalDateTime.now().getYear() + "";
		Mailbox mailbox = mailboxRepository.findByMemberIdAndYear(memberId, year);
		List<Letter> letters = mailbox.getLetters();
		Integer count = letterRepository.countByMailboxId(mailbox.getId());

		List<LetterDesignResponseDTO> letterDesignResponseDTOList = letters.stream()
			.map(letter -> {
				String doorImage = doorNumberConverter(letter.getDoorNum())
					+ letter.getColorNum().toString()+"_"
					+ decoNumberConverter(letter.getDecorationNum())+".png";

				String doorImageUrl = s3Service.getDecorationFileUrl(doorImage);

				return LetterDesignResponseDTO.from(letter, doorImageUrl);

			}).collect(Collectors.toList());

		return MailboxResponseDTO.from(mailbox,count, letterDesignResponseDTOList);
	}

	public PastMailboxListResponseDTO getPastMailboxList(Long memberId) {
		String year = LocalDateTime.now().getYear() + "";
		List<Mailbox> pastMailboxList = mailboxRepository.findByMemberIdAndPastYear(memberId, year);
		Integer count = mailboxRepository.countByMemberId(memberId);

		List<PastMailboxResponseDTO> pastMailboxResponseDTOList = pastMailboxList.stream()
			.map(PastMailboxResponseDTO::createPastMailBoxResponseDTO)
			.collect(Collectors.toList());

		return PastMailboxListResponseDTO.from(memberId, count, pastMailboxResponseDTOList);
	}

	public MailboxResponseDTO getPastMailbox(String year, Long memberId) {
		Mailbox pastMailbox = mailboxRepository.findByMemberIdAndYear(memberId, year);
		List<Letter> letters = pastMailbox.getLetters();
		Integer count = letterRepository.countByMailboxId(pastMailbox.getId());

		List<LetterDesignResponseDTO> letterDesignResponseDTOList = letters.stream()
			.map(letter -> {
				String doorImage = doorNumberConverter(letter.getDoorNum())
					+ letter.getColorNum().toString()+"_"
					+ decoNumberConverter(letter.getDecorationNum())+".png";
				String encodedDoorImage;
				try {
					encodedDoorImage = URLEncoder.encode(doorImage, StandardCharsets.UTF_8.toString());
				} catch (Exception e) {
					throw new RuntimeException("Error encoding URL for file: " + doorImage, e);
				}
				String doorImageUrl = s3Service.getDecorationFileUrl(encodedDoorImage);

				return LetterDesignResponseDTO.from(letter, doorImageUrl);
			}).collect(Collectors.toList());

		return MailboxResponseDTO.from(pastMailbox,count, letterDesignResponseDTOList);
	}

	public String updateVisibility(MailboxVisibilityRequestDTO requestDTO) {
		Mailbox targetMailbox = mailboxRepository.findById(requestDTO.getMailboxId()).orElse(null);

		if(requestDTO.getVisibility() == 1) {
			targetMailbox.changeVisibility(requestDTO.getVisibility());
			mailboxRepository.save(targetMailbox);
			return "우체통을 비공개로 설정합니다.";
		} else if (requestDTO.getVisibility() == 0) {
			targetMailbox.changeVisibility(requestDTO.getVisibility());
			mailboxRepository.save(targetMailbox);
			return "우체통을 공개로 설정합니다.";
		} else
			return "잘못된 입력입니다. 전환 실패";
	}

	public String doorNumberConverter(Integer number) {
		switch (number) {
			case 1:
				return "a";
			case 2:
				return "b";
			case 3:
				return "c";
			case 4:
				return "d";
			default:
				return null;
		}
	}
	public String decoNumberConverter(Integer number) {
		switch (number) {
			case 1:
				return "flower";
			case 2:
				return "ribon";
			case 3:
				return "wreath";
			case 4:
				return "bell";
			default:
				return null;
		}
	}
}
