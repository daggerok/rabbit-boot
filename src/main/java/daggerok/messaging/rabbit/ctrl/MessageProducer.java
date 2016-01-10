package daggerok.messaging.rabbit.ctrl;

import daggerok.messaging.rabbit.messaging.justmessage.Sender1;
import daggerok.messaging.rabbit.messaging.routing.Sender4;
import daggerok.messaging.rabbit.messaging.workqueues.Sender2;
import daggerok.messaging.rabbit.messaging.publishsubscribe.Sender3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/message")
public class MessageProducer {
    @Autowired Sender1 sender;

    @Autowired Sender2 sender2;

    @Autowired Sender3 sender3;

    @Autowired Sender4 sender4;

    @RequestMapping("/send")
    public void sendMessage(String message, HttpServletRequest request, HttpServletResponse response) throws IOException {
        sender.send(message);
        sender2.send(message);
        sender3.send(message);
        sender4.send(message);
        response.sendRedirect(request.getContextPath().concat("/"));
    }
}