package com.example.springbootdemo.service;

import com.example.springbootdemo.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.StringEntity;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

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
    private static final Logger logger = LoggerFactory.getLogger(SearchService.class);

    private RestHighLevelClient restHighLevelClient;

    private RestClient restLowLevelClient;

    public SearchService(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
        this.restLowLevelClient = restHighLevelClient.getLowLevelClient();
    }

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

    /**
     * 创建索引
     *
     * @param index
     * @return
     */
    public Boolean createIndex(String index) {
        try {
            CreateIndexRequest request = new CreateIndexRequest(index);
            CreateIndexResponse indexResponse = this.restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
            if (indexResponse.isAcknowledged()) {
                System.out.println("index success");
            } else {
                System.out.println("index failed");
            }
            return indexResponse.isAcknowledged();
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * 检查索引是否存在
     *
     * @param index
     * @return
     * @throws IOException
     */
    public Boolean isExistingIndex(String index) throws IOException {
        Request request = new Request("GET", "/" + index);
        Response response = this.restLowLevelClient.performRequest(request);
        if (response.getStatusLine().getReasonPhrase().equals("OK")) {
            return true;
        }
        return false;

    }

    /**
     * 向索引内添加文档
     *
     * @param indexName
     * @param docId
     * @param docJson
     * @return
     * @throws IOException
     */
    public IndexResponse indexDocument(String indexName, String docId, String docJson) throws IOException {
        IndexRequest request = new IndexRequest(indexName, "_doc", docId);
        request.source(docJson, XContentType.JSON);
        IndexResponse response = this.restHighLevelClient.index(request, RequestOptions.DEFAULT);
        return response;
    }

    /**
     * 批量添加文档到索引中
     *
     * @param indexName
     * @param idDocMap
     * @return
     * @throws IOException
     */
    public BulkResponse indexDocuments(String indexName, Map<String, Object> idDocMap) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (Map.Entry<String, Object> entry : idDocMap.entrySet()) {
            IndexRequest indexRequest = new IndexRequest(indexName, "_doc", entry.getKey());
            requestSource(indexRequest, entry.getValue());
            bulkRequest.add(indexRequest);
        }

        BulkResponse bulkResponse = this.restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return bulkResponse;
    }

    private void requestSource(IndexRequest request, Object doc) {
        if (doc instanceof String) {
            request.source((String) doc, XContentType.JSON);
        } else if (doc instanceof Map) {
            request.source((Map) doc);
        }
    }

    /**
     * 获取文档
     *
     * @param index
     * @param id
     * @return
     * @throws IOException
     */
    public GetResponse getDocument(String index, String id) throws IOException {
        GetRequest request = new GetRequest(index, id);
        GetResponse response = this.restHighLevelClient.get(request, RequestOptions.DEFAULT);
        return response;
    }

    /**
     * 删除文档
     *
     * @param index
     * @param id
     * @return
     * @throws IOException
     */
    public DeleteResponse deleteDocument(String index, String id) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(index, id);
        DeleteResponse deleteResponse = this.restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        return deleteResponse;
    }

    /**
     * 批量删除文档
     *
     * @param index
     * @param ids
     * @return
     * @throws IOException
     */
    public BulkResponse deleteDocuments(String index, List<String> ids) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        ids.forEach(id -> {
            DeleteRequest deleteRequest = new DeleteRequest(index, id);
            bulkRequest.add(deleteRequest);
        });
        BulkResponse bulkResponse = this.restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return bulkResponse;
    }

    /**
     * 搜索
     *
     * @param searchRequest
     * @return
     * @throws IOException
     */
    public SearchResponse search(SearchRequest searchRequest) throws IOException {
        SearchResponse response = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return response;
    }
}
