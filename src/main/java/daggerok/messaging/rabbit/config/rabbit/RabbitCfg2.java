package daggerok.messaging.rabbit.config.rabbit;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

@Configuration
public class RabbitCfg2 {
    public static final String queue = "queue2";
    Logger logger = Logger.getLogger(RabbitCfg2.class.getName());

    @Bean
    public ConnectionFactory connectionFactory2() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public AmqpAdmin amqpAdmin2() {
        return new RabbitAdmin(connectionFactory2());
    }

    @Bean
    public RabbitTemplate rabbitTemplate2() {
        return new RabbitTemplate(connectionFactory2());
    }

    @Bean
    public Queue queue2() {
        return new Queue(queue);
    }

    @Bean public CountDownLatch countDownLatch2() {
        return new CountDownLatch(2);
    }
}