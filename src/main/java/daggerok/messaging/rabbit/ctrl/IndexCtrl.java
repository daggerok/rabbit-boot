package daggerok.messaging.rabbit.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexCtrl {
    @RequestMapping("/")
    public String sendMessage(Model model, HttpServletRequest request) {
        model.addAttribute("request", request);
        return "index";
    }
}