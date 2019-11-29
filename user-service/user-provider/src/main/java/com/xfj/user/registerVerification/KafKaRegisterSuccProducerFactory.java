package com.xfj.user.registerVerification;

import org.springframework.kafka.core.DefaultKafkaProducerFactory;

import java.util.Map;

/**
 * Administrator
 * 2019/8/22 0022
 * 18:31
 * 自定义的生产者工厂，也可以不要这个类
 */
public class KafKaRegisterSuccProducerFactory extends DefaultKafkaProducerFactory {
    public KafKaRegisterSuccProducerFactory(Map confings) {
        super(confings);
    }
}
