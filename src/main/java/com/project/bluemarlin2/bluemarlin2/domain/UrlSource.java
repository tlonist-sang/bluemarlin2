package com.project.bluemarlin2.bluemarlin2.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Accessors(chain=true)
public class UrlSource {
    @Id
    @GeneratedValue
    @Column(name="URL_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    private String url;

    @OneToMany (mappedBy = "urlSource", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Keyword> keywords = new ArrayList();

    public List<KeywordDTO> getWords(){
        List<KeywordDTO> words = new ArrayList<>();
        for(Keyword keyword : keywords){
            words.add(new KeywordDTO(keyword.getId(),keyword.getWord()));
        }
        return words;
    }

}
