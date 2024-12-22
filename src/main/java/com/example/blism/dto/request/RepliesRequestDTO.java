package com.example.blism.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class RepliesRequestDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class addreplyDTO{
        String content;
        Long letter_id;
        Integer font;
        Long sender_id;
        Long receiver_id;
        Long mailbox_id;

    }



}
