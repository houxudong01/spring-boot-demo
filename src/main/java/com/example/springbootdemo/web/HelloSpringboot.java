package com.example.springbootdemo.web;

import com.example.springbootdemo.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HouXudong
 * @data 2018-12-28
 */
@RestController
@RequestMapping("/test")
public class HelloSpringboot {

    @Autowired
    private HelloService helloService;

    @GetMapping("/hello")
    public String hello(@RequestParam Long id) {
        Long hello = helloService.hello(id);
        System.out.println(hello);
        return "Hello Word!" + hello;
    }
}
