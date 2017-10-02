package com.ppffl.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ppffl.domain.League;
import com.ppffl.service.dto.LeagueDTO;

/**
 * Mapper for the entity League and its DTO LeagueDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LeagueMapper extends EntityMapper <LeagueDTO, League> {
    
    @Mapping(target = "seasons", ignore = true)
    League toEntity(LeagueDTO leagueDTO); 
    default League fromId(Long id) {
        if (id == null) {
            return null;
        }
        League league = new League();
        league.setId(id);
        return league;
    }
}
