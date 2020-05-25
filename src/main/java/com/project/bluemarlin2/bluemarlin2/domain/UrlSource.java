package com.project.bluemarlin2.bluemarlin2.domain;

import com.project.bluemarlin2.bluemarlin2.domain.baseEntity.BaseEntity;
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
public class UrlSource extends BaseEntity {
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
}
