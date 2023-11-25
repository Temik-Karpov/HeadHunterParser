package ru.karpov.HeadHunter.controller;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.karpov.HeadHunter.model.CollectInfo;
import ru.karpov.HeadHunter.model.Language;
import ru.karpov.HeadHunter.model.MonthRus;
import ru.karpov.HeadHunter.model.Vacancy;
import ru.karpov.HeadHunter.repo.VacancyRepo;

import java.io.IOException;


@Controller
public class CollectController {

    private final VacancyRepo vacancyRepo;

    @Autowired
    public CollectController(final VacancyRepo vacancyRepo)
    {
        this.vacancyRepo = vacancyRepo;
    }

    @GetMapping("/")
    public String getMain(final Model model)
    {
        model.addAttribute("countOfVacancy", vacancyRepo.findAll().size());
        return "main";
    }

    @PostMapping("/calculate")
    public String start(@ModelAttribute("CollectInfo") final CollectInfo info,
                        final Model model) throws IOException {
        int n = info.getLastPage();
        String hhUrl;

        long sumTime = 0;
        int countTime = 1;

        if(info.getTown().equals("Москва"))
            hhUrl = "https://hh.ru/vacancies/programmist/za_sutki?page=";
        else
            hhUrl = "https://spb.hh.ru/vacancies/programmist/za_sutki?page=";

        for(int i = info.getFirstPage(); i < n; i++) {

            long startTime = System.nanoTime();

            Connection.Response response = Jsoup.connect(hhUrl + i)
                    .method(Connection.Method.GET)
                    .execute();

            Document doc = response.parse();

            Elements listNews = doc.select(".vacancy-serp-item__layout");

            for (Element element : listNews) {

                Vacancy newVacancy = new Vacancy();
                newVacancy.setTitle(element.select(".serp-item__title").text());

                Connection.Response response2 = Jsoup.connect(element.select(".serp-item__title").attr("href"))
                       .method(Connection.Method.GET)
                        .execute();
               Document doc2 = response2.parse();

               newVacancy.setCompanyName(doc2.select(".vacancy-company-name").text().length() != 0 ?
                       doc2.select(".vacancy-company-name").text() : "no");

               newVacancy.setTown(info.getTown());

               Elements listNews2 = doc2.select(".g-user-content");
               String languages = "";

               for (Element element2 : listNews2) {

                   for (Language lan : Language.values())
                   {
                       if(element2.text().toLowerCase().contains(lan.name()) && !languages.contains(lan.name()))
                       {
                           languages += lan.name() + "; ";
                       }
                       if(element2.text().toLowerCase().contains("c++") && !languages.contains("c++"))
                       {
                           languages += "c++; ";
                       }
                       if(element2.text().toLowerCase().contains("1с") && !languages.contains("1c"))
                       {
                           languages += "1c; ";
                       }
                   }
               }

               newVacancy.setLanguages(languages);

               listNews2 = doc2.select(".vacancy-creation-time-redesigned");

               for (Element element2 : listNews2) {
                    final String date = parseDate(element2.select("span").text());
                    newVacancy.setDate(date);
                }

                newVacancy.setExperience(parseExperience(element));

                String money = element.select(".bloko-header-section-2").text().length() == 0 ? "0" :
                        element.select(".bloko-header-section-2").text();

                newVacancy.setMoney(parseMoneyString(money));

                newVacancy.setMetro_station(element.select(".metro-station").text().length() == 0 ? "no" :
                        element.select(".metro-station").text());

                vacancyRepo.save(newVacancy);

                countTime++;
                long endTime = System.nanoTime();
                sumTime += (endTime - startTime) / 1000000;
            }

        }

        model.addAttribute("countOfVacancy", vacancyRepo.count());
        model.addAttribute("time", sumTime / countTime);
        model.addAttribute("count", countTime - 1);
        return "main";
    }

    private String parseExperience(final Element element)
    {
        for(Element experience : element.select(".bloko-text"))
        {
            if(experience.text().contains("Опыт") && experience.text().length() < 30)
            {
                return experience.text();
            }
        }
        return "Без опыта";
    }

    private Integer parseMoneyString(final String money)
    {
        String newMoney = money.replaceAll(" ", "");
        final boolean z = newMoney.contains("₽");
        if(money.contains("–"))
        {
            newMoney = newMoney.substring(0, money.indexOf("–") - 2);
        }
        newMoney = newMoney.replaceAll("\\D", "");
        int income = Integer.parseInt (newMoney);
        return z ? income : income * 100;
    }

    private String parseDate(final String str_date)
    {
        String date = "";
        String[] dateInfo = str_date.split(" ");
        date += dateInfo[0] + ".";
        String result = null;
        for (MonthRus month : MonthRus.values()) {
            if (month.name().equalsIgnoreCase(dateInfo[1])) {
                result = month.name();
                break;
            }
        }
        date += MonthRus.valueOf(result).ordinal() + 1 + ".";
        date += dateInfo[2];
        return date;
    }
}
