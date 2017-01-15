package daggerok.messaging.rabbit.rest;

import daggerok.messaging.rabbit.messaging.prc.RpcSenderReceiver;
import daggerok.messaging.rabbit.messaging.pubsubs.Publisher;
import daggerok.messaging.rabbit.messaging.routing.RoutingSender;
import daggerok.messaging.rabbit.messaging.simple.SimpleSender;
import daggerok.messaging.rabbit.messaging.topics.TopicSender;
import daggerok.messaging.rabbit.messaging.workqueues.WorkerQueueSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessagingService {

    final Publisher publisher;
    final TopicSender topicSender;
    final SimpleSender simpleSender;
    final RoutingSender routingSender;
    final WorkerQueueSender workerQueueSender;
    final RpcSenderReceiver rpcSenderReceiver;

    @RequestMapping("/message/send")
    public ResponseEntity<String> send(final String message) {

        log.info("processing new message: {}", message);

        if (null != message && !"".equals(message)) {
            simpleSender.send(message);
            workerQueueSender.send(message);
            publisher.send(message);
            routingSender.send(message);
            topicSender.send(message);
            rpcSenderReceiver.send(message);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
