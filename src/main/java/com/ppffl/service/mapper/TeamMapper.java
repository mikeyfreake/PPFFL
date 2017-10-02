package com.ppffl.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ppffl.domain.Team;
import com.ppffl.service.dto.TeamDTO;

/**
 * Mapper for the entity Team and its DTO TeamDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TeamMapper extends EntityMapper <TeamDTO, Team> {
    
    @Mapping(target = "teamStats", ignore = true)
    Team toEntity(TeamDTO teamDTO); 
    default Team fromId(Long id) {
        if (id == null) {
            return null;
        }
        Team team = new Team();
        team.setId(id);
        return team;
    }
}
