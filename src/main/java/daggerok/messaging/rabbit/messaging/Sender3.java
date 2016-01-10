package daggerok.messaging.rabbit.messaging;

import daggerok.messaging.rabbit.config.RabbitCfg3;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class Sender3 {
    @Autowired RabbitTemplate rabbitTemplate;

    public void send(String message) {
        rabbitTemplate.setExchange(RabbitCfg3.exchange3);
        rabbitTemplate.convertAndSend(message);
    }
}