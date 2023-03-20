package com.yoshua.sqe.yoshuasqe.service;

import com.yoshua.sqe.yoshuasqe.dto.request.OtpRequestDto;
import com.yoshua.sqe.yoshuasqe.dto.request.OtpValidateDto;
import com.yoshua.sqe.yoshuasqe.dto.response.OtpRequestResponseDto;
import com.yoshua.sqe.yoshuasqe.dto.response.OtpValidateResponseDto;

public interface OtpService {
    OtpRequestResponseDto generateOtp(OtpRequestDto otpRequestDto);
    OtpRequestResponseDto resendOtp(OtpRequestDto otpRequestDto);
    OtpValidateResponseDto validateOtp(OtpValidateDto otpValidateDto);
}
