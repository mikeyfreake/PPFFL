package com.ppffl.service.mapper;

import com.ppffl.domain.*;
import com.ppffl.service.dto.TeamDTO;

import org.mapstruct.*;

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
