package com.project.bluemarlin2.bluemarlin2.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class MemberDto {
    private List<Long> urlSourceIds = new ArrayList<>();
    private String email;
    private String userId;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public MemberDto(List<Long> urlSourceIds, String email, String userId, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.urlSourceIds = urlSourceIds;
        this.email = email;
        this.userId = userId;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
