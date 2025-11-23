package com.flextime.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String CHECKIN_QUEUE = "checkinQueue";

    @Bean
    public Queue checkinQueue() {
        return new Queue(CHECKIN_QUEUE, true);
    }
}
