package ru.karpov.HeadHunter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.karpov.HeadHunter.model.KeyValue;
import ru.karpov.HeadHunter.model.Vacancy;
import ru.karpov.HeadHunter.repo.VacancyRepo;
import ru.karpov.HeadHunter.service.AnalyseDataService;

import java.util.*;

@Service
public class AnalyseDataServiceImpl implements AnalyseDataService {

    private final VacancyRepo vacancyRepo;

    @Autowired
    public AnalyseDataServiceImpl(final VacancyRepo vacancyRepo) {
        this.vacancyRepo = vacancyRepo;
    }

    @Override
    public int getVacancyTown(final String town) {
        return vacancyRepo.getVacancyByTown(town).size();
    }

    @Override
    public Map<String, Integer> getVacancyLanguages() {
        Map<String, Integer> countLanguages = new HashMap<>();
        for(Vacancy vac : vacancyRepo.findAll())
        {
            final String[] languages = vac.getLanguages().split("; ");
            for(String lang: languages)
            {
                if(!lang.isEmpty())
                    countLanguages.merge(lang, 1, Integer::sum);
            }
        }
        return countLanguages;
    }

    @Override
    public KeyValue getVacancyAvgSalary(final String town) {
        int sum = 0;
        final List<Vacancy> vacancies = vacancyRepo.getVacancyByTownAndMoneyIsNotNull(town);
        for(Vacancy vac : vacancies)
        {
            sum += vac.getMoney();
        }
        return new KeyValue(town, sum / vacancies.size());
    }

    @Override
    public KeyValue getVacancyCompany() {
        Map<String, Integer> countCompany = new HashMap<>();
        int max = 0;
        KeyValue company = new KeyValue();
        for(Vacancy vac : vacancyRepo.findAll())
        {
            if(!vac.getCompanyName().equals("no")) {
                if (countCompany.get(vac.getCompanyName()) == null) {
                    countCompany.put(vac.getCompanyName(), 1);
                } else {
                    countCompany.put(vac.getCompanyName(), countCompany.get(vac.getCompanyName()) + 1);
                    if (countCompany.get(vac.getCompanyName()) > max) {
                        max = countCompany.get(vac.getCompanyName());
                        company.setValue(countCompany.get(vac.getCompanyName()));
                        company.setKey(vac.getCompanyName());
                    }
                }
            }
        }
        return company;
    }

    @Override
    public Map<String, Integer> getVacancyAvgSalaryLanguage(final String town) {
        Map<String, Integer> countLanguages = new HashMap<>();
        Map<String, Integer> sumLanguages = new HashMap<>();
        final List<Vacancy> vacancies = vacancyRepo.getVacancyByTownAndMoneyIsNotNull(town);
        for(Vacancy vac : vacancies) {
            final String[] languages = vac.getLanguages().split("; ");
            for (String lang : languages) {
                if (countLanguages.get(lang) == null && !lang.isEmpty()) {
                    countLanguages.put(lang, 1);
                    sumLanguages.put(lang, vac.getMoney());
                } else if(!lang.isEmpty()) {
                    countLanguages.put(lang, countLanguages.get(lang) + 1);
                    sumLanguages.put(lang, sumLanguages.get(lang) + vac.getMoney());
                }
            }
        }
        for (String key: countLanguages.keySet())
        {
            sumLanguages.put(key, sumLanguages.get(key) / countLanguages.get(key));
        }
        return sumLanguages;
    }

    @Override
    public Map<String, Integer> getVacancyMetro() {
        Map<String, Integer> countMetro = new HashMap<>();
        for(Vacancy vac : vacancyRepo.getVacancyByTown("Санкт-Петербург"))
        {
            if(!vac.getMetro_station().equals("no"))
                countMetro.merge(vac.getMetro_station().replaceAll(" ", ""), 1, Integer::sum);
        }
        List<Map.Entry<String, Integer>> list = new LinkedList<>(countMetro.entrySet());
        list.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));
        Map<String, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list)
        {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    @Override
    public long getVacancyCount() {
        return vacancyRepo.findAll().size();
    }
}
