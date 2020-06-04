package com.example.springbootdemo.dubbo;


import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 * @author:hxd
 * @date:2020/6/3
 */
@Service(version = "1.0.0", timeout = 1000, interfaceClass = ProviderService.class)
@Component
public class ProviderServiceImpl implements ProviderService {
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
