package daggerok.messaging.rabbit.messaging.justmessage;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender1 {
    @Autowired RabbitTemplate rabbitTemplate;

    public void send(String message) {
        rabbitTemplate.convertAndSend(JustSimpleMessageSendOneToOneReceive.queue1, message);
    }
}