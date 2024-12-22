package com.example.blism.dto.response;

import java.util.List;

import com.example.blism.domain.Mailbox;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PastMailboxListResponseDTO {
    private Long memberId;
    private Integer count;
    private List<PastMailboxResponseDTO>  pastMailboxList;

    @Builder
    private PastMailboxListResponseDTO(Long memberId, Integer count, List<PastMailboxResponseDTO> pastMailboxList) {
        this.memberId = memberId;
        this.count = count;
        this.pastMailboxList = pastMailboxList;
    }

    public static PastMailboxListResponseDTO from(Long memberId, Integer count, List<PastMailboxResponseDTO> pastMailboxes) {
        return PastMailboxListResponseDTO.builder()
                .memberId(memberId)
                .count(count)
                .pastMailboxList(pastMailboxes)
                .build();
    }
}