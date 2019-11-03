package com.example.springbootdemo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

/**
 * @author HouXudong
 * @data 2018-12-28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloServiceTest {

    @Autowired
    private HelloService helloService;

    @Test
    public void hello() {
        Long hello = this.helloService.hello(100L);
        assertNotNull(hello);
    }
}
