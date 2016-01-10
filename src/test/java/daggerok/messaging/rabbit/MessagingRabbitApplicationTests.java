package daggerok.messaging.rabbit;

import daggerok.messaging.rabbit.messaging.justmessage.Sender1;
import daggerok.messaging.rabbit.messaging.routing.Sender4;
import daggerok.messaging.rabbit.messaging.workqueues.Sender2;
import daggerok.messaging.rabbit.messaging.publishsubscribe.Sender3;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MessagingRabbitApplication.class)
public class MessagingRabbitApplicationTests {
    @Autowired ApplicationContext applicationContext;

    @Autowired
    Sender1 sender1;

    @Resource(name = "countDownLatch1") CountDownLatch countDownLatch1;

    @Autowired Sender2 sender2;

    @Resource(name = "countDownLatch2") CountDownLatch countDownLatch2;

    @Autowired Sender3 sender3;

    @Resource(name = "countDownLatch3") CountDownLatch countDownLatch3;

    @Autowired Sender4 sender4;

    @Resource(name = "countDownLatch4") CountDownLatch countDownLatch4;

    @Test
    public void testJustSimpleMessageSendOneToOneReceive() throws Exception {
        sender1.send("first!");
        countDownLatch1.await(5, TimeUnit.SECONDS);
        // verify such output:
        // ... d.messaging.rabbit.messaging.Receiver1   : ███ process1: first!
    }

    @Test
    public void testQueueListenAllWorkersButOnlyOneWorkerPerMessage() throws Exception {
        sender2.send("one");
        sender2.send("two");
        countDownLatch2.await(5, TimeUnit.SECONDS);
        // verify such output:
        // ... d.messaging.rabbit.messaging.Receiver2   : ███ process22: ONE
        // ... d.messaging.rabbit.messaging.Receiver2   : ███ process21: two
    }

    @Test
    public void testAllWorkersProcessAllQueueMessages() throws Exception {
        sender3.send("1");
        sender3.send("2");
        sender3.send("3");
        countDownLatch3.await(5, TimeUnit.SECONDS);
        // verify such output:
        // ... d.messaging.rabbit.messaging.Receiver3   : ███ process31: _1_
        // ... d.messaging.rabbit.messaging.Receiver3   : ███ process32: __1__
        // ... d.messaging.rabbit.messaging.Receiver3   : ███ process31: _2_
        // ... d.messaging.rabbit.messaging.Receiver3   : ███ process32: __2__
        // ... d.messaging.rabbit.messaging.Receiver3   : ███ process31: _3_
        // ... d.messaging.rabbit.messaging.Receiver3   : ███ process32: __3__
    }

    @Test
    public void testRoutingDirectExchange() throws Exception {
        sender4.send("log very bad error message!");
        sender4.send("log some nice info msg");
        sender4.send("some not processed message");
        countDownLatch4.await(5, TimeUnit.SECONDS);
        // verify such output:
        // ... d.m.rabbit.messaging.routing.Receiver4   : ███ info: log very bad error message!
        // ... d.m.rabbit.messaging.routing.Receiver4   : ███ error: .l..o..g.. ..v..e..r..y.. ..b..a..d.. ..e..r..r..o..r.. ..m..e..s..s..a..g..e..!.
        // ... d.m.rabbit.messaging.routing.Receiver4   : ███ info: log some nice info msg
    }

    @Test
    public void contextLoads() {
        assertNotNull("null applicationContext", applicationContext);

        assertTrue("commonCfg bean wasn't found", applicationContext.containsBean("commonCfg"));
        assertTrue("connectionFactory bean wasn't found", applicationContext.containsBean("connectionFactory"));
        assertTrue("amqpAdmin bean wasn't found", applicationContext.containsBean("amqpAdmin"));
        assertTrue("rabbitTemplate bean wasn't found", applicationContext.containsBean("rabbitTemplate"));

        assertTrue("messageProducer bean wasn't found", applicationContext.containsBean("messageProducer"));

        assertTrue("justSimpleMessageSendOneToOneReceive bean wasn't found",
                applicationContext.containsBean("justSimpleMessageSendOneToOneReceive"));
        assertTrue("queueListenAllWorkersButOnlyOneWorkerPerMessage bean wasn't found",
                applicationContext.containsBean("queueListenAllWorkersButOnlyOneWorkerPerMessage"));
        assertTrue("allWorkersProcessAllQueueMessages bean wasn't found",
                applicationContext.containsBean("allWorkersProcessAllQueueMessages"));
        assertTrue("routingDirectExchange bean wasn't found", applicationContext.containsBean("routingDirectExchange"));

        assertTrue("queue1 bean wasn't found", applicationContext.containsBean("queue1"));
        assertTrue("queue2 bean wasn't found", applicationContext.containsBean("queue2"));
        assertTrue("queue31 bean wasn't found", applicationContext.containsBean("queue31"));
        assertTrue("queue32 bean wasn't found", applicationContext.containsBean("queue32"));
        assertTrue("info bean wasn't found", applicationContext.containsBean("info"));
        assertTrue("error bean wasn't found", applicationContext.containsBean("error"));

        assertTrue("countDownLatch1 bean wasn't found", applicationContext.containsBean("countDownLatch1"));
        assertTrue("countDownLatch2 bean wasn't found", applicationContext.containsBean("countDownLatch2"));
        assertTrue("countDownLatch3 bean wasn't found", applicationContext.containsBean("countDownLatch3"));
        assertTrue("countDownLatch4 bean wasn't found", applicationContext.containsBean("countDownLatch4"));

        assertTrue("exchange3 bean wasn't found", applicationContext.containsBean("exchange3"));
        assertTrue("exchange4 bean wasn't found", applicationContext.containsBean("exchange4"));

        assertTrue("binding31 bean wasn't found", applicationContext.containsBean("binding31"));
        assertTrue("binding32 bean wasn't found", applicationContext.containsBean("binding32"));
        assertTrue("binding41 bean wasn't found", applicationContext.containsBean("binding41"));
        assertTrue("binding42 bean wasn't found", applicationContext.containsBean("binding42"));
        assertTrue("binding43 bean wasn't found", applicationContext.containsBean("binding43"));

        assertTrue("indexCtrl bean wasn't found", applicationContext.containsBean("indexCtrl"));
        assertTrue("errorIndexRedirectHandler bean wasn't found",
                applicationContext.containsBean("errorIndexRedirectHandler"));

        assertTrue("receiver1 bean wasn't found", applicationContext.containsBean("receiver1"));
        assertTrue("receiver2 bean wasn't found", applicationContext.containsBean("receiver2"));
        assertTrue("receiver3 bean wasn't found", applicationContext.containsBean("receiver2"));
        assertTrue("receiver4 bean wasn't found", applicationContext.containsBean("receiver4"));

        assertTrue("sender1 bean wasn't found", applicationContext.containsBean("sender1"));
        assertTrue("sender2 bean wasn't found", applicationContext.containsBean("sender2"));
        assertTrue("sender3 bean wasn't found", applicationContext.containsBean("sender2"));
        assertTrue("sender4 bean wasn't found", applicationContext.containsBean("sender4"));

        assertTrue("messagingRabbitApplication bean wasn't found",
                applicationContext.containsBean("messagingRabbitApplication"));
    }
}