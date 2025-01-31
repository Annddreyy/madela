package dev.madela.hr_bot.repository;

import dev.madela.hr_bot.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
}
