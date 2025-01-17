package com.example.blism.apiPayload.code.status;


import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorReasonDTO {

    private HttpStatus httpStatus;

    private final boolean isSuccess;
    private final int code;
    private final String message;

    public boolean getIsSuccess(){return isSuccess;}
}

