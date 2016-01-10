package daggerok.messaging.rabbit.config.rabbit;

import daggerok.messaging.rabbit.messaging.Receiver;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;

@Configuration
public class RabbitCfg {
    public static final String queue = "queue";

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(Queue queue
            , ConnectionFactory rabbitCnnectionFactory, MessageListener messageListener) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();

        container.setQueueNames(queue.getName());
        container.setMessageListener(messageListener);
        container.setConnectionFactory(rabbitCnnectionFactory);
        return container;
    }

    @Bean
    Queue queue() {
        return new Queue(queue, false);
    }

//    @Bean
//    TopicExchange topicExchange(Queue queue) {
//        return new TopicExchange(queue.getName());
//    }
//
//    @Bean
//    Binding binding(Queue queue, TopicExchange topicExchange) {
//        return BindingBuilder.bind(queue).to(topicExchange).with(queue.getName());
//    }

    @Bean
    public MessageListener messageListener(Receiver receiver) {
        return new MessageListenerAdapter(receiver, receiver.defaultListenerMethod());
    }

    @Bean public CountDownLatch countDownLatch() {
        return new CountDownLatch(1);
    }
}