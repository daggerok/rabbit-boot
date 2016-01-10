package daggerok.messaging.rabbit.messaging.prc;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class Receiver6 {
    Logger logger = Logger.getLogger(daggerok.messaging.rabbit.messaging.topics.Receiver5.class.getName());

    @RabbitListener(queues = RemoteProcedureCalls.queue6)
    public String process6(String data) {
        String result = String.format("███ %s: %s", RemoteProcedureCalls.queue6
                , data.toLowerCase().chars()
                .mapToObj(i -> (char) i)
                .map(ch -> String.format("%c", ch).toUpperCase())
                .collect(Collectors.joining("█")));
        logger.info(result);
        return result;
    }
}