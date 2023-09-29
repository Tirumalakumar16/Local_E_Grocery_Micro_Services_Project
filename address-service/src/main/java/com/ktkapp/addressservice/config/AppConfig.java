package com.ktkapp.addressservice.config;
//
//import feign.codec.Decoder;
//import feign.jackson.JacksonDecoder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper getMapper() {
        return new ModelMapper();
    }
//    @Bean
//    public Decoder getDecoder() {
//        return new JacksonDecoder();
//    }
}
