package daggerok.messaging.rabbit;

import daggerok.messaging.rabbit.messaging.Sender;
import daggerok.messaging.rabbit.messaging.Sender2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MessagingRabbitApplication.class)
public class MessagingRabbitApplicationTests {
    @Autowired ApplicationContext applicationContext;

    @Autowired Sender sender;

    @Autowired Sender2 sender2;

    @Autowired CountDownLatch countDownLatch, countDownLatch2;

    @Test
    public void contextLoads() {
        assertNotNull("null applicationContext", applicationContext);

        assertTrue("simpleMessageListenerContainer bean wasn't found",
                applicationContext.containsBean("simpleMessageListenerContainer"));
        assertTrue("messageListener bean wasn't found", applicationContext.containsBean("messageListener"));

        assertTrue("connectionFactory2 bean wasn't found", applicationContext.containsBean("connectionFactory2"));
        assertTrue("amqpAdmin2 bean wasn't found", applicationContext.containsBean("amqpAdmin2"));
        assertTrue("rabbitTemplate2 bean wasn't found", applicationContext.containsBean("rabbitTemplate2"));

        assertTrue("rabbitCfg bean wasn't found", applicationContext.containsBean("rabbitCfg"));
        assertTrue("rabbitCfg2 bean wasn't found", applicationContext.containsBean("rabbitCfg2"));

        assertTrue("queue bean wasn't found", applicationContext.containsBean("queue"));
        assertTrue("queue2 bean wasn't found", applicationContext.containsBean("queue2"));

        assertTrue("countDownLatch bean wasn't found", applicationContext.containsBean("countDownLatch"));
        assertTrue("countDownLatch2 bean wasn't found", applicationContext.containsBean("countDownLatch2"));

        assertTrue("cfg bean wasn't found", applicationContext.containsBean("cfg"));

        assertTrue("indexCtrl bean wasn't found", applicationContext.containsBean("indexCtrl"));

        assertTrue("messageCtrl bean wasn't found", applicationContext.containsBean("messageCtrl"));

        assertTrue("receiver bean wasn't found", applicationContext.containsBean("receiver"));
        assertTrue("receiver2 bean wasn't found", applicationContext.containsBean("receiver2"));

        assertTrue("sender bean wasn't found", applicationContext.containsBean("sender"));
        assertTrue("sender2 bean wasn't found", applicationContext.containsBean("sender2"));

        assertTrue("messagingRabbitApplication bean wasn't found",
                applicationContext.containsBean("messagingRabbitApplication"));
    }

    @Test
    public void testRabbitCfg() throws Exception {
        sender.send("hello, folks!");
        countDownLatch.await(5, TimeUnit.SECONDS);
        // verify such output:
        // d.messaging.rabbit.messaging.Receiver    : ███ receive: content: hello, folks!
    }

    @Test
    public void testRabbitCfg2() throws Exception {
        sender2.send("and one!");
        sender2.send("and two!");
        countDownLatch2.await(5, TimeUnit.SECONDS);
        // verify such output:
        // ... d.messaging.rabbit.messaging.Receiver2   : ███ process2: CONTENT: AND ONE!
        // ... d.messaging.rabbit.messaging.Receiver2   : ███ process1: content: and two!
    }
}