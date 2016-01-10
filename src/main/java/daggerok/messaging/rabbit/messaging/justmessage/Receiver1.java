package daggerok.messaging.rabbit.messaging.justmessage;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

@Component
public class Receiver1 {
    Logger logger = Logger.getLogger(Receiver1.class.getName());

    @Resource(name = "countDownLatch1")
    CountDownLatch countDownLatch1;

    @RabbitListener(queues = JustSimpleMessageSendOneToOneReceive.queue1)
    public void process1(String message) {
        logger.info("███ process1: " + message);
        countDownLatch1.countDown();
    }
}