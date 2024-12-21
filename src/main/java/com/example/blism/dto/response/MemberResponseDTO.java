package com.example.blism.dto.response;

import lombok.*;

public class MemberResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckMemberDTO{
        String nickname;
        Long id;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    public static class SearchMemberDTO{
        Long memeber_id;
        String nickname;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    public static class ValidateMemberDTO{
        Long mailbox_id;
    }

}
