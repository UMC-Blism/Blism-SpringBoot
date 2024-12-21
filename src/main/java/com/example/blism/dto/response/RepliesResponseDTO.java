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
    public static class allsentrepliesDTO{
        Long letter_id;
        String content;
        Long receiver_id;
        String receiver_name;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class allreceivedrepliesDTO{
        Long letter_id;
        String content;
        Long sender_id;
        String sender_name;

    }
}
