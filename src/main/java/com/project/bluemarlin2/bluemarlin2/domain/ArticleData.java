package com.project.bluemarlin2.bluemarlin2.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Accessors(chain = true)
@Document(indexName = "nutch", type="default")
public class ArticleData {
    @Id @GeneratedValue
    Long id;
    String tstamp;
    String title;
    String url;
    String content;
}
