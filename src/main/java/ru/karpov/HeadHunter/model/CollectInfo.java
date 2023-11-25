package ru.karpov.HeadHunter.model;

public class CollectInfo {
    private String town;
    private Integer firstPage;
    private Integer lastPage;

    public CollectInfo(final String town, final Integer firstPage, final Integer lastPage) {
        this.town = town;
        this.firstPage = firstPage;
        this.lastPage = lastPage;
    }

    public String getTown() {
        return town;
    }

    public void setTown(final String town) {
        this.town = town;
    }

    public Integer getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(final Integer firstPage) {
        this.firstPage = firstPage;
    }

    public Integer getLastPage() {
        return lastPage;
    }

    public void setLastPage(final Integer lastPage) {
        this.lastPage = lastPage;
    }
}
