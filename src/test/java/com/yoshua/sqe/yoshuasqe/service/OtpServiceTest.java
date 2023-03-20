package com.yoshua.sqe.yoshuasqe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.yoshua.sqe.yoshuasqe.dto.request.OtpRequestDto;
import com.yoshua.sqe.yoshuasqe.dto.request.OtpValidateDto;
import com.yoshua.sqe.yoshuasqe.dto.response.OtpRequestResponseDto;
import com.yoshua.sqe.yoshuasqe.dto.response.OtpValidateResponseDto;
import com.yoshua.sqe.yoshuasqe.entity.Otp;
import com.yoshua.sqe.yoshuasqe.enums.ErrorEnum;
import com.yoshua.sqe.yoshuasqe.enums.OTPStatusEnum;
import com.yoshua.sqe.yoshuasqe.repository.OtpRepository;

@SpringBootTest
public class OtpServiceTest {
    

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private OtpService otpService;

    private final String USER_ID_GENERATE = "yoshua_generate";
    private final String USER_ID_VALIDATE = "yoshua_validate";


    @Test
    void generateOtp_returnSuccess() {
        // Call generate OTP
        OtpRequestDto otpRequestDto = new OtpRequestDto();
        otpRequestDto.setUserId(USER_ID_GENERATE);
        
        OtpRequestResponseDto generateOtpResp = this.otpService.generateOtp(otpRequestDto);
        String generatedOtp = generateOtpResp.getOtp();


        // Validate
        Optional<Otp> otpOptional = this.otpRepository.findByUserIdAndOtpCode(otpRequestDto.getUserId(), generatedOtp);

        assertNotNull(otpOptional.get());
        assertEquals(generatedOtp, otpOptional.get().getOtpCode());
        
    }

    @Test
    void validateOtp_validOtp_returnSuccess() {

        // Call generate OTP
        String otpResponse = this.getOtp(USER_ID_GENERATE);

        // Assert otp generated successfully
        Optional<Otp> otpGenerateOpt = this.otpRepository.findByUserIdAndOtpStatusAndOtpCode(USER_ID_VALIDATE, OTPStatusEnum.CREATED.getCode(), otpResponse);
        assertNotNull(otpGenerateOpt.get());

        // Call validate OTP
        OtpValidateDto otpValidateDto = new OtpValidateDto();
        otpValidateDto.setUserId(USER_ID_VALIDATE);
        otpValidateDto.setOtp(otpResponse);
        
        this.otpService.validateOtp(otpValidateDto);

        Optional<Otp> otpValidateOptional = this.otpRepository.findByUserIdAndOtpStatusAndOtpCode(USER_ID_VALIDATE, OTPStatusEnum.VALIDATED.getCode(), otpResponse);
        // Assert 
        // Validate status already covered in SQL query
        assertNotNull(otpValidateOptional.get());
        
    }

    @Test
    void validateOtp_invalidOtp_returnError() {

        String otpResponse = this.getOtp(USER_ID_VALIDATE);
        // Assert otp generated successfully
        Optional<Otp> otpGenerateOpt = this.otpRepository.findByUserIdAndOtpStatusAndOtpCode(USER_ID_VALIDATE, OTPStatusEnum.CREATED.getCode(), otpResponse);
        assertNotNull(otpGenerateOpt.get());

        OtpValidateDto otpValidateDto = new OtpValidateDto();
        otpValidateDto.setUserId(USER_ID_VALIDATE);
        otpValidateDto.setOtp("999999");

        OtpValidateResponseDto validateOtpResp = this.otpService.validateOtp(otpValidateDto);

        // Assert
        assertNotNull(validateOtpResp.getError());
        assertNotNull(validateOtpResp.getErrorDescription());
        assertEquals(ErrorEnum.OTP_NOT_FOUND.getErrCode(), validateOtpResp.getError());
        assertEquals(ErrorEnum.OTP_NOT_FOUND.getErrMsg(), validateOtpResp.getErrorDescription());
        
    }

    // Not completed, still error
    @Test
    void validateOtp_MoreThan2Minutes_returnError() {

        String otpResponse = this.getOtp(USER_ID_VALIDATE);
        // Assert otp generated successfully
        Optional<Otp> otpGenerateOpt = this.otpRepository.findByUserIdAndOtpStatusAndOtpCode(USER_ID_VALIDATE, OTPStatusEnum.CREATED.getCode(), otpResponse);
        assertNotNull(otpGenerateOpt.get());
        // Set create time to 2 minutes ago
        Otp otp = otpGenerateOpt.get();
        otp.setCreateTime(otp.getCreateTime() - 121000L);
        this.otpRepository.saveAndFlush(otp);

        OtpValidateDto otpValidateDto = new OtpValidateDto();
        otpValidateDto.setUserId(USER_ID_VALIDATE);
        otpValidateDto.setOtp(otpResponse);

        OtpValidateResponseDto validateOtpResp = this.otpService.validateOtp(otpValidateDto);

        // Assert
        assertNotNull(validateOtpResp.getError());
        assertNotNull(validateOtpResp.getErrorDescription());
        assertEquals(ErrorEnum.OTP_NOT_FOUND.getErrCode(), validateOtpResp.getError());
        assertEquals(ErrorEnum.OTP_NOT_FOUND.getErrMsg(), validateOtpResp.getErrorDescription());

        // Validate the otp status is updated to expired
        Optional<Otp> otpExpiredOpt = this.otpRepository.findByUserIdAndOtpStatusAndOtpCode(USER_ID_VALIDATE, OTPStatusEnum.EXPIRED.getCode(), otpResponse);
        assertNotNull(otpExpiredOpt.get());
        
    }

    private String getOtp(String userId) {
        OtpRequestDto otpRequestDto = new OtpRequestDto();
        otpRequestDto.setUserId(userId);

        OtpRequestResponseDto generateOtpResp = this.otpService.generateOtp(otpRequestDto);
        return generateOtpResp.getOtp();
    }
}
