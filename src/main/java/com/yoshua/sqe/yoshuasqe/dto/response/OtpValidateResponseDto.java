package com.yoshua.sqe.yoshuasqe.dto.response;

import com.yoshua.sqe.yoshuasqe.enums.ErrorEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class OtpValidateResponseDto extends ErrorResponse {
    private String userId;
    private String message;

    public OtpValidateResponseDto(ErrorEnum errorEnum) {
        this.error = errorEnum.getErrCode();
        this.errorDescription = errorEnum.getErrMsg();
    }
}
