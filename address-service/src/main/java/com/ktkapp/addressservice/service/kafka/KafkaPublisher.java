package com.ktkapp.addressservice.service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaPublisher {

    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    public KafkaPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private Logger log = LoggerFactory.getLogger(KafkaPublisher.class);

    public void sendMessage(String message) {
        CompletableFuture<SendResult<String, Object>> send = kafkaTemplate.send("address-service", message);

        send.whenComplete((result,ex) ->{
            if(ex == null) {
                log.info("From Address-service : " + message+" with partition : "+result.getRecordMetadata().partition()+" " +
                        " with Offset Number : "+result.getRecordMetadata().offset());
            } else {
                log.info("Unable to send message : "+message+ " due to : "+ex.getMessage());
            }
        });
    }
}
