package com.ktkapp.addressservice.controller;

import com.ktkapp.addressservice.service.kafka.KafkaPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaEventController {

    private KafkaPublisher kafkaPublisher;
    @Autowired
    public KafkaEventController(KafkaPublisher kafkaPublisher) {
        this.kafkaPublisher = kafkaPublisher;
    }

    @PostMapping("/address/{publish}")
    public ResponseEntity<?> sendKafkaMessage(@PathVariable("publish") String message) {

        try {
            kafkaPublisher.sendMessage(message);
            return ResponseEntity.ok("Message published successfully.....");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
