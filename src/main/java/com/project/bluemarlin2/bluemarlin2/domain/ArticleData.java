package com.project.bluemarlin2.bluemarlin2.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@Document(indexName = "nutch", type="default")
public class ArticleData {
    @Id @GeneratedValue
    String id;
    String tstamp;
    String segment;
    List<String> anchor;
    String digest;
    String host;
    String boost;
    String title;
    String url;
    String content;
}
