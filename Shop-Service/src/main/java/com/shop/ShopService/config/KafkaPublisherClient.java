package com.shop.ShopService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaPublisherClient {

    private KafkaTemplate<String , String> kafkaTemplate;
    @Autowired
    public KafkaPublisherClient(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic , String message) {
        kafkaTemplate.send(topic,message);
    }


}
