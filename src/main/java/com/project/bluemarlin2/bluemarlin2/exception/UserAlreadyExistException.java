package com.project.bluemarlin2.bluemarlin2.exception;

import com.project.bluemarlin2.bluemarlin2.constants.ErrorCode;
import lombok.Getter;

@Getter
public class UserAlreadyExistException extends GlobalException {
    String userId;

    public UserAlreadyExistException(ErrorCode errorCode) {
        super(errorCode);
    }

    public UserAlreadyExistException(ErrorCode errorCode, String userId) {
        super(errorCode);
        this.userId = userId;
    }
}
