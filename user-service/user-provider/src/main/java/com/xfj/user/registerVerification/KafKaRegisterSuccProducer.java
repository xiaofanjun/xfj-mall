package com.xfj.user.registerVerification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author ZQ
 * @Description
 * @Date 2019/11/27 21:27
 **/
@Component
@Slf4j
public class KafKaRegisterSuccProducer {
    @Autowired
    @Qualifier("registerSuccInfoTemplate")
    KafkaTemplate kafkaTemplate;

    private final static String topic = "user-register-succ-topic";

    public void sendRegisterSuccInfo(Map uerVerifyMap) {
        try {
            kafkaTemplate.send(topic, uerVerifyMap);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

    }
}
