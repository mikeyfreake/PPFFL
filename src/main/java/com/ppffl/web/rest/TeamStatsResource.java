package com.ppffl.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.ppffl.domain.TeamStats;
import com.ppffl.repository.TeamStatsRepository;
import com.ppffl.service.dto.TeamStatsDTO;
import com.ppffl.service.mapper.TeamStatsMapper;
import com.ppffl.web.rest.util.HeaderUtil;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing TeamStats.
 */
@RestController
@RequestMapping("/api")
public class TeamStatsResource {

    private final Logger log = LoggerFactory.getLogger(TeamStatsResource.class);

    private static final String ENTITY_NAME = "teamStats";

    private final TeamStatsRepository teamStatsRepository;

    private final TeamStatsMapper teamStatsMapper;

    public TeamStatsResource(TeamStatsRepository teamStatsRepository, TeamStatsMapper teamStatsMapper) {
        this.teamStatsRepository = teamStatsRepository;
        this.teamStatsMapper = teamStatsMapper;
    }

    /**
     * POST  /team-stats : Create a new teamStats.
     *
     * @param teamStatsDTO the teamStatsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new teamStatsDTO, or with status 400 (Bad Request) if the teamStats has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/team-stats")
    @Timed
    public ResponseEntity<TeamStatsDTO> createTeamStats(@RequestBody TeamStatsDTO teamStatsDTO) throws URISyntaxException {
        log.debug("REST request to save TeamStats : {}", teamStatsDTO);
        if (teamStatsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new teamStats cannot already have an ID")).body(null);
        }
        TeamStats teamStats = teamStatsMapper.toEntity(teamStatsDTO);
        teamStats = teamStatsRepository.save(teamStats);
        TeamStatsDTO result = teamStatsMapper.toDto(teamStats);
        return ResponseEntity.created(new URI("/api/team-stats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /team-stats : Updates an existing teamStats.
     *
     * @param teamStatsDTO the teamStatsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated teamStatsDTO,
     * or with status 400 (Bad Request) if the teamStatsDTO is not valid,
     * or with status 500 (Internal Server Error) if the teamStatsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/team-stats")
    @Timed
    public ResponseEntity<TeamStatsDTO> updateTeamStats(@RequestBody TeamStatsDTO teamStatsDTO) throws URISyntaxException {
        log.debug("REST request to update TeamStats : {}", teamStatsDTO);
        if (teamStatsDTO.getId() == null) {
            return createTeamStats(teamStatsDTO);
        }
        TeamStats teamStats = teamStatsMapper.toEntity(teamStatsDTO);
        teamStats = teamStatsRepository.save(teamStats);
        TeamStatsDTO result = teamStatsMapper.toDto(teamStats);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, teamStatsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /team-stats : get all the teamStats.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of teamStats in body
     */
    @GetMapping("/team-stats")
    @Timed
    public List<TeamStatsDTO> getAllTeamStats() {
        log.debug("REST request to get all TeamStats");
        List<TeamStats> teamStats = teamStatsRepository.findAll();
        return teamStatsMapper.toDto(teamStats);
        }

    /**
     * GET  /team-stats/:id : get the "id" teamStats.
     *
     * @param id the id of the teamStatsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the teamStatsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/team-stats/{id}")
    @Timed
    public ResponseEntity<TeamStatsDTO> getTeamStats(@PathVariable Long id) {
        log.debug("REST request to get TeamStats : {}", id);
        TeamStats teamStats = teamStatsRepository.findOne(id);
        TeamStatsDTO teamStatsDTO = teamStatsMapper.toDto(teamStats);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(teamStatsDTO));
    }

    /**
     * DELETE  /team-stats/:id : delete the "id" teamStats.
     *
     * @param id the id of the teamStatsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/team-stats/{id}")
    @Timed
    public ResponseEntity<Void> deleteTeamStats(@PathVariable Long id) {
        log.debug("REST request to delete TeamStats : {}", id);
        teamStatsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
