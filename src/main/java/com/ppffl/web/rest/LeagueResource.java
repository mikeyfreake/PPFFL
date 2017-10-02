package com.ppffl.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ppffl.domain.League;

import com.ppffl.repository.LeagueRepository;
import com.ppffl.web.rest.util.HeaderUtil;
import com.ppffl.service.dto.LeagueDTO;
import com.ppffl.service.mapper.LeagueMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing League.
 */
@RestController
@RequestMapping("/api")
public class LeagueResource {

    private final Logger log = LoggerFactory.getLogger(LeagueResource.class);

    private static final String ENTITY_NAME = "league";

    private final LeagueRepository leagueRepository;

    private final LeagueMapper leagueMapper;

    public LeagueResource(LeagueRepository leagueRepository, LeagueMapper leagueMapper) {
        this.leagueRepository = leagueRepository;
        this.leagueMapper = leagueMapper;
    }

    /**
     * POST  /leagues : Create a new league.
     *
     * @param leagueDTO the leagueDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new leagueDTO, or with status 400 (Bad Request) if the league has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/leagues")
    @Timed
    public ResponseEntity<LeagueDTO> createLeague(@Valid @RequestBody LeagueDTO leagueDTO) throws URISyntaxException {
        log.debug("REST request to save League : {}", leagueDTO);
        if (leagueDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new league cannot already have an ID")).body(null);
        }
        League league = leagueMapper.toEntity(leagueDTO);
        league = leagueRepository.save(league);
        LeagueDTO result = leagueMapper.toDto(league);
        return ResponseEntity.created(new URI("/api/leagues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /leagues : Updates an existing league.
     *
     * @param leagueDTO the leagueDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated leagueDTO,
     * or with status 400 (Bad Request) if the leagueDTO is not valid,
     * or with status 500 (Internal Server Error) if the leagueDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/leagues")
    @Timed
    public ResponseEntity<LeagueDTO> updateLeague(@Valid @RequestBody LeagueDTO leagueDTO) throws URISyntaxException {
        log.debug("REST request to update League : {}", leagueDTO);
        if (leagueDTO.getId() == null) {
            return createLeague(leagueDTO);
        }
        League league = leagueMapper.toEntity(leagueDTO);
        league = leagueRepository.save(league);
        LeagueDTO result = leagueMapper.toDto(league);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, leagueDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /leagues : get all the leagues.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of leagues in body
     */
    @GetMapping("/leagues")
    @Timed
    public List<LeagueDTO> getAllLeagues() {
        log.debug("REST request to get all Leagues");
        List<League> leagues = leagueRepository.findAll();
        return leagueMapper.toDto(leagues);
        }

    /**
     * GET  /leagues/:id : get the "id" league.
     *
     * @param id the id of the leagueDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the leagueDTO, or with status 404 (Not Found)
     */
    @GetMapping("/leagues/{id}")
    @Timed
    public ResponseEntity<LeagueDTO> getLeague(@PathVariable Long id) {
        log.debug("REST request to get League : {}", id);
        League league = leagueRepository.findOne(id);
        LeagueDTO leagueDTO = leagueMapper.toDto(league);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(leagueDTO));
    }

    /**
     * DELETE  /leagues/:id : delete the "id" league.
     *
     * @param id the id of the leagueDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/leagues/{id}")
    @Timed
    public ResponseEntity<Void> deleteLeague(@PathVariable Long id) {
        log.debug("REST request to delete League : {}", id);
        leagueRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
