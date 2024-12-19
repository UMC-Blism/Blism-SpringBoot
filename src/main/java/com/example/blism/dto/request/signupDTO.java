package com.example.blism.dto.requestdto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
public class signupDTO {
    String nickname;

    Integer password;
}
