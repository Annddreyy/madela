package dev.madela.hr_bot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SurveyController {

    @GetMapping("/surveys")
    public String surveys() {
        return "surveys";
    }
}
