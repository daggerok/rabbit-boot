package daggerok.messaging.rabbit.messaging.routing;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;

@Configuration
public class RoutingDirectExchange {
    public static final String info = "info";

    public static final String error = "error";

    public static final String exchange4 = "exchange4";

    @Bean
    public Queue info() {
        return new Queue(info);
    }

    @Bean
    public Queue error() {
        return new Queue(error);
    }

    @Bean
    public CountDownLatch countDownLatch4() {
        return new CountDownLatch(3);
    }

    @Bean
    public DirectExchange exchange4() {
        return new DirectExchange(exchange4);
    }

    @Bean
    public Binding binding41(DirectExchange exchange4) {
        return BindingBuilder.bind(info()).to(exchange4).with(info);
    }

    @Bean
    public Binding binding42(DirectExchange exchange4) {
        return BindingBuilder.bind(info()).to(exchange4).with(error);
    }

    @Bean
    public Binding binding43(DirectExchange exchange4) {
        return BindingBuilder.bind(error()).to(exchange4).with(error);
    }
}