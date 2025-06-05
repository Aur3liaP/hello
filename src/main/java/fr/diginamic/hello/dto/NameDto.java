package fr.diginamic.hello.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NameDto {

    private String common;

    private String official;

    public NameDto() {}

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public String getOfficial() {
        return official;
    }

    public void setOfficial(String official) {
        this.official = official;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(common);
        sb.append(" (").append(official);
        sb.append(")");
        return sb.toString();
    }
}
