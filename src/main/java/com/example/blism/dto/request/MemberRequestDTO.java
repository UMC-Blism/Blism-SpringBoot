package com.example.blism.dto.request;

import lombok.*;


public class MemberRequestDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class signupDTO{
        String nickname;
        String check_code;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class searchDTO{
        String nickname;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class validateDTO{
        String nickname;
        String check_code;
    }

}
