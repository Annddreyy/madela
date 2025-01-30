package dev.madela.hr_bot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VacancyController {
    @GetMapping("/vacancies")
    public String showVacancies() {
        return "vacancies";
    }
}