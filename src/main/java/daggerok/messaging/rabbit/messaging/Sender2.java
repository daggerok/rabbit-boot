package daggerok.messaging.rabbit.messaging;

import daggerok.messaging.rabbit.config.RabbitCfg2;
import daggerok.messaging.rabbit.config.RabbitCfg3;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender2 {
    @Autowired RabbitTemplate rabbitTemplate;

    public void send(String message) {
        rabbitTemplate.convertAndSend(RabbitCfg2.queue2, message);
    }
}