package daggerok.messaging.rabbit.messaging;

import daggerok.messaging.rabbit.config.RabbitCfg3;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Receiver3 {
    Logger logger = Logger.getLogger(Receiver3.class.getName());

    @Resource(name = "countDownLatch3")
    CountDownLatch countDownLatch3;

    @RabbitListener(queues = RabbitCfg3.queue31)
    public void process31(String data) {
        logger.info(String.format("███ process31: %s",
                Stream.of(data.toLowerCase())
                        .map(i -> i.concat("_"))
                        .collect(Collectors.joining("_"))));
        countDownLatch3.countDown();
    }

    @RabbitListener(queues = RabbitCfg3.queue32)
    public void process32(String data) {
        logger.info(String.format("███ process32: %s",
                Stream.of(data.toLowerCase())
                        .map(i -> i.concat("__"))
                        .collect(Collectors.joining("__"))));
        countDownLatch3.countDown();
    }
}