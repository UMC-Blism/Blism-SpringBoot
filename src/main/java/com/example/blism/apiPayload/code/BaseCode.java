package com.example.blism.apiPayload.code;

import com.example.blism.apiPayload.code.status.ReasonDTO;

public interface BaseCode {

    ReasonDTO getReason();

    ReasonDTO getReasonHttpStatus();
}