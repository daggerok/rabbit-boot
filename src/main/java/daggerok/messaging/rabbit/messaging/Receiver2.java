package daggerok.messaging.rabbit.messaging;

import daggerok.messaging.rabbit.config.rabbit.RabbitCfg2;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

@Component
public class Receiver2 {
    Logger logger = Logger.getLogger(Receiver2.class.getName());

    @Autowired CountDownLatch countDownLatch2;

    @RabbitListener(
            bindings = @QueueBinding(
            value = @Queue(value = RabbitCfg2.queue, durable = "true"),
            exchange = @Exchange(value = RabbitCfg2.queue),
            key = RabbitCfg2.queue)
    )
    public void process1(String data) {
        logger.info(String.format("███ process1: %s", data.toLowerCase()));
        countDownLatch2.countDown();
    }

    @RabbitListener(queues = RabbitCfg2.queue)
    public void process2(String data) {
        logger.info(String.format("███ process2: %s", data.toUpperCase()));
        countDownLatch2.countDown();
    }
}