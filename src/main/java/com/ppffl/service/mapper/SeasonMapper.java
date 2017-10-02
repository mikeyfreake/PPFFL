package com.ppffl.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ppffl.domain.Season;
import com.ppffl.service.dto.SeasonDTO;

/**
 * Mapper for the entity Season and its DTO SeasonDTO.
 */
@Mapper(componentModel = "spring", uses = {LeagueMapper.class, })
public interface SeasonMapper extends EntityMapper <SeasonDTO, Season> {

    @Mapping(source = "league.id", target = "leagueId")
    @Mapping(source = "league.name", target = "leagueName")
    SeasonDTO toDto(Season season); 
    @Mapping(target = "teamStats", ignore = true)

    @Mapping(source = "leagueId", target = "league")
    Season toEntity(SeasonDTO seasonDTO); 
    default Season fromId(Long id) {
        if (id == null) {
            return null;
        }
        Season season = new Season();
        season.setId(id);
        return season;
    }
}
