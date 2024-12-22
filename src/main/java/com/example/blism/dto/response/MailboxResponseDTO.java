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
	private Long mailboxId;
	private Integer count;
	private Integer visibility;
	private List<LetterDesignResponseDTO> letters;

	@Builder
	private MailboxResponseDTO(Long memberId, Long mailboxId, Integer count, Integer visibility, List<LetterDesignResponseDTO> letters) {
		this.memberId = memberId;
		this.mailboxId = mailboxId;
		this.count = count;
		this.letters = letters;
		this.visibility = visibility;
	}

	public static MailboxResponseDTO from(Mailbox mailbox, Integer count, List<LetterDesignResponseDTO> letters) {
		return MailboxResponseDTO.builder()
			.memberId(mailbox.getOwner().getId())
			.mailboxId(mailbox.getId())
			.count(count)
			.visibility(mailbox.getVisibility())
			.letters(letters)
			.build();
	}
}
