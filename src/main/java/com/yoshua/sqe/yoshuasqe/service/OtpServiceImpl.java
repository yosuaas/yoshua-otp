package com.yoshua.sqe.yoshuasqe.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoshua.sqe.yoshuasqe.dto.request.OtpRequestDto;
import com.yoshua.sqe.yoshuasqe.dto.request.OtpValidateDto;
import com.yoshua.sqe.yoshuasqe.dto.response.OtpRequestResponseDto;
import com.yoshua.sqe.yoshuasqe.dto.response.OtpValidateResponseDto;
import com.yoshua.sqe.yoshuasqe.entity.Otp;
import com.yoshua.sqe.yoshuasqe.enums.ErrorEnum;
import com.yoshua.sqe.yoshuasqe.enums.OTPStatusEnum;
import com.yoshua.sqe.yoshuasqe.repository.OtpRepository;
import com.yoshua.sqe.yoshuasqe.utils.NumberUtils;

@Service
public class OtpServiceImpl implements OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Override
    public OtpRequestResponseDto generateOtp(OtpRequestDto otpRequestDto) {

        Optional<Otp> otpOptional = this.otpRepository.findByUserIdAndOtpStatus(otpRequestDto.getUserId(), OTPStatusEnum.CREATED.getCode());

        Long currentTime = System.currentTimeMillis();

        if (otpOptional.isPresent()) {
            Otp otp = otpOptional.get();

            // Compare create_time dengan currentTime, if < 1 minute, reject / return error
            if (currentTime - otp.getCreateTime() <= 60000L) {
                return new OtpRequestResponseDto(ErrorEnum.OTP_REQUEST_TOO_FREQUENTLY);
            }

        }

        String otpCode = NumberUtils.generateOtp();
        Otp otp = new Otp();
        otp.setUserId(otpRequestDto.getUserId());
        otp.setOtpCode(otpCode);
        otp.setCreator(otpRequestDto.getUserId());
        otp.setUpdator(otpRequestDto.getUserId());
        otp.setCreateTime(System.currentTimeMillis());
        otp.setUpdateTime(System.currentTimeMillis());
        otp.setExpiryTime(System.currentTimeMillis() + 120000L); // 2mins
        otp.setOtpStatus(OTPStatusEnum.CREATED.getCode());

        this.otpRepository.saveAndFlush(otp);

        OtpRequestResponseDto otpRequestResponseDto = new OtpRequestResponseDto();
        otpRequestResponseDto.setUserId(otpRequestDto.getUserId());
        otpRequestResponseDto.setOtp(otpCode);

        return otpRequestResponseDto;
        
    }

    @Override
    public OtpRequestResponseDto resendOtp(OtpRequestDto otpRequestDto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OtpValidateResponseDto validateOtp(OtpValidateDto otpValidateDto) {
        
        Optional<Otp> otpOptional = this.otpRepository.findByUserIdAndOtpStatusAndOtpCode(otpValidateDto.getUserId(), OTPStatusEnum.CREATED.getCode(), otpValidateDto.getOtp());
        if (!otpOptional.isPresent()) {
            return new OtpValidateResponseDto(ErrorEnum.OTP_NOT_FOUND);
        }
        Otp otp = otpOptional.get();
        otp.setUpdator(otpValidateDto.getUserId());
        otp.setUpdateTime(System.currentTimeMillis());
        

        // Check expiry
        Long currentTime = System.currentTimeMillis();
        if (currentTime - otp.getExpiryTime() >= 120000L) {
            otp.setOtpStatus(OTPStatusEnum.EXPIRED.getCode());
            this.otpRepository.saveAndFlush(otp);
            return new OtpValidateResponseDto(ErrorEnum.OTP_NOT_FOUND);
        }
        
        otp.setOtpStatus(OTPStatusEnum.VALIDATED.getCode());
        this.otpRepository.saveAndFlush(otp);

        OtpValidateResponseDto otpValidateResponseDto = new OtpValidateResponseDto();
        otpValidateResponseDto.setUserId(otpValidateDto.getUserId());
        otpValidateResponseDto.setMessage("OTP validated successfully.");

        return otpValidateResponseDto;
    }
    
}
