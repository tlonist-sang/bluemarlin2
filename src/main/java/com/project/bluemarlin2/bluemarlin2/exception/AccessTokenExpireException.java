package com.project.bluemarlin2.bluemarlin2.exception;

import javax.servlet.ServletException;

public class AccessTokenExpireException extends ServletException {
    String errorCode;
    String message;

    public AccessTokenExpireException(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
