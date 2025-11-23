package com.flextime.messaging;

import com.flextime.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class CheckinProducer {

    private final RabbitTemplate rabbitTemplate;

    public CheckinProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendCheckinMessage(String message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.CHECKIN_QUEUE, message);
    }
}
