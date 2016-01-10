package daggerok.messaging.rabbit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;

@Configuration
public class RabbitCfg3 {
    public static final String queue31 = "queue31";

    public static final String queue32 = "queue32";

    public static final String exchange3 = "exchange3";

//    @Bean // see daggerok.messaging.rabbit.messaging.Sender3.send
//    public RabbitTemplate rabbitTemplate3(ConnectionFactory connectionFactory) {
//        RabbitTemplate rabbitTemplate3 = new RabbitTemplate(connectionFactory);
//        rabbitTemplate3.setExchange(exchange3);
//        return rabbitTemplate3;
//    }

    @Bean
    public Queue queue31() {
        return new Queue(queue31);
    }

    @Bean
    public Queue queue32() {
        return new Queue(queue32);
    }

    @Bean
    public CountDownLatch countDownLatch3() {
        return new CountDownLatch(6);
    }

    @Bean
    public FanoutExchange exchange3() {
        return new FanoutExchange(exchange3);
    }

    @Bean
    public Binding binding31() {
        return BindingBuilder.bind(queue31()).to(exchange3());
    }

    @Bean
    public Binding binding32() {
        return BindingBuilder.bind(queue32()).to(exchange3());
    }
}