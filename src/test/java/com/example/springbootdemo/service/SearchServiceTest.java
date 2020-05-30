package com.example.springbootdemo.service;

import com.example.springbootdemo.pojo.Article;
import com.example.springbootdemo.pojo.Student;
import com.example.springbootdemo.util.JsonUtil;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author:hxd
 * @date:2020/3/16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchServiceTest {
    @Autowired
    private SearchService searchService;

    @Test
    public void createIndex() {
        Boolean aBoolean = this.searchService.createIndex("zh_articles");
        System.out.println(aBoolean);
    }

    @Test
    public void isExistingIndex() throws IOException {
        Boolean existingIndex = this.searchService.isExistingIndex("zh_articles");
        System.out.println(existingIndex);
    }

    @Test
    public void indexDocument() throws IOException {
        Article article = new Article();
        article.setId(3);
        article.setTitle("深入理解 spring cloud");
        article.setContent("深入理解 spring cloud, mybatis");
        article.setAuthor("方志朋");
        article.setLikes(15);
        article.setVersion(2);
        String s = JsonUtil.writeValueAsString(article);
        IndexResponse response = this.searchService.indexDocument("zh_articles", "4", s);
        System.out.println(response);
    }

    @Test
    public void indexDocuments() throws IOException {
        Map<String, Object> map = new HashMap<>(16);
        for (Integer i = 1; i <= 3; i++) {
            Article article = new Article();
            article.setId(i);
            article.setTitle("jfjj");
            article.setContent("fff");
            article.setAuthor("fff");
            article.setLikes(i);
            article.setVersion(i + 3);
            map.put(i.toString(), JsonUtil.writeValueAsString(article));
        }
        BulkResponse bulkResponse = this.searchService.indexDocuments("zh_articles", map);
        System.out.println(bulkResponse);
    }

    @Test
    public void getDocument() throws IOException {
        GetResponse getResponse = this.searchService.getDocument("students", "8");
        String sourceAsString = getResponse.getSourceAsString();
        Student student = JsonUtil.readValue(sourceAsString, Student.class);
        System.out.println(student);
    }

    @Test
    public void deleteDocument() throws IOException {
        DeleteResponse deleteResponse = this.searchService.deleteDocument("students", "7");
        System.out.println(deleteResponse);
    }

    @Test
    public void search() throws IOException {
        SearchRequest searchRequest = new SearchRequest("students");

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchPhraseQuery("address", "宁武"));
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = this.searchService.search(searchRequest);

        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Student student = JsonUtil.readValue(hit.getSourceAsString(), Student.class);
            System.out.println(student);
        }
    }
}
