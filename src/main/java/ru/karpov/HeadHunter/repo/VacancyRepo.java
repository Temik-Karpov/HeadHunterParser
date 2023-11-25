package ru.karpov.HeadHunter.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.karpov.HeadHunter.model.Vacancy;

import java.util.List;

@Repository
public interface VacancyRepo extends JpaRepository<Vacancy, Long> {
    List<Vacancy> getVacancyByTown(final String townName);
    List<Vacancy> getVacancyByTownAndMoneyIsNotNull(final String townName);
}
