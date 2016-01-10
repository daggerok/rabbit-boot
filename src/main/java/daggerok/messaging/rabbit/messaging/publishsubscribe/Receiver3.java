package daggerok.messaging.rabbit.messaging.publishsubscribe;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
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

    @RabbitListener(queues = AllWorkersProcessAllQueueMessages.queue31)
    public void process31(String data) {
        logger.info(String.format("███ process31: %s",
                data.toLowerCase().chars()
                        .mapToObj(i -> (char) i)
                        .map(ch -> String.format("_%c_", ch))
                        .collect(Collectors.joining(","))));
        countDownLatch3.countDown();
    }

    @RabbitListener(queues = AllWorkersProcessAllQueueMessages.queue32)
    public void process32(String data) {
        logger.info(String.format("███ process32: %s",
                data.toLowerCase().chars()
                        .mapToObj(i -> (char) i)
                        .map(ch -> String.format("__%c__", ch))
                        .collect(Collectors.joining(","))));
        countDownLatch3.countDown();
    }
}