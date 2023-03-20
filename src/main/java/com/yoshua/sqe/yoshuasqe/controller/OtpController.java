package com.yoshua.sqe.yoshuasqe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yoshua.sqe.yoshuasqe.dto.request.OtpRequestDto;
import com.yoshua.sqe.yoshuasqe.dto.request.OtpValidateDto;
import com.yoshua.sqe.yoshuasqe.dto.response.OtpRequestResponseDto;
import com.yoshua.sqe.yoshuasqe.dto.response.OtpValidateResponseDto;
import com.yoshua.sqe.yoshuasqe.service.OtpService;

@RestController
public class OtpController {

    @Autowired
    private OtpService otpService;
    
    @PostMapping("/v1/otp/request")
    private OtpRequestResponseDto requestOtp(@RequestBody OtpRequestDto otpRequestDto) {
        return this.otpService.generateOtp(otpRequestDto);
    }

    @PostMapping("/v1/otp/resend")
    private OtpRequestResponseDto resendOtp(@RequestBody OtpRequestDto otpRequestDto) {
        return this.otpService.generateOtp(otpRequestDto);
    }

    @PostMapping("/v1/otp/validate")
    private OtpValidateResponseDto validateOtp(@RequestBody OtpValidateDto otpValidateDto) {
        return this.otpService.validateOtp(otpValidateDto);
    }
}
