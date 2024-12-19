package com.example.blism.apiPayload.code;

import com.example.blism.apiPayload.code.status.ErrorReasonDTO;

public interface BaseErrorCode {

    ErrorReasonDTO getReason();

    ErrorReasonDTO getReasonHttpStatus();
}
