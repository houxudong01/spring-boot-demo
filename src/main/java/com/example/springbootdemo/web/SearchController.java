package com.example.springbootdemo.web;

import com.example.springbootdemo.pojo.ApiResult;
import com.example.springbootdemo.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author HouXudong
 * @date 2019-09-20
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/search")
    public ApiResult<List<Map>> search() throws IOException {
        List<Map> mapList = this.searchService.search();
        return new ApiResult<>(mapList);
    }
}
