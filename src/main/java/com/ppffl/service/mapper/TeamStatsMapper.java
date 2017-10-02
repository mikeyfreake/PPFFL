package com.ppffl.service.mapper;

import com.ppffl.domain.*;
import com.ppffl.service.dto.TeamStatsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TeamStats and its DTO TeamStatsDTO.
 */
@Mapper(componentModel = "spring", uses = {TeamMapper.class, SeasonMapper.class, })
public interface TeamStatsMapper extends EntityMapper <TeamStatsDTO, TeamStats> {

    @Mapping(source = "team.id", target = "teamId")

    @Mapping(source = "season.id", target = "seasonId")
    @Mapping(source = "season.year", target = "seasonYear")
    TeamStatsDTO toDto(TeamStats teamStats); 

    @Mapping(source = "teamId", target = "team")

    @Mapping(source = "seasonId", target = "season")
    TeamStats toEntity(TeamStatsDTO teamStatsDTO); 
    default TeamStats fromId(Long id) {
        if (id == null) {
            return null;
        }
        TeamStats teamStats = new TeamStats();
        teamStats.setId(id);
        return teamStats;
    }
}
