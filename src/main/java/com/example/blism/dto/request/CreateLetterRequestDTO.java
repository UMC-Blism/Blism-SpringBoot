package com.example.blism.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Valid
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLetterRequestDTO {
    @NotNull
    private Long senderId;

    @NotNull
    private Long receiverId;

    @NotNull
    private Long mailboxId;

    @NotNull
    private int doorDesign;

    @NotNull
    private int colorDesign;

    @NotNull
    private int decorationDesign;

    @NotNull
    private String content;

    @NotNull
    private int font;

    @NotNull
    private int visibility;
}
