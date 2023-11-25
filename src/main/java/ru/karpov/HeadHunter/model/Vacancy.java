package ru.karpov.HeadHunter.model;

import javax.persistence.*;

@Entity
@Table(name="vacancy")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    private Integer money;
    private String metro_station;
    private String experience;
    private String date;
    private String languages;
    private String companyName;
    private String town;

    public String getTown() {
        return town;
    }

    public void setTown(final String town) {
        this.town = town;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(final String languages) {
        this.languages = languages;
    }

    public void setExperience(final String experience) {
        this.experience = experience;
    }

    public String getExperience() {
        return experience;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(final Integer money) {
        this.money = money;
    }

    public String getMetro_station() {
        return metro_station;
    }

    public void setMetro_station(final String metro_station) {
        this.metro_station = metro_station;
    }
}
