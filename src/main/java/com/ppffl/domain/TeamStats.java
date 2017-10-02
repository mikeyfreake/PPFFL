package com.ppffl.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TeamStats.
 */
@Entity
@Table(name = "team_stats")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TeamStats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "rank")
    private Integer rank;

    @Column(name = "wins")
    private Integer wins;

    @Column(name = "losses")
    private Integer losses;

    @Column(name = "ties")
    private Integer ties;

    @Column(name = "points_for")
    private Double pointsFor;

    @Column(name = "points_against")
    private Double pointsAgainst;

    @Column(name = "points_for_per_game")
    private Double pointsForPerGame;

    @Column(name = "points_against_per_game")
    private Double pointsAgainstPerGame;

    @Column(name = "points_diff_per_game")
    private Double pointsDiffPerGame;

    @ManyToOne
    private Team team;

    @ManyToOne
    private Season season;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRank() {
        return rank;
    }

    public TeamStats rank(Integer rank) {
        this.rank = rank;
        return this;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getWins() {
        return wins;
    }

    public TeamStats wins(Integer wins) {
        this.wins = wins;
        return this;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLosses() {
        return losses;
    }

    public TeamStats losses(Integer losses) {
        this.losses = losses;
        return this;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public Integer getTies() {
        return ties;
    }

    public TeamStats ties(Integer ties) {
        this.ties = ties;
        return this;
    }

    public void setTies(Integer ties) {
        this.ties = ties;
    }

    public Double getPointsFor() {
        return pointsFor;
    }

    public TeamStats pointsFor(Double pointsFor) {
        this.pointsFor = pointsFor;
        return this;
    }

    public void setPointsFor(Double pointsFor) {
        this.pointsFor = pointsFor;
    }

    public Double getPointsAgainst() {
        return pointsAgainst;
    }

    public TeamStats pointsAgainst(Double pointsAgainst) {
        this.pointsAgainst = pointsAgainst;
        return this;
    }

    public void setPointsAgainst(Double pointsAgainst) {
        this.pointsAgainst = pointsAgainst;
    }

    public Double getPointsForPerGame() {
        return pointsForPerGame;
    }

    public TeamStats pointsForPerGame(Double pointsForPerGame) {
        this.pointsForPerGame = pointsForPerGame;
        return this;
    }

    public void setPointsForPerGame(Double pointsForPerGame) {
        this.pointsForPerGame = pointsForPerGame;
    }

    public Double getPointsAgainstPerGame() {
        return pointsAgainstPerGame;
    }

    public TeamStats pointsAgainstPerGame(Double pointsAgainstPerGame) {
        this.pointsAgainstPerGame = pointsAgainstPerGame;
        return this;
    }

    public void setPointsAgainstPerGame(Double pointsAgainstPerGame) {
        this.pointsAgainstPerGame = pointsAgainstPerGame;
    }

    public Double getPointsDiffPerGame() {
        return pointsDiffPerGame;
    }

    public TeamStats pointsDiffPerGame(Double pointsDiffPerGame) {
        this.pointsDiffPerGame = pointsDiffPerGame;
        return this;
    }

    public void setPointsDiffPerGame(Double pointsDiffPerGame) {
        this.pointsDiffPerGame = pointsDiffPerGame;
    }

    public Team getTeam() {
        return team;
    }

    public TeamStats team(Team team) {
        this.team = team;
        return this;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Season getSeason() {
        return season;
    }

    public TeamStats season(Season season) {
        this.season = season;
        return this;
    }

    public void setSeason(Season season) {
        this.season = season;
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
        TeamStats teamStats = (TeamStats) o;
        if (teamStats.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), teamStats.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TeamStats{" +
            "id=" + getId() +
            ", rank='" + getRank() + "'" +
            ", wins='" + getWins() + "'" +
            ", losses='" + getLosses() + "'" +
            ", ties='" + getTies() + "'" +
            ", pointsFor='" + getPointsFor() + "'" +
            ", pointsAgainst='" + getPointsAgainst() + "'" +
            ", pointsForPerGame='" + getPointsForPerGame() + "'" +
            ", pointsAgainstPerGame='" + getPointsAgainstPerGame() + "'" +
            ", pointsDiffPerGame='" + getPointsDiffPerGame() + "'" +
            "}";
    }
}
