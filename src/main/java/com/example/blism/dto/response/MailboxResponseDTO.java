package com.example.blism.dto.response;

import java.util.List;

import com.example.blism.domain.Mailbox;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MailboxResponseDTO {

	private Long memberId;
	private Integer count;
	private Integer visibility;
	private List<LetterDesignResponseDTO> letters;

	@Builder
	private MailboxResponseDTO(Long memberId, Integer count, List<LetterDesignResponseDTO> letters) {
		this.memberId = memberId;
		this.count = count;
		this.letters = letters;
	}

	public static MailboxResponseDTO from(Mailbox mailbox, Integer count, List<LetterDesignResponseDTO> letters) {
		return MailboxResponseDTO.builder()
			.memberId(mailbox.getOwner().getId())
			.count(count)
			.letters(letters)
			.build();
	}
}
