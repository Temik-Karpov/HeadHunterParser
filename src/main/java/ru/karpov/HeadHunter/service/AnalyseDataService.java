package ru.karpov.HeadHunter.service;

import ru.karpov.HeadHunter.model.KeyValue;

import java.util.Map;

public interface AnalyseDataService {
    int getVacancyTown(final String town);
    Map<String, Integer> getVacancyLanguages();
    KeyValue getVacancyAvgSalary(final String town);
    KeyValue getVacancyCompany();
    Map<String, Integer> getVacancyAvgSalaryLanguage(final String town);
    Map<String, Integer> getVacancyMetro();
    long getVacancyCount();
}
