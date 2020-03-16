package com.example.springbootdemo.service;

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
        Boolean aBoolean = this.searchService.createIndex("students");
        System.out.println(aBoolean);
    }

    @Test
    public void isExistingIndex() throws IOException {
        Boolean existingIndex = this.searchService.isExistingIndex("students");
        System.out.println(existingIndex);
    }

    @Test
    public void indexDocument() throws IOException {
        Student student = new Student();
        student.setId("1");
        student.setClassId("1");
        student.setName("张三三");
        student.setAddress("山西省忻州市宁武县怀道乡官地村");
        String s = JsonUtil.writeValueAsString(student);
        IndexResponse response = this.searchService.indexDocument("students", "1", s);
        System.out.println(response);
    }

    @Test
    public void indexDocuments() throws IOException {
        Map<String, Object> map = new HashMap<>(16);
        for (Integer i = 2; i < 10; i++) {
            Student student = new Student();
            student.setId(i.toString());
            student.setClassId(i.toString());
            student.setName("张三三" + i);
            student.setAddress("山西省忻州市宁武县" + i);
            map.put(i.toString(), JsonUtil.writeValueAsString(student));
        }
        BulkResponse bulkResponse = this.searchService.indexDocuments("students", map);
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
