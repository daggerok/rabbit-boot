package daggerok.messaging.rabbit;

import daggerok.messaging.rabbit.messaging.prc.RpcSenderReceiver;
import daggerok.messaging.rabbit.messaging.pubsubs.Publisher;
import daggerok.messaging.rabbit.messaging.routing.RoutingSender;
import daggerok.messaging.rabbit.messaging.simple.SimpleSender;
import daggerok.messaging.rabbit.messaging.topics.TopicSender;
import daggerok.messaging.rabbit.messaging.workqueues.WorkerQueueSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MessagingRabbitApplication.class)
public class MessagingRabbitApplicationTests {
    @Autowired ApplicationContext applicationContext;

    @Test
    public void testContext() {
        assertNotNull("null applicationContext", applicationContext);

        Arrays.asList(
                "commonConfig", "connectionFactory", "amqpAdmin", "rabbitTemplate",

                "simpleSendReceiveConfig", "workerQueueConfig", "publisherSubscriberConfig", "routingConfig",
                "topicsExchangeConfig", "remoteProcedureCallsConfig",

                "simpleQueue", "workerQueue", "dashQueue", "underscoreQueue", "info", "error", "noColorQueue",
                "colorQueue", "rpcQueue",

                "simpleLatch", "workerLatch", "subscriberLatch", "routingLatch", "topicsLatch",

                "publishExchange", "routingExchange", "topicsExchange", "dashToToPublishExchange",
                "underscoreToPublishExchange",

                "infoWithInfo", "infoWithError", "errorWithError", "noColorWithNoColor", "colorWithNoColor",
                "colorWithColor",

                "simpleReceiver", "workerQueueReceiver", "subscriber", "routingReceiver", "topicReceiver",
                "rpcProcessor",

                "simpleSender", "workerQueueSender", "publisher", "routingSender", "topicSender", "rpcSenderReceiver",

                "indexCtrl", "errorIndexRedirectHandler", "messagingService",

                "messagingRabbitApplication"
        ).stream().forEach(beanName ->
                assertTrue(String.format("%s bean wasn't found", beanName), applicationContext.containsBean(beanName)));
    }

    @Autowired SimpleSender simpleSender;
    @Resource(name = "simpleLatch") CountDownLatch countDownLatch1;

    @Autowired
    WorkerQueueSender workerQueueSender;
    @Resource(name = "workerLatch") CountDownLatch countDownLatch2;

    @Autowired Publisher publisher;
    @Resource(name = "subscriberLatch") CountDownLatch countDownLatch3;

    @Autowired RoutingSender routingSender;
    @Resource(name = "routingLatch") CountDownLatch countDownLatch4;

    @Autowired TopicSender topicSender;
    @Resource(name = "topicsLatch") CountDownLatch countDownLatch5;

    @Autowired RpcSenderReceiver rpcSenderReceiver;

    @Test
    public void testSimpleSendReceive() throws Exception {
        simpleSender.send("first!");
        countDownLatch1.await(5, TimeUnit.SECONDS);
        // verify such output:
        // ... d.messaging.rabbit.messaging.SimpleReceiver   : ███ process: first!
    }

    @Test
    public void testWorkersQueue() throws Exception {
        workerQueueSender.send("one");
        workerQueueSender.send("two");
        countDownLatch2.await(5, TimeUnit.SECONDS);
        // verify such output:
        // ... d.messaging.rabbit.messaging.WorkerQueueReceiver   : ███ processWorker2: ONE
        // ... d.messaging.rabbit.messaging.WorkerQueueReceiver   : ███ processWorker1: two
    }

    @Test
    public void testPublisherSubscriber() throws Exception {
        publisher.send("1");
        publisher.send("2");
        publisher.send("3");
        countDownLatch3.await(5, TimeUnit.SECONDS);
        // verify such output:
        // ... d.messaging.rabbit.messaging.Subscriber   : ███ processDash: -1-
        // ... d.messaging.rabbit.messaging.Subscriber   : ███ processUnderscore: _1_
        // ... d.messaging.rabbit.messaging.Subscriber   : ███ processDash: -2-
        // ... d.messaging.rabbit.messaging.Subscriber   : ███ processUnderscore: _2_
        // ... d.messaging.rabbit.messaging.Subscriber   : ███ processDash: -3-
        // ... d.messaging.rabbit.messaging.Subscriber   : ███ processUnderscore: _3_
    }

    @Test
    public void testRouting() throws Exception {
        routingSender.send("log very bad error message!");
        routingSender.send("log some nice info msg");
        routingSender.send("some not processed message");
        countDownLatch4.await(5, TimeUnit.SECONDS);
        // verify such output:
        // ... d.m.rabbit.messaging.routing.RoutingReceiver   : ███ info: log very bad error message!
        // ... d.m.rabbit.messaging.routing.RoutingReceiver   : ███ error: .l..o..g.. ..v..e..r..y.. ..b..a..d.. ..e..r..r..o..r.. ..m..e..s..s..a..g..e..!.
        // ... d.m.rabbit.messaging.routing.RoutingReceiver   : ███ info: log some nice info msg
    }

    @Test
    public void testTopics() throws Exception {
        Stream.of("rgb means red blue and green".split(" ")).forEach(topicSender::send);
        Stream.of("black and white in all of us".split(" ")).forEach(topicSender::send);
        Stream.of("the beatles - yellow submarine".split(" ")).forEach(topicSender::send);
        countDownLatch5.await(5, TimeUnit.SECONDS);
        // verify such output:
        // ... d.m.rabbit.messaging.topics.TopicReceiver    : ### colorQueue: r#e#d
        // ... d.m.rabbit.messaging.topics.TopicReceiver    : ███ noColorQueue: b█l█a█c█k
        // ... d.m.rabbit.messaging.topics.TopicReceiver    : ### colorQueue: b#l#u#e
        // ... d.m.rabbit.messaging.topics.TopicReceiver    : ███ noColorQueue: w█h█i█t█e
        // ... d.m.rabbit.messaging.topics.TopicReceiver    : ### colorQueue: g#r#e#e#n
        // ... d.m.rabbit.messaging.topics.TopicReceiver    : ### colorQueue: b#l#a#c#k
        // ... d.m.rabbit.messaging.topics.TopicReceiver    : ### colorQueue: w#h#i#t#e
        // ... d.m.rabbit.messaging.topics.TopicReceiver    : ### colorQueue: y#e#l#l#o#w
    }

    @Test
    public void testRemoteProcedureCalls() throws Exception {
        String result = rpcSenderReceiver.send("one");

        assertNotNull("null result", result);
        assertEquals("procedure response: ███ rpcQueue: O█N█E", result);

        result = rpcSenderReceiver.send("and two");

        assertNotNull("null result", result);
        assertEquals("procedure response: ███ rpcQueue: A█N█D█ █T█W█O" , result);

        result = rpcSenderReceiver.send("and three and four");

        assertNotNull("null result", result);
        assertEquals("procedure response: ███ rpcQueue: A█N█D█ █T█H█R█E█E█ █A█N█D█ █F█O█U█R" , result);
    }
}