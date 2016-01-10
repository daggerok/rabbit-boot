package daggerok.messaging.rabbit.messaging.routing;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender4 {
    @Autowired ConnectionFactory connectionFactory;

    public void send(String message) {
        if (message.toLowerCase().contains("log")) {
            if (message.toLowerCase().contains("error")) {
                error(message);
            } else {
                info(message);
            }
        }
    }

    public void send(String routingKey, String message) {
        template(routingKey, message);
    }

    public void info(String message) {
        template(RoutingDirectExchange.info, message);
    }

    public void error(String message) {
        template(RoutingDirectExchange.error, message);
    }

    private void template(String routing, String message) {
        RabbitTemplate exchanged = new RabbitTemplate(connectionFactory);
        exchanged.setExchange(RoutingDirectExchange.exchange4);
        exchanged.convertAndSend(routing, message);
    }
}