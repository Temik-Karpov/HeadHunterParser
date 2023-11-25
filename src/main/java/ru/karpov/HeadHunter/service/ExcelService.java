package ru.karpov.HeadHunter.service;
import java.io.IOException;
import java.util.Map;

public interface ExcelService {
    void addVacancyTown(final Integer countSaint, final Integer countMoscow) throws IOException;
    void addVacancyLanguages(final Map<String, Integer> countLanguages) throws IOException;
    void addVacancyAvgSalary(final Integer avg1, final String town1, final Integer avg2, final String town2) throws IOException;
    void addVacancyCompany(final Integer max, final String company) throws IOException;
    void addVacancyAvgSalaryLanguage(final Map<String, Integer> sumLanguages1, final Map<String, Integer> sumLanguages2) throws IOException;
    void addVacancyMetro(final Map<String, Integer> countMetro) throws IOException;
    void addVacancyCount(final long count) throws IOException;
}
