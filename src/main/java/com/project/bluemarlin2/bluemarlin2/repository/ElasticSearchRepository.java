package com.project.bluemarlin2.bluemarlin2.repository;

import com.project.bluemarlin2.bluemarlin2.domain.ArticleData;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ElasticSearchRepository {
    private final ElasticsearchOperations elasticsearchOperations;

    public List<ArticleData> findByKeywordsInContent(List<String> keywords){
        MultiMatchQueryBuilder bykeywords = QueryBuilders.multiMatchQuery("content", keywords.get(0));
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(bykeywords)
                .withIndices()
                .withTypes()
                .build();
        List<ArticleData> articleData = elasticsearchOperations.queryForList(searchQuery, ArticleData.class);
        return articleData;
    }

    public List<ArticleData> findBySingleKeywordInContent(String keyword){
        MultiMatchQueryBuilder bykeywords = QueryBuilders.multiMatchQuery("content", keyword);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(bykeywords)
                .withIndices()
                .withTypes()
                .build();
        List<ArticleData> articleData = elasticsearchOperations.queryForList(searchQuery, ArticleData.class);
        return articleData;
    }
}
