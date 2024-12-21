package com.example.blism.dto.response;

import com.example.blism.domain.Mailbox;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PastMailboxResponseDTO {
	private Long pastMailboxId;
	private String year;

	@Builder
	private PastMailboxResponseDTO(Long pastMailboxId, String year) {
		this.pastMailboxId = pastMailboxId;
		this.year = year;
	}

	public static PastMailboxResponseDTO createPastMailBoxResponseDTO(Mailbox mailbox) {
		String year = String.valueOf(mailbox.getCreatedAt().getYear());

		return PastMailboxResponseDTO.builder()
			.pastMailboxId(mailbox.getId())
			.year(year)
			.build();
	}
}
