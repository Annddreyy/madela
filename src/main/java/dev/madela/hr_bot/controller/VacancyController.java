package dev.madela.hr_bot.controller;

import dev.madela.hr_bot.model.Vacancy;
import dev.madela.hr_bot.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VacancyController {

    @Autowired
    private VacancyService vacancyService;

    @PreAuthorize("hasRole('HR')")
    @GetMapping("/vacancies")
    public List<Vacancy> getAllVacancies() {
        return vacancyService.findAll();
    }
}

