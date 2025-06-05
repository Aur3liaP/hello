package fr.diginamic.hello.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaysDto {

    private NameDto name;
    private List<String> capital;
    private Map<String, String> languages;
    private String region;
    private String subregion;
    private Long population;

    public PaysDto() {}

    public NameDto getName() {
        return name;
    }

    public void setName(NameDto name) {
        this.name = name;
    }

    public List<String> getCapital() {
        return capital;
    }

    public void setCapital(List<String> capital) {
        this.capital = capital;
    }

    public Map<String, String> getLanguages() {
        return languages;
    }

    public void setLanguages(Map<String, String> languages) {
        this.languages = languages;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("-");
        sb.append(" ").append(name);
        sb.append(", capital=").append(capital);
        sb.append(", languages=").append(languages);
        sb.append(", ").append(region);
        sb.append(", ").append(subregion);
        sb.append(", population= ").append(population);
        return sb.toString();
    }
}
