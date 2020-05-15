package com.project.bluemarlin2.bluemarlin2.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Response <T>{
    String status;
    List<T> data;
    int count;
    int startIndex;
    int lastIndex;

    public Response(String status) {
        this.status = status;
    }

    public Response(String status, List<T> data, int count) {
        this.status = status;
        this.data = data;
        this.count = count;
    }

    public Response(String status, List<T> data, int count, int startIndex, int lastIndex) {
        this.status = status;
        this.data = data;
        this.count = count;
        this.startIndex = startIndex;
        this.lastIndex = lastIndex;
    }
}
