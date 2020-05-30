package com.example.springbootdemo.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:hxd
 * @date:2020/5/30
 */
@Service
public class MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    public String post(String msg) {
        if (StringUtils.isBlank(msg)) {
            return "Parameter msg invalid!";
        }
        Message message = new Message("DemoTopic", "demo_tag", msg.getBytes());
        try {
            SendResult send = this.defaultMQProducer.send(message);
            return send.toString();
        } catch (Exception e) {
            logger.error("RocketMQ send message exception", e);
        }
        return null;
    }
}
