package com.flextime.messaging;

import com.flextime.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class CheckinConsumer {

    @RabbitListener(queues = RabbitMQConfig.CHECKIN_QUEUE)
    public void processMessage(String message) {
        System.out.println("📩 Mensagem recebida na fila: " + message);
    }
}
