package daggerok.messaging.rabbit.rest;

import daggerok.messaging.rabbit.messaging.prc.RpcSenderReceiver;
import daggerok.messaging.rabbit.messaging.pubsubs.Publisher;
import daggerok.messaging.rabbit.messaging.routing.RoutingSender;
import daggerok.messaging.rabbit.messaging.simple.SimpleSender;
import daggerok.messaging.rabbit.messaging.topics.TopicSender;
import daggerok.messaging.rabbit.messaging.workqueues.WorkerQueueSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@Controller
@RequestMapping("/message")
public class MessagingService {
    private static final Logger logger = Logger.getLogger(MessagingService.class.getName());

    @Autowired SimpleSender simpleSender;

    @Autowired
    WorkerQueueSender workerQueueSender;

    @Autowired Publisher publisher;

    @Autowired RoutingSender routingSender;

    @Autowired TopicSender topicSender;

    @Autowired RpcSenderReceiver rpcSenderReceiver;

    @RequestMapping("/send")
    public void send(String message, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("=== processing new message ===");

        if (null != message && !"".equals(message)) {
            simpleSender.send(message);
            workerQueueSender.send(message);
            publisher.send(message);
            routingSender.send(message);
            topicSender.send(message);
            rpcSenderReceiver.send(message);
        }

        response.sendRedirect(request.getContextPath().concat("/"));
    }
}