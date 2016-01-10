package daggerok.messaging.rabbit.messaging.topics;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Sender5 {
    @Autowired ConnectionFactory connectionFactory;

    public void send(String message) {
        if (Arrays.asList("white","black","red","green","blue","pink","yellow").contains(message)) {
            if (message.toLowerCase().contains("black") || message.toLowerCase().contains("white")) {
                noColor(message);
            } else {
                color(message);
            }
        }
    }

    public void send(String routingKey, String message) {
        template(routingKey, message);
    }

    public void noColor(String message) {
        template(TopicsExchange.noColor, message);
    }

    public void color(String message) {
        template(TopicsExchange.color, message);
    }

    private void template(String routing, String message) {
        RabbitTemplate exchanged = new RabbitTemplate(connectionFactory);
        exchanged.setExchange(TopicsExchange.exchange5);
        exchanged.convertAndSend(routing, message);
    }
}