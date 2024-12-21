package com.example.blism.dto.response;

import com.example.blism.domain.Letter;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LetterDesignResponseDTO {
	private Long id;
	private String doorImageUrl;
	private Integer visibility;

	@Builder
	private LetterDesignResponseDTO(Long id, String doorImageUrl, Integer visibility) {
		this.id = id;
		this.doorImageUrl = doorImageUrl;
		this.visibility = visibility;
	}

	public static LetterDesignResponseDTO from(Letter letter, String doorImageUrl) {
		return LetterDesignResponseDTO.builder()
			.id(letter.getId())
			.doorImageUrl(doorImageUrl)
			.visibility(letter.getVisibility())
			.build();
	}
}
