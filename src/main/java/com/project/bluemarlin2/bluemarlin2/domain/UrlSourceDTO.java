package com.project.bluemarlin2.bluemarlin2.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UrlSourceDTO {

    public UrlSourceDTO() {
    }

    public UrlSourceDTO(Long id, String address) {
        this.id = id;
        this.address = address;
    }

    private Long id;
    private String address;
}
