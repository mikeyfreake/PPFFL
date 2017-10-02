package com.ppffl.service.dto;


import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the Season entity.
 */
public class SeasonDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer year;

    private Long leagueId;

    private String leagueName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(Long leagueId) {
        this.leagueId = leagueId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SeasonDTO seasonDTO = (SeasonDTO) o;
        if(seasonDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seasonDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeasonDTO{" +
            "id=" + getId() +
            ", year='" + getYear() + "'" +
            "}";
    }
}
