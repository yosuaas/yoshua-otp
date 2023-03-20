package com.yoshua.sqe.yoshuasqe.enums;

import lombok.Getter;

public enum ErrorEnum {
    OTP_REQUEST_TOO_FREQUENTLY("otp_request_too_frequent", "OTP Request too frequent. Please try again in 1 minute."),
    OTP_NOT_FOUND("otp_not_found", "OTP Not Found.")

    ;


    @Getter
    private String errCode;

    @Getter
    private String errMsg;

    ErrorEnum(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
}
