package com.project.bluemarlin2.bluemarlin2.domain.keywordDtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KeywordDTO {
    public KeywordDTO() {
    }

    public KeywordDTO(Long id, String word) {
        this.word = word;
        this.id = id;
    }

    String word;
    Long id;
}
