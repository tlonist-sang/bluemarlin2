package com.project.bluemarlin2.bluemarlin2.controller;

import com.project.bluemarlin2.bluemarlin2.constants.ApiConstants;
import com.project.bluemarlin2.bluemarlin2.domain.CustomResponse;
import com.project.bluemarlin2.bluemarlin2.exception.AccessTokenExpireException;
import com.project.bluemarlin2.bluemarlin2.exception.PasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(PasswordException.class)
    public ResponseEntity passwordError(PasswordException passwordException) {
        CustomResponse response = new CustomResponse()
                .setStatus(ApiConstants.FAIL)
                .setErrorCode(passwordException.getErrorCode());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }


    @ExceptionHandler(AccessTokenExpireException.class)
    public ResponseEntity tokenError(AccessTokenExpireException accessTokenExpireException) {
        CustomResponse response = new CustomResponse()
                .setStatus(ApiConstants.FAIL)
                .setErrorCode(accessTokenExpireException.getErrorCode());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(response);
    }
}
