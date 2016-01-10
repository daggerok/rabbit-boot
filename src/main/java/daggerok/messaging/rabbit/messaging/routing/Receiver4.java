package daggerok.messaging.rabbit.messaging.routing;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Receiver4 {
    Logger logger = Logger.getLogger(Receiver4.class.getName());

    @Resource(name = "countDownLatch4")
    CountDownLatch countDownLatch4;

    @RabbitListener(queues = RoutingDirectExchange.info)
    public void process41(String data) {
        logger.info(String.format("███ %s: %s", RoutingDirectExchange.info,
                data.toLowerCase().chars()
                        .mapToObj(i -> (char) i)
                        .map(ch -> String.format("%c", ch))
                        .collect(Collectors.joining(""))));
        countDownLatch4.countDown();
    }

    @RabbitListener(queues = RoutingDirectExchange.error)
    public void process42(String data) {
        logger.info(String.format("███ %s: %s", RoutingDirectExchange.error,
                data.toLowerCase().chars()
                        .mapToObj(i -> (char) i)
                        .map(ch -> String.format(".%c.", ch))
                        .collect(Collectors.joining(""))));
        countDownLatch4.countDown();
    }
}