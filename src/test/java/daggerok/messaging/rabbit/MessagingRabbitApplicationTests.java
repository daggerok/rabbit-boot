package daggerok.messaging.rabbit;

import daggerok.messaging.rabbit.messaging.Sender1;
import daggerok.messaging.rabbit.messaging.Sender2;
import daggerok.messaging.rabbit.messaging.Sender3;
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

    @Test
    public void testRabbitCfg1() throws Exception {
        sender1.send("first!");
        countDownLatch1.await(5, TimeUnit.SECONDS);
        // verify such output:
        // d.messaging.rabbit.messaging.Receiver1   : ███ process1: first!
    }

    @Test
    public void testRabbitCfg2() throws Exception {
        sender2.send("and one!");
        sender2.send("and two!");
        countDownLatch2.await(5, TimeUnit.SECONDS);
        // verify such output:
        // d.messaging.rabbit.messaging.Receiver2   : ███ process22: AND TWO!
        // d.messaging.rabbit.messaging.Receiver2   : ███ process21: and one!
    }

    @Test
    public void testRabbitCfg3() throws Exception {
        sender3.send("1");
        sender3.send("2-2");
        sender3.send("3-3-3");
        countDownLatch3.await(5, TimeUnit.SECONDS);
        // verify such output:
        // d.messaging.rabbit.messaging.Receiver3   : ███ process31: 1_
        // d.messaging.rabbit.messaging.Receiver3   : ███ process32: 1__
        // d.messaging.rabbit.messaging.Receiver3   : ███ process31: 2-2_
        // d.messaging.rabbit.messaging.Receiver3   : ███ process32: 2-2__
        // d.messaging.rabbit.messaging.Receiver3   : ███ process31: 3-3-3_
        // d.messaging.rabbit.messaging.Receiver3   : ███ process32: 3-3-3__
    }

    @Test
    public void contextLoads() {
        assertNotNull("null applicationContext", applicationContext);

        assertTrue("connectionFactory bean wasn't found", applicationContext.containsBean("connectionFactory"));

        assertTrue("amqpAdmin bean wasn't found", applicationContext.containsBean("amqpAdmin"));

        assertTrue("rabbitTemplate bean wasn't found", applicationContext.containsBean("rabbitTemplate"));

        assertTrue("rabbitCfg1 bean wasn't found", applicationContext.containsBean("rabbitCfg1"));
        assertTrue("rabbitCfg2 bean wasn't found", applicationContext.containsBean("rabbitCfg2"));
        assertTrue("rabbitCfg3 bean wasn't found", applicationContext.containsBean("rabbitCfg3"));

        assertTrue("queue1 bean wasn't found", applicationContext.containsBean("queue1"));
        assertTrue("queue2 bean wasn't found", applicationContext.containsBean("queue2"));
        assertTrue("queue31 bean wasn't found", applicationContext.containsBean("queue31"));
        assertTrue("queue32 bean wasn't found", applicationContext.containsBean("queue32"));

        assertTrue("countDownLatch1 bean wasn't found", applicationContext.containsBean("countDownLatch1"));
        assertTrue("countDownLatch2 bean wasn't found", applicationContext.containsBean("countDownLatch2"));
        assertTrue("countDownLatch3 bean wasn't found", applicationContext.containsBean("countDownLatch3"));

        assertTrue("exchange3 bean wasn't found", applicationContext.containsBean("exchange3"));

        assertTrue("binding31 bean wasn't found", applicationContext.containsBean("binding31"));
        assertTrue("binding32 bean wasn't found", applicationContext.containsBean("binding32"));

        assertTrue("indexCtrl bean wasn't found", applicationContext.containsBean("indexCtrl"));
        assertTrue("noErrorCtrl bean wasn't found", applicationContext.containsBean("noErrorCtrl"));

        assertTrue("messageCtrl bean wasn't found", applicationContext.containsBean("messageCtrl"));

        assertTrue("receiver1 bean wasn't found", applicationContext.containsBean("receiver1"));
        assertTrue("receiver2 bean wasn't found", applicationContext.containsBean("receiver2"));
        assertTrue("receiver3 bean wasn't found", applicationContext.containsBean("receiver2"));

        assertTrue("sender1 bean wasn't found", applicationContext.containsBean("sender1"));
        assertTrue("sender2 bean wasn't found", applicationContext.containsBean("sender2"));
        assertTrue("sender3 bean wasn't found", applicationContext.containsBean("sender2"));

        assertTrue("messagingRabbitApplication bean wasn't found",
                applicationContext.containsBean("messagingRabbitApplication"));
    }
}