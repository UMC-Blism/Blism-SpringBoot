package com.example.blism.dto.response;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class RepliesResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class allsentrepliesDTO{
        Long reply_id;
        Long letter_id;
        String content;
        Long receiver_id;
        String receiver_name;
        LocalDateTime created_at;
        Integer font;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class allreceivedrepliesDTO{
        Long reply_id;
        Long letter_id;
        String content;
        Long sender_id;
        String sender_name;
        LocalDateTime created_at;
        Integer font;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getreplyDTO{
        Long letter_id;
        String content;
        Long sender_id;
        String sender_name;
        Long receiver_id;
        String receiver_name;
        LocalDateTime created_at;
        Integer font;
        String photo_url;
    }


}
