package com.example.blism.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.blism.common.BaseTimeEntity;
import com.example.blism.domain.Letter;
import com.example.blism.domain.Mailbox;
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
public class MailboxService extends BaseTimeEntity {
	private final MailboxRepository mailboxRepository;
	private final LetterRepository letterRepository;
	private final S3Service s3Service;

	public MailboxResponseDTO getMailbox(Long memberId) {
		String year = LocalDateTime.now().getYear() + "";
		Mailbox mailbox = mailboxRepository.findByMemberId(memberId, year);
		List<Letter> letters = mailbox.getLetters();
		Integer count = letterRepository.countByMailboxId(memberId);

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

		return MailboxResponseDTO.from(mailbox,count, letterDesignResponseDTOList);
	}

	public PastMailboxListResponseDTO getPastMailboxList(Long memberId) {
		List<Mailbox> pastMailboxList = mailboxRepository.findByMemberId(memberId);
		Integer count = mailboxRepository.countByMemberId(memberId);

		List<PastMailboxResponseDTO> pastMailboxResponseDTOList = pastMailboxList.stream()
			.map(PastMailboxResponseDTO::createPastMailBoxResponseDTO)
			.collect(Collectors.toList());

		return PastMailboxListResponseDTO.from(memberId, count, pastMailboxResponseDTOList);
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
				return "꽃";
			case 2:
				return "리본";
			case 3:
				return "리스";
			case 4:
				return "종";
			default:
				return null;
		}
	}
}