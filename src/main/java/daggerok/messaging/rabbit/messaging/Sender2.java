package daggerok.messaging.rabbit.messaging;

import daggerok.messaging.rabbit.config.rabbit.RabbitCfg2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class Sender2 {
    @Resource(name = "rabbitTemplate2") RabbitTemplate rabbitTemplate2;

    public void send(String message) {
        rabbitTemplate2.convertAndSend(RabbitCfg2.queue, String.format("content: %s", message));
    }
}