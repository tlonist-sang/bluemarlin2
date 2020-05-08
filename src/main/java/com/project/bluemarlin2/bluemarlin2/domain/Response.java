package com.project.bluemarlin2.bluemarlin2.domain;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Response <T>{
    String status;
    List<T> data;
    int count;
    int startIndex;
    int lastIndex;
}
