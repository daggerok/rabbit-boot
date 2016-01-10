package daggerok.messaging.rabbit.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;

@Configuration
public class RabbitCfg2 {
    public static final String queue2 = "queue2";

    @Bean
    public Queue queue2() {
        return new Queue(queue2);
    }

    @Bean
    public CountDownLatch countDownLatch2() {
        return new CountDownLatch(2);
    }
}