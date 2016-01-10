package daggerok.messaging.rabbit.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexCtrl {
    @RequestMapping("/")
    public String sendMessage(Model model) {
        return "index";
    }
}