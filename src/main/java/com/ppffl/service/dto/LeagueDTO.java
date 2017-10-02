package com.ppffl.service.dto;


import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ppffl.domain.enumeration.FantasyProvider;

/**
 * A DTO for the League entity.
 */
public class LeagueDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    private FantasyProvider importedFrom;

    @Size(max = 8000)
    private String constitution;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FantasyProvider getImportedFrom() {
        return importedFrom;
    }

    public void setImportedFrom(FantasyProvider importedFrom) {
        this.importedFrom = importedFrom;
    }

    public String getConstitution() {
        return constitution;
    }

    public void setConstitution(String constitution) {
        this.constitution = constitution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LeagueDTO leagueDTO = (LeagueDTO) o;
        if(leagueDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), leagueDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LeagueDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", importedFrom='" + getImportedFrom() + "'" +
            ", constitution='" + getConstitution() + "'" +
            "}";
    }
}
