package com.project.bluemarlin2.bluemarlin2.repository;

import com.project.bluemarlin2.bluemarlin2.domain.ArticleData;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ElasticSearchRepository {
    private final ElasticsearchOperations elasticsearchOperations;

    public List<ArticleData> findByKeywordsInContent(List<String> keywords){
        String queryString = makeQueryString(keywords);
        QueryStringQueryBuilder content = QueryBuilders.queryStringQuery(queryString).field("content");

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

    private String makeQueryString(List<String> keywords) {
        String queryString = null;
        for (String keyword : keywords) {
            queryString += "("+keyword+")";
            queryString += " OR ";
        }
        String substring = queryString.substring(0, queryString.length() - 4);
        return substring;
    }
}
