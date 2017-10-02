package com.ppffl.service.mapper;

import com.ppffl.domain.*;
import com.ppffl.service.dto.LeagueDTO;

import org.mapstruct.*;

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
