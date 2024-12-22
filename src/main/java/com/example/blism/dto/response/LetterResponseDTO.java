package com.example.blism.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class LetterResponseDTO {

    private Long letterId;
    private Long senderId;
    private Long receiverId;
    private String senderNickname;
    private String receiverNickname;
    private String content;
    private String photoUrl;
    private Integer font;
    private Integer doorDesign;
    private Integer colorDesign;
    private Integer decorationDesign;
    private Integer visibility;
    private LocalDateTime createdAt;
}
