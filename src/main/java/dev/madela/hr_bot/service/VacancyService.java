package dev.madela.hr_bot.service;

import dev.madela.hr_bot.model.Vacancy;
import dev.madela.hr_bot.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacancyService {

    @Autowired
    private VacancyRepository vacancyRepository;

    public List<Vacancy> findAll() {
        return vacancyRepository.findAll();
    }
}

