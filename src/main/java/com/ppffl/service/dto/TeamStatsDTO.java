package com.ppffl.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TeamStats entity.
 */
public class TeamStatsDTO implements Serializable {

    private Long id;

    private Integer rank;

    private Integer wins;

    private Integer losses;

    private Integer ties;

    private Double pointsFor;

    private Double pointsAgainst;

    private Double pointsForPerGame;

    private Double pointsAgainstPerGame;

    private Double pointsDiffPerGame;

    private Long teamId;

    private Long seasonId;

    private String seasonYear;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public Integer getTies() {
        return ties;
    }

    public void setTies(Integer ties) {
        this.ties = ties;
    }

    public Double getPointsFor() {
        return pointsFor;
    }

    public void setPointsFor(Double pointsFor) {
        this.pointsFor = pointsFor;
    }

    public Double getPointsAgainst() {
        return pointsAgainst;
    }

    public void setPointsAgainst(Double pointsAgainst) {
        this.pointsAgainst = pointsAgainst;
    }

    public Double getPointsForPerGame() {
        return pointsForPerGame;
    }

    public void setPointsForPerGame(Double pointsForPerGame) {
        this.pointsForPerGame = pointsForPerGame;
    }

    public Double getPointsAgainstPerGame() {
        return pointsAgainstPerGame;
    }

    public void setPointsAgainstPerGame(Double pointsAgainstPerGame) {
        this.pointsAgainstPerGame = pointsAgainstPerGame;
    }

    public Double getPointsDiffPerGame() {
        return pointsDiffPerGame;
    }

    public void setPointsDiffPerGame(Double pointsDiffPerGame) {
        this.pointsDiffPerGame = pointsDiffPerGame;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Long seasonId) {
        this.seasonId = seasonId;
    }

    public String getSeasonYear() {
        return seasonYear;
    }

    public void setSeasonYear(String seasonYear) {
        this.seasonYear = seasonYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TeamStatsDTO teamStatsDTO = (TeamStatsDTO) o;
        if(teamStatsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), teamStatsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TeamStatsDTO{" +
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
