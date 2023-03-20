package com.yoshua.sqe.yoshuasqe.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="otp")
public class Otp {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="otp_code", length=6, nullable=false, unique=false)
    private String otpCode;

    @Column(name="user_id", length=255, nullable=false, unique=false)
    private String userId;

    @Column(name = "create_time")
    private Long createTime;

    @Column(name = "creator")
    private String creator;

    @Column(name = "update_time")
    private Long updateTime;

    @Column(name = "updator")
    private String updator;

    @Column(name = "expiry_time")
    private Long expiryTime;

    @Column(name = "otp_status")
    private Byte otpStatus;

    
}
