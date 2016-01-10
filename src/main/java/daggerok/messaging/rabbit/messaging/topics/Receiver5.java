package daggerok.messaging.rabbit.messaging.topics;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class Receiver5 {
    Logger logger = Logger.getLogger(Receiver5.class.getName());

    @Resource(name = "countDownLatch5")
    CountDownLatch countDownLatch5;

    @RabbitListener(queues = TopicsExchange.noColor)
    public void process51(String data) {
        logger.info(String.format("███ %s: %s", TopicsExchange.noColor,
                data.toLowerCase().chars()
                        .mapToObj(i -> (char) i)
                        .map(ch -> String.format("%c", ch))
                        .collect(Collectors.joining("█"))));
        countDownLatch5.countDown();
    }

    @RabbitListener(queues = TopicsExchange.color)
    public void process52(String data) {
        logger.info(String.format("### %s: %s", TopicsExchange.color,
                data.toLowerCase().chars()
                        .mapToObj(i -> (char) i)
                        .map(ch -> String.format("%c", ch))
                        .collect(Collectors.joining("#"))));
        countDownLatch5.countDown();
    }
}