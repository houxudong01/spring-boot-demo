package com.example.springbootdemo.dubbo;

/**
 * @author:hxd
 * @date:2020/6/3
 */
public interface ProviderService {
    /**
     * 服务接口
     * @param name
     * @return
     */
    String sayHello(String name);
}
