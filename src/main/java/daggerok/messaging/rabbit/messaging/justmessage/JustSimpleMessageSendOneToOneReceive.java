package daggerok.messaging.rabbit.messaging.justmessage;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;

@Configuration
public class JustSimpleMessageSendOneToOneReceive {
    public static final String queue1 = "queue1";

    @Bean
    Queue queue1() {
        return new Queue(queue1, false);
    }


    @Bean
    public CountDownLatch countDownLatch1() {
        return new CountDownLatch(1);
    }
}