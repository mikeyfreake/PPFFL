package com.ppffl.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ppffl.domain.enumeration.FantasyProvider;

/**
 * A League.
 */
@Entity
@Table(name = "league")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class League implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "imported_from")
    private FantasyProvider importedFrom;

    @Size(max = 8000)
    @Column(name = "constitution", length = 8000)
    private String constitution;

    @OneToMany(mappedBy = "league")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Season> seasons = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public League name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FantasyProvider getImportedFrom() {
        return importedFrom;
    }

    public League importedFrom(FantasyProvider importedFrom) {
        this.importedFrom = importedFrom;
        return this;
    }

    public void setImportedFrom(FantasyProvider importedFrom) {
        this.importedFrom = importedFrom;
    }

    public String getConstitution() {
        return constitution;
    }

    public League constitution(String constitution) {
        this.constitution = constitution;
        return this;
    }

    public void setConstitution(String constitution) {
        this.constitution = constitution;
    }

    public Set<Season> getSeasons() {
        return seasons;
    }

    public League seasons(Set<Season> seasons) {
        this.seasons = seasons;
        return this;
    }

    public League addSeason(Season season) {
        this.seasons.add(season);
        season.setLeague(this);
        return this;
    }

    public League removeSeason(Season season) {
        this.seasons.remove(season);
        season.setLeague(null);
        return this;
    }

    public void setSeasons(Set<Season> seasons) {
        this.seasons = seasons;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        League league = (League) o;
        if (league.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), league.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "League{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", importedFrom='" + getImportedFrom() + "'" +
            ", constitution='" + getConstitution() + "'" +
            "}";
    }
}
