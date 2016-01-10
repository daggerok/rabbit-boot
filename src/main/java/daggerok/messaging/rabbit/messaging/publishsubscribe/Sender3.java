package daggerok.messaging.rabbit.messaging.publishsubscribe;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender3 {
    @Autowired ConnectionFactory connectionFactory;

    public void send(String message) {
        RabbitTemplate exchanged = new RabbitTemplate(connectionFactory);
        exchanged.setExchange(AllWorkersProcessAllQueueMessages.exchange3);
        exchanged.convertAndSend(message);
    }
}