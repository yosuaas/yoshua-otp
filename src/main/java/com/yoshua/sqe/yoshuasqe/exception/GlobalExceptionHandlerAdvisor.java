package com.yoshua.sqe.yoshuasqe.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yoshua.sqe.yoshuasqe.dto.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandlerAdvisor {

  @ExceptionHandler(value = {OtpException.class})
  @ResponseBody
  public ErrorResponse otpExceptionHandler(OtpException e) {
    log.error("otpExceptionHandler error: {}", e.getErrorMessage());
    ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
    return errorResponse;
  }

}
