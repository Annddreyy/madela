package dev.madela.hr_bot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<String> vacancies = Arrays.asList("Java Developer", "QA Engineer", "DevOps Engineer");
        List<String> reports = Arrays.asList("Employee Satisfaction Survey", "Performance Review");

        model.addAttribute("vacancies", vacancies);
        model.addAttribute("reports", reports);

        return "dashboard";
    }
}
