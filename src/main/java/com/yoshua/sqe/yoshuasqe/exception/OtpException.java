package com.yoshua.sqe.yoshuasqe.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OtpException extends RuntimeException {

  private final String errorCode;

  private final String errorMessage;

  // ErrorCodeEnum ?

  public OtpException(String errorCode) {
    this(errorCode, null);
  }

  public OtpException(String errorCode, String errorMessage) {
    super(errorCode);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }
}
