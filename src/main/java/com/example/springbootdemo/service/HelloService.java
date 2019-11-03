package com.example.springbootdemo.service;

import org.springframework.stereotype.Service;

/**
 * @author HouXudong
 * @data 2018-12-28
 */
@Service
public class HelloService {

    public Long hello(Long id) {
        return id;
    }
}
