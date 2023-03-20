package com.yoshua.sqe.yoshuasqe.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.yoshua.sqe.yoshuasqe.enums.ErrorEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class OtpRequestResponseDto extends ErrorResponse {
    private String userId;
    private String otp;



    @JsonIgnore
    private boolean success;

    public OtpRequestResponseDto(ErrorEnum errorEnum) {
        this.error = errorEnum.getErrCode();
        this.errorDescription = errorEnum.getErrMsg();
    }
}
