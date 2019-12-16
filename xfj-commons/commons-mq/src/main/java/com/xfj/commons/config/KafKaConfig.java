package com.xfj.commons.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

/**
 * @CLassNmae KafKaConfig
 * @Description kafka相关配置
 * @Author ZQ
 * @Date 2019/12/16 9:31
 * @Version 1.0
 **/
@Configuration
public class KafKaConfig {
    @Autowired
    KafkaProperties kafkaProperties;

    /**
     * @return com.xfj.user.registerVerification.KafKaRegisterSuccProducerFactory
     * @Author ZQ
     * @Description 配置kafka属性 并添加到工厂（生产端）
     * @Date 2019/12/4 17:35
     * @Param []
     **/
    @Bean
    public DefaultKafkaProducerFactory kafKaRegisterSuccProducerFactory() {
        Map<String, Object> producerProperties = kafkaProperties.buildProducerProperties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        producerProperties.put(ProducerConfig.ACKS_CONFIG, "-1");
        return new DefaultKafkaProducerFactory(producerProperties);
    }

    /**
     * @return org.springframework.kafka.core.KafkaTemplate
     * @Author ZQ
     * @Description 通过kafka类工厂创建 kafkatemplate
     * @Date 2019/12/4 17:36
     * @Param []
     **/
    @Bean
    public KafkaTemplate registerSuccInfoTemplate() {
        KafkaTemplate template = new KafkaTemplate<>(kafKaRegisterSuccProducerFactory());
        return template;
    }

    /**
     * @return com.xfj.user.registerVerification.KafKaRegisterSuccConsumerFactory
     * @Author ZQ
     * @Description 配置kafka属性 并添加到工厂（消费端）
     * @Date 2019/12/4 17:37
     * @Param []
     **/
    @Bean
    public DefaultKafkaConsumerFactory kafKaRegisterSuccConsumerFactory() {
        Map<String, Object> consumerProperties = kafkaProperties.buildProducerProperties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "default");
        consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);//消费者的自动提交方式关闭
        // consumerProperties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,10);//在一个的轮训方法中，返回的最大记录数
        consumerProperties.put("spring.json.trusted.packages", "com.xfj.user.entitys,com.xfj.user.dal.*");
        /*
        earliest:当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费
        latest: 当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，消费新产生的该分区下的数据
        none: topic各分区都存在已提交的offset时，从offset后开始消费；只要有一个分区不存在已提交的offset，则抛出异常
        */
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return new DefaultKafkaConsumerFactory(consumerProperties);
    }

    /**
     * @return org.springframework.kafka.config.KafkaListenerContainerFactory
     * @Author ZQ
     * @Description 消费者的监听工厂
     * @Date 2019/12/4 17:38
     * @Param []
     **/
    @Bean
    public KafkaListenerContainerFactory userRegisterSuccKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory conFactory = new ConcurrentKafkaListenerContainerFactory<>();
        conFactory.setConsumerFactory(kafKaRegisterSuccConsumerFactory());
        conFactory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);//  设置消费者消费消息后的提交方式为手动提交
        return conFactory;
    }
}
