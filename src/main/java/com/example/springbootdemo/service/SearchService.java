package com.example.springbootdemo.service;

import com.example.springbootdemo.util.JsonUtil;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author HouXudong
 * @date 2019-09-20
 */
@Service
public class SearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public List<Map> search() throws IOException {

        SearchRequest searchRequest = new SearchRequest("kibana_sample_data_flights");

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchPhraseQuery("DestCountry", "CN"));
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        searchRequest.source(sourceBuilder);

        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        List<Map> list = new ArrayList<>();
        for (SearchHit hit : response.getHits().getHits()) {
            Map map = JsonUtil.readValue(hit.getSourceAsString(), Map.class);
            list.add(map);
        }
        return list;
    }
}
