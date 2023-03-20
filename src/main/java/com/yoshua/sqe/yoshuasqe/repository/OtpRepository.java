package com.yoshua.sqe.yoshuasqe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yoshua.sqe.yoshuasqe.entity.Otp;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long>{
    
    Optional<Otp> findByUserIdAndOtpStatus(String userId, Byte otpStatus);

    Optional<Otp> findByUserIdAndOtpStatusAndOtpCode(String userId, Byte otpStatus, String otpCode);

    Optional<Otp> findByUserIdAndOtpCode(String userId, String otpCode);
}
