package daggerok.messaging.rabbit.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class ErrorCtrl extends AbstractErrorController implements ErrorController {
    @Value("${server.error.path:${error.path:/error}}")
    String path;

    ErrorAttributes errorAttributes;

    @Autowired
    public ErrorCtrl(ErrorAttributes errorAttributes) {
        super(errorAttributes);
        this.errorAttributes = errorAttributes;
    }

    @Override
    public String getErrorPath() {
        return path;
    }

    @RequestMapping(produces = "text/html")
    public String errorHtml(Model model, HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(getStatus(request).value());
        model.addAllAttributes(getErrorAttributes(request, true));
        model.addAttribute("request", request);
        return "index";
    }

    @RequestMapping
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, true);
        HttpStatus status = getStatus(request);
        return new ResponseEntity<Map<String, Object>>(body, status);
    }
}