package com.project.bluemarlin2.bluemarlin2.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity(name="keyword_data")
@Getter
@Setter
@Accessors(chain=true)
public class Keyword {

    public Keyword() {
    }

    public Keyword(UrlSource urlSource, String word) {
        this.urlSource = urlSource;
        this.word = word;
    }

    @Id
    @GeneratedValue
    @Column(name="KEYWORD_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="URL_ID")
    private UrlSource urlSource;

    private String word;

}
