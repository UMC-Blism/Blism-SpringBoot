package com.example.blism.dto.response;

import lombok.Builder;

@Builder
public class LetterResponseDTO {

    private Long letterId;
    private Long senderId;
    private Long receiverId;
    private String senderNickname;
    private String receiverNickname;
    private String content;
    private String photoUrl;
    private Integer font;
    private Integer visibility;
}
