package com.project.bluemarlin2.bluemarlin2.domain;


import com.project.bluemarlin2.bluemarlin2.constants.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class CustomResponse<T>{
    String status;
    List<T> data;
    T datum;
    int count;
    int startIndex;
    int lastIndex;
    ErrorCode errorCode;

    public CustomResponse(String status) {
        this.status = status;
    }


    public CustomResponse(String status, T datum) {
        this.status = status;
        this.datum = datum;
    }

    public CustomResponse(String status, List<T> data, int count) {
        this.status = status;
        this.data = data;
        this.count = count;
    }

    public CustomResponse(String status, List<T> data, int count, int startIndex, int lastIndex) {
        this.status = status;
        this.data = data;
        this.count = count;
        this.startIndex = startIndex;
        this.lastIndex = lastIndex;
    }

    public CustomResponse(String status, ErrorCode errorCode) {
        this.status = status;
        this.errorCode = errorCode;
    }
}
