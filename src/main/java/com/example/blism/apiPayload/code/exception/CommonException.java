package com.example.blism.apiPayload.code.exception;

import com.example.blism.apiPayload.code.BaseErrorCode;
import com.example.blism.apiPayload.code.status.ErrorReasonDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonException extends RuntimeException {

	private BaseErrorCode code;

	public ErrorReasonDTO getErrorReason() {
		return this.code.getReason();
	}

	public ErrorReasonDTO getErrorReasonHttpStatus(){
		return this.code.getReasonHttpStatus();
	}
}
