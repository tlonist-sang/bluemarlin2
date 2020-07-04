package com.project.bluemarlin2.bluemarlin2.exception;

import com.project.bluemarlin2.bluemarlin2.constants.ErrorCode;
import lombok.Getter;


@Getter
public class PasswordException extends GlobalException {
    public PasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
