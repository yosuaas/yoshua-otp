package com.yoshua.sqe.yoshuasqe.enums;

import lombok.Getter;

public enum OTPStatusEnum {
    CREATED((byte)1),
    VALIDATED((byte)2),
    EXPIRED((byte)2)

    ;
    
    @Getter
    private Byte code;

    OTPStatusEnum(Byte code) {
        this.code = code;
    }
}
