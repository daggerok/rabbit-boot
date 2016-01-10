package daggerok.messaging.rabbit.messaging;

import daggerok.messaging.rabbit.config.rabbit.RabbitCfg;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {
    @Autowired RabbitTemplate rabbitTemplate;

    public void send(String message) {
        rabbitTemplate.convertAndSend(RabbitCfg.queue, String.format("content: %s", message));
    }
}