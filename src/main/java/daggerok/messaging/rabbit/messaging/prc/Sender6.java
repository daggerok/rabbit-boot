package daggerok.messaging.rabbit.messaging.prc;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender6 {
    @Autowired ConnectionFactory connectionFactory;

    public String send(String message) {
        RabbitTemplate queued = new RabbitTemplate(connectionFactory);
        queued.setQueue(RemoteProcedureCalls.queue6);
        queued.setReplyTimeout(60_000L);
        queued.convertAndSend(message);

        String response = (String) queued.convertSendAndReceive(RemoteProcedureCalls.queue6, message);

        return String.format("procedure response: %s", response);
    }
}