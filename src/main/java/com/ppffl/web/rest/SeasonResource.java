package com.ppffl.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import com.ppffl.domain.Season;
import com.ppffl.repository.SeasonRepository;
import com.ppffl.service.dto.SeasonDTO;
import com.ppffl.service.mapper.SeasonMapper;
import com.ppffl.web.rest.util.HeaderUtil;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Season.
 */
@RestController
@RequestMapping("/api")
public class SeasonResource {

    private final Logger log = LoggerFactory.getLogger(SeasonResource.class);

    private static final String ENTITY_NAME = "season";

    private final SeasonRepository seasonRepository;

    private final SeasonMapper seasonMapper;

    public SeasonResource(SeasonRepository seasonRepository, SeasonMapper seasonMapper) {
        this.seasonRepository = seasonRepository;
        this.seasonMapper = seasonMapper;
    }

    /**
     * POST  /seasons : Create a new season.
     *
     * @param seasonDTO the seasonDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new seasonDTO, or with status 400 (Bad Request) if the season has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/seasons")
    @Timed
    public ResponseEntity<SeasonDTO> createSeason(@Valid @RequestBody SeasonDTO seasonDTO) throws URISyntaxException {
        log.debug("REST request to save Season : {}", seasonDTO);
        if (seasonDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new season cannot already have an ID")).body(null);
        }
        Season season = seasonMapper.toEntity(seasonDTO);
        season = seasonRepository.save(season);
        SeasonDTO result = seasonMapper.toDto(season);
        return ResponseEntity.created(new URI("/api/seasons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /seasons : Updates an existing season.
     *
     * @param seasonDTO the seasonDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated seasonDTO,
     * or with status 400 (Bad Request) if the seasonDTO is not valid,
     * or with status 500 (Internal Server Error) if the seasonDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/seasons")
    @Timed
    public ResponseEntity<SeasonDTO> updateSeason(@Valid @RequestBody SeasonDTO seasonDTO) throws URISyntaxException {
        log.debug("REST request to update Season : {}", seasonDTO);
        if (seasonDTO.getId() == null) {
            return createSeason(seasonDTO);
        }
        Season season = seasonMapper.toEntity(seasonDTO);
        season = seasonRepository.save(season);
        SeasonDTO result = seasonMapper.toDto(season);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, seasonDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /seasons : get all the seasons.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of seasons in body
     */
    @GetMapping("/seasons")
    @Timed
    public List<SeasonDTO> getAllSeasons() {
        log.debug("REST request to get all Seasons");
        List<Season> seasons = seasonRepository.findAll();
        return seasonMapper.toDto(seasons);
        }

    /**
     * GET  /seasons/:id : get the "id" season.
     *
     * @param id the id of the seasonDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the seasonDTO, or with status 404 (Not Found)
     */
    @GetMapping("/seasons/{id}")
    @Timed
    public ResponseEntity<SeasonDTO> getSeason(@PathVariable Long id) {
        log.debug("REST request to get Season : {}", id);
        Season season = seasonRepository.findOne(id);
        SeasonDTO seasonDTO = seasonMapper.toDto(season);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(seasonDTO));
    }

    /**
     * DELETE  /seasons/:id : delete the "id" season.
     *
     * @param id the id of the seasonDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/seasons/{id}")
    @Timed
    public ResponseEntity<Void> deleteSeason(@PathVariable Long id) {
        log.debug("REST request to delete Season : {}", id);
        seasonRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
