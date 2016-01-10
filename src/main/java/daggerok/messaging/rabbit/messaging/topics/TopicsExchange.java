package daggerok.messaging.rabbit.messaging.topics;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;

@Configuration
public class TopicsExchange {
    public static final String noColor = "noColor";

    public static final String color = "color";

    public static final String exchange5 = "exchange5";

    @Bean
    public Queue noColor() {
        return new Queue(noColor);
    }

    @Bean
    public Queue color() {
        return new Queue(color);
    }

    @Bean
    public CountDownLatch countDownLatch5() {
        return new CountDownLatch(8);
    }

    @Bean
    public TopicExchange exchange5() {
        return new TopicExchange(exchange5);
    }

    @Bean
    public Binding binding51(TopicExchange exchange5) {
        return BindingBuilder.bind(noColor()).to(exchange5).with(noColor);
    }

    @Bean
    public Binding binding52(TopicExchange exchange5) {
        return BindingBuilder.bind(color()).to(exchange5).with(noColor);
    }

    @Bean
    public Binding binding53(TopicExchange exchange5) {
        return BindingBuilder.bind(color()).to(exchange5).with(color);
    }
}