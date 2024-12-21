package com.example.blism.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MailboxVisibilityRequestDTO {

	private Long mailboxId;
	private Integer visibility;
}
