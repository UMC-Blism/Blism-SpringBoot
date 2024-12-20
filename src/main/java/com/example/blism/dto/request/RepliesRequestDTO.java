package com.example.blism.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RepliesRequestDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class addreplyDTO{
        Long id;
        Long sender_id;
        Long receiver_id;
        String photo_url;
        String content;
        Long mailbox_id;
        Long letter_id;

    }
}
