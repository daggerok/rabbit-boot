package daggerok.messaging.rabbit.messaging;

import daggerok.messaging.rabbit.config.RabbitCfg2;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

@Component
public class Receiver2 {
    Logger logger = Logger.getLogger(Receiver2.class.getName());

    @Resource(name = "countDownLatch2")
    CountDownLatch countDownLatch2;

//    @RabbitListener(
//            bindings = @QueueBinding(
//            value = @Queue(value = RabbitCfg2.queue2, durable = "true"),
//            exchange = @Exchange(value = RabbitCfg2.queue2),
//            key = RabbitCfg2.queue2)
//    )
    @RabbitListener(queues = RabbitCfg2.queue2)
    public void process21(String data) {
        logger.info(String.format("███ process21: %s", data.toLowerCase()));
        countDownLatch2.countDown();
    }

    @RabbitListener(queues = RabbitCfg2.queue2)
    public void process22(String data) {
        logger.info(String.format("███ process22: %s", data.toUpperCase()));
        countDownLatch2.countDown();
    }
}