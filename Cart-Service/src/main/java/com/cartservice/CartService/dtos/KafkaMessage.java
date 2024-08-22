package com.cartservice.CartService.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KafkaMessage {
    private String to;
    private String from;
    private String subject;
    private String body;

}
