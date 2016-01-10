package daggerok.messaging.rabbit.messaging.prc;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RemoteProcedureCalls {
    public static final String queue6 = "queue6";

    @Bean
    public Queue queue6() {
        return new Queue(queue6);
    }
}