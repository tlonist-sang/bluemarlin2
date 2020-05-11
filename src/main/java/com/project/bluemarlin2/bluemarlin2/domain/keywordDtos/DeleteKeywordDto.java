package com.project.bluemarlin2.bluemarlin2.domain.keywordDtos;

import lombok.Data;

@Data
public class DeleteKeywordDto {
    String userId;
    String urlName;
    String keyword;

    public DeleteKeywordDto(String userId, String urlName, String keyword) {
        this.userId = userId;
        this.urlName = urlName;
        this.keyword = keyword;
    }
}
