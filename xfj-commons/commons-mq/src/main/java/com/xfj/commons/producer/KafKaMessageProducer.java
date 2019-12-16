package com.xfj.commons.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author ZQ
 * @Description kafka 生产者发送消息工具类
 * @Date 2019/11/27 21:27
 **/
@Component
@Slf4j
public class KafKaMessageProducer {
    @Autowired
    @Qualifier("registerSuccInfoTemplate")
    KafkaTemplate kafkaTemplate;

    /**
     * @return void
     * @Author ZQ
     * @Description 生产者发送消息到指定topic
     * @Date 2019/12/4 17:57
     * @Param [topic, uerVerifyMap：消息内容]
     **/
    public void sendMessage(String topic, Map uerVerifyMap) {
        try {
            kafkaTemplate.send(topic, uerVerifyMap);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

    }
}
