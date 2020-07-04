package com.project.bluemarlin2.bluemarlin2.exception;

import com.project.bluemarlin2.bluemarlin2.constants.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GlobalException extends RuntimeException {
    ErrorCode errorCode;


    public GlobalException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
