package com.project.bluemarlin2.bluemarlin2.domain.keywordDtos;

import lombok.Data;

@Data
public class DeleteKeywordDto {
    Long urlId;
    String keyword;

    public DeleteKeywordDto(Long urlId, String keyword) {
        this.urlId = urlId;
        this.keyword = keyword;
    }
}
