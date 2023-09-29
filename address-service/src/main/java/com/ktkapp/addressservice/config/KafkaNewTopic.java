package com.ktkapp.addressservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaNewTopic {
    @Bean
    public NewTopic topic(){
        return new NewTopic("address-service",3,(short)1);
    }
}
