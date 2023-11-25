package ru.karpov.HeadHunter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.karpov.HeadHunter.service.AnalyseDataService;
import ru.karpov.HeadHunter.service.impl.ExcelServiceImpl;

import java.io.IOException;

@Controller
public class AnalyseController {

    private final AnalyseDataService dataService;

    private final ExcelServiceImpl excelService;

    @Autowired
    public AnalyseController(final AnalyseDataService dataService, final ExcelServiceImpl excelService)
    {
        this.dataService = dataService;
        this.excelService = excelService;
    }

    @GetMapping("/analyse/excelOutput")
    public String excelOutput() throws IOException {
        excelService.addVacancyCount(dataService.getVacancyCount());

        excelService.addVacancyTown(dataService.getVacancyTown("Санкт-Петербург"), dataService.getVacancyTown("Москва"));

        excelService.addVacancyAvgSalary(dataService.getVacancyAvgSalary("Санкт-Петербург").getValue(),
                dataService.getVacancyAvgSalary("Санкт-Петербург").getKey(),
                dataService.getVacancyAvgSalary("Москва").getValue(),
                dataService.getVacancyAvgSalary("Москва").getKey());

        excelService.addVacancyCompany(dataService.getVacancyCompany().getValue(),
                dataService.getVacancyCompany().getKey());

        excelService.addVacancyLanguages(dataService.getVacancyLanguages());

        excelService.addVacancyAvgSalaryLanguage(dataService.getVacancyAvgSalaryLanguage("Санкт-Петербург"),
                dataService.getVacancyAvgSalaryLanguage("Москва"));

        excelService.addVacancyMetro(dataService.getVacancyMetro());

        return "main";
    }

    @PostMapping("/analyse/vacancyTown")
    public String vacancyTown(@RequestParam("town") final String town,
                                    final Model model) {
        model.addAttribute("count", dataService.getVacancyTown(town));
        return "vacancyTown";
    }

    @GetMapping("/analyse/vacancyLanguages")
    public String vacancyLanguages(final Model model) {
        model.addAttribute("map", dataService.getVacancyLanguages());
        return "vacancyLanguage";
    }

    @PostMapping("/analyse/vacancyAvgSalary")
    public String vacancyAvgSalary(@RequestParam("town") final String town,
                                   final Model model) {

        model.addAttribute("town", dataService.getVacancyAvgSalary(town).getKey());
        model.addAttribute("avg", dataService.getVacancyAvgSalary(town).getValue());

        return "vacancyAvgSalary";
    }

    @GetMapping("/analyse/vacancyCompany")
    public String vacancyCompany(final Model model) {

        model.addAttribute("max", dataService.getVacancyCompany().getValue());
        model.addAttribute("company", dataService.getVacancyCompany().getKey());
        return "vacancyCompany";
    }

    @PostMapping("/analyse/vacancyAvgSalary/language")
    public String vacancyAvgSalaryLanguage(@RequestParam("town") final String town,
                                           final Model model) {

        model.addAttribute("map", dataService.getVacancyAvgSalaryLanguage(town));
        return "vacancyLanguage";
    }

    @GetMapping("/analyse/vacancyMetro")
    public String vacancyMetro(final Model model)
    {
        model.addAttribute("map", dataService.getVacancyMetro());
        return "vacancyLanguage";
    }

    @GetMapping("/analyse/vacancyCount")
    public String vacancyCount(final Model model)
    {
        model.addAttribute("countOfVacancy", dataService.getVacancyCount());
        return "main";
    }
}
