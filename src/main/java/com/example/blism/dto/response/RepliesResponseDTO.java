package com.example.blism.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RepliesResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class allrepliesDTO{
        Long letter_id;
        String content;
        Long receiver_id;
        String receiver_name;

    }
}
