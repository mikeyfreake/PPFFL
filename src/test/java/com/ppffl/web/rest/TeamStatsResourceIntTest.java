package com.ppffl.web.rest;

import com.ppffl.ScraperApp;

import com.ppffl.domain.TeamStats;
import com.ppffl.repository.TeamStatsRepository;
import com.ppffl.service.dto.TeamStatsDTO;
import com.ppffl.service.mapper.TeamStatsMapper;
import com.ppffl.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TeamStatsResource REST controller.
 *
 * @see TeamStatsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ScraperApp.class)
public class TeamStatsResourceIntTest {

    private static final Integer DEFAULT_RANK = 1;
    private static final Integer UPDATED_RANK = 2;

    private static final Integer DEFAULT_WINS = 1;
    private static final Integer UPDATED_WINS = 2;

    private static final Integer DEFAULT_LOSSES = 1;
    private static final Integer UPDATED_LOSSES = 2;

    private static final Integer DEFAULT_TIES = 1;
    private static final Integer UPDATED_TIES = 2;

    private static final Double DEFAULT_POINTS_FOR = 1D;
    private static final Double UPDATED_POINTS_FOR = 2D;

    private static final Double DEFAULT_POINTS_AGAINST = 1D;
    private static final Double UPDATED_POINTS_AGAINST = 2D;

    private static final Double DEFAULT_POINTS_FOR_PER_GAME = 1D;
    private static final Double UPDATED_POINTS_FOR_PER_GAME = 2D;

    private static final Double DEFAULT_POINTS_AGAINST_PER_GAME = 1D;
    private static final Double UPDATED_POINTS_AGAINST_PER_GAME = 2D;

    private static final Double DEFAULT_POINTS_DIFF_PER_GAME = 1D;
    private static final Double UPDATED_POINTS_DIFF_PER_GAME = 2D;

    @Autowired
    private TeamStatsRepository teamStatsRepository;

    @Autowired
    private TeamStatsMapper teamStatsMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTeamStatsMockMvc;

    private TeamStats teamStats;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TeamStatsResource teamStatsResource = new TeamStatsResource(teamStatsRepository, teamStatsMapper);
        this.restTeamStatsMockMvc = MockMvcBuilders.standaloneSetup(teamStatsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamStats createEntity(EntityManager em) {
        TeamStats teamStats = new TeamStats()
            .rank(DEFAULT_RANK)
            .wins(DEFAULT_WINS)
            .losses(DEFAULT_LOSSES)
            .ties(DEFAULT_TIES)
            .pointsFor(DEFAULT_POINTS_FOR)
            .pointsAgainst(DEFAULT_POINTS_AGAINST)
            .pointsForPerGame(DEFAULT_POINTS_FOR_PER_GAME)
            .pointsAgainstPerGame(DEFAULT_POINTS_AGAINST_PER_GAME)
            .pointsDiffPerGame(DEFAULT_POINTS_DIFF_PER_GAME);
        return teamStats;
    }

    @Before
    public void initTest() {
        teamStats = createEntity(em);
    }

    @Test
    @Transactional
    public void createTeamStats() throws Exception {
        int databaseSizeBeforeCreate = teamStatsRepository.findAll().size();

        // Create the TeamStats
        TeamStatsDTO teamStatsDTO = teamStatsMapper.toDto(teamStats);
        restTeamStatsMockMvc.perform(post("/api/team-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamStatsDTO)))
            .andExpect(status().isCreated());

        // Validate the TeamStats in the database
        List<TeamStats> teamStatsList = teamStatsRepository.findAll();
        assertThat(teamStatsList).hasSize(databaseSizeBeforeCreate + 1);
        TeamStats testTeamStats = teamStatsList.get(teamStatsList.size() - 1);
        assertThat(testTeamStats.getRank()).isEqualTo(DEFAULT_RANK);
        assertThat(testTeamStats.getWins()).isEqualTo(DEFAULT_WINS);
        assertThat(testTeamStats.getLosses()).isEqualTo(DEFAULT_LOSSES);
        assertThat(testTeamStats.getTies()).isEqualTo(DEFAULT_TIES);
        assertThat(testTeamStats.getPointsFor()).isEqualTo(DEFAULT_POINTS_FOR);
        assertThat(testTeamStats.getPointsAgainst()).isEqualTo(DEFAULT_POINTS_AGAINST);
        assertThat(testTeamStats.getPointsForPerGame()).isEqualTo(DEFAULT_POINTS_FOR_PER_GAME);
        assertThat(testTeamStats.getPointsAgainstPerGame()).isEqualTo(DEFAULT_POINTS_AGAINST_PER_GAME);
        assertThat(testTeamStats.getPointsDiffPerGame()).isEqualTo(DEFAULT_POINTS_DIFF_PER_GAME);
    }

    @Test
    @Transactional
    public void createTeamStatsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teamStatsRepository.findAll().size();

        // Create the TeamStats with an existing ID
        teamStats.setId(1L);
        TeamStatsDTO teamStatsDTO = teamStatsMapper.toDto(teamStats);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamStatsMockMvc.perform(post("/api/team-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamStatsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TeamStats in the database
        List<TeamStats> teamStatsList = teamStatsRepository.findAll();
        assertThat(teamStatsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTeamStats() throws Exception {
        // Initialize the database
        teamStatsRepository.saveAndFlush(teamStats);

        // Get all the teamStatsList
        restTeamStatsMockMvc.perform(get("/api/team-stats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teamStats.getId().intValue())))
            .andExpect(jsonPath("$.[*].rank").value(hasItem(DEFAULT_RANK)))
            .andExpect(jsonPath("$.[*].wins").value(hasItem(DEFAULT_WINS)))
            .andExpect(jsonPath("$.[*].losses").value(hasItem(DEFAULT_LOSSES)))
            .andExpect(jsonPath("$.[*].ties").value(hasItem(DEFAULT_TIES)))
            .andExpect(jsonPath("$.[*].pointsFor").value(hasItem(DEFAULT_POINTS_FOR.doubleValue())))
            .andExpect(jsonPath("$.[*].pointsAgainst").value(hasItem(DEFAULT_POINTS_AGAINST.doubleValue())))
            .andExpect(jsonPath("$.[*].pointsForPerGame").value(hasItem(DEFAULT_POINTS_FOR_PER_GAME.doubleValue())))
            .andExpect(jsonPath("$.[*].pointsAgainstPerGame").value(hasItem(DEFAULT_POINTS_AGAINST_PER_GAME.doubleValue())))
            .andExpect(jsonPath("$.[*].pointsDiffPerGame").value(hasItem(DEFAULT_POINTS_DIFF_PER_GAME.doubleValue())));
    }

    @Test
    @Transactional
    public void getTeamStats() throws Exception {
        // Initialize the database
        teamStatsRepository.saveAndFlush(teamStats);

        // Get the teamStats
        restTeamStatsMockMvc.perform(get("/api/team-stats/{id}", teamStats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(teamStats.getId().intValue()))
            .andExpect(jsonPath("$.rank").value(DEFAULT_RANK))
            .andExpect(jsonPath("$.wins").value(DEFAULT_WINS))
            .andExpect(jsonPath("$.losses").value(DEFAULT_LOSSES))
            .andExpect(jsonPath("$.ties").value(DEFAULT_TIES))
            .andExpect(jsonPath("$.pointsFor").value(DEFAULT_POINTS_FOR.doubleValue()))
            .andExpect(jsonPath("$.pointsAgainst").value(DEFAULT_POINTS_AGAINST.doubleValue()))
            .andExpect(jsonPath("$.pointsForPerGame").value(DEFAULT_POINTS_FOR_PER_GAME.doubleValue()))
            .andExpect(jsonPath("$.pointsAgainstPerGame").value(DEFAULT_POINTS_AGAINST_PER_GAME.doubleValue()))
            .andExpect(jsonPath("$.pointsDiffPerGame").value(DEFAULT_POINTS_DIFF_PER_GAME.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTeamStats() throws Exception {
        // Get the teamStats
        restTeamStatsMockMvc.perform(get("/api/team-stats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeamStats() throws Exception {
        // Initialize the database
        teamStatsRepository.saveAndFlush(teamStats);
        int databaseSizeBeforeUpdate = teamStatsRepository.findAll().size();

        // Update the teamStats
        TeamStats updatedTeamStats = teamStatsRepository.findOne(teamStats.getId());
        updatedTeamStats
            .rank(UPDATED_RANK)
            .wins(UPDATED_WINS)
            .losses(UPDATED_LOSSES)
            .ties(UPDATED_TIES)
            .pointsFor(UPDATED_POINTS_FOR)
            .pointsAgainst(UPDATED_POINTS_AGAINST)
            .pointsForPerGame(UPDATED_POINTS_FOR_PER_GAME)
            .pointsAgainstPerGame(UPDATED_POINTS_AGAINST_PER_GAME)
            .pointsDiffPerGame(UPDATED_POINTS_DIFF_PER_GAME);
        TeamStatsDTO teamStatsDTO = teamStatsMapper.toDto(updatedTeamStats);

        restTeamStatsMockMvc.perform(put("/api/team-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamStatsDTO)))
            .andExpect(status().isOk());

        // Validate the TeamStats in the database
        List<TeamStats> teamStatsList = teamStatsRepository.findAll();
        assertThat(teamStatsList).hasSize(databaseSizeBeforeUpdate);
        TeamStats testTeamStats = teamStatsList.get(teamStatsList.size() - 1);
        assertThat(testTeamStats.getRank()).isEqualTo(UPDATED_RANK);
        assertThat(testTeamStats.getWins()).isEqualTo(UPDATED_WINS);
        assertThat(testTeamStats.getLosses()).isEqualTo(UPDATED_LOSSES);
        assertThat(testTeamStats.getTies()).isEqualTo(UPDATED_TIES);
        assertThat(testTeamStats.getPointsFor()).isEqualTo(UPDATED_POINTS_FOR);
        assertThat(testTeamStats.getPointsAgainst()).isEqualTo(UPDATED_POINTS_AGAINST);
        assertThat(testTeamStats.getPointsForPerGame()).isEqualTo(UPDATED_POINTS_FOR_PER_GAME);
        assertThat(testTeamStats.getPointsAgainstPerGame()).isEqualTo(UPDATED_POINTS_AGAINST_PER_GAME);
        assertThat(testTeamStats.getPointsDiffPerGame()).isEqualTo(UPDATED_POINTS_DIFF_PER_GAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTeamStats() throws Exception {
        int databaseSizeBeforeUpdate = teamStatsRepository.findAll().size();

        // Create the TeamStats
        TeamStatsDTO teamStatsDTO = teamStatsMapper.toDto(teamStats);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTeamStatsMockMvc.perform(put("/api/team-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamStatsDTO)))
            .andExpect(status().isCreated());

        // Validate the TeamStats in the database
        List<TeamStats> teamStatsList = teamStatsRepository.findAll();
        assertThat(teamStatsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTeamStats() throws Exception {
        // Initialize the database
        teamStatsRepository.saveAndFlush(teamStats);
        int databaseSizeBeforeDelete = teamStatsRepository.findAll().size();

        // Get the teamStats
        restTeamStatsMockMvc.perform(delete("/api/team-stats/{id}", teamStats.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TeamStats> teamStatsList = teamStatsRepository.findAll();
        assertThat(teamStatsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeamStats.class);
        TeamStats teamStats1 = new TeamStats();
        teamStats1.setId(1L);
        TeamStats teamStats2 = new TeamStats();
        teamStats2.setId(teamStats1.getId());
        assertThat(teamStats1).isEqualTo(teamStats2);
        teamStats2.setId(2L);
        assertThat(teamStats1).isNotEqualTo(teamStats2);
        teamStats1.setId(null);
        assertThat(teamStats1).isNotEqualTo(teamStats2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeamStatsDTO.class);
        TeamStatsDTO teamStatsDTO1 = new TeamStatsDTO();
        teamStatsDTO1.setId(1L);
        TeamStatsDTO teamStatsDTO2 = new TeamStatsDTO();
        assertThat(teamStatsDTO1).isNotEqualTo(teamStatsDTO2);
        teamStatsDTO2.setId(teamStatsDTO1.getId());
        assertThat(teamStatsDTO1).isEqualTo(teamStatsDTO2);
        teamStatsDTO2.setId(2L);
        assertThat(teamStatsDTO1).isNotEqualTo(teamStatsDTO2);
        teamStatsDTO1.setId(null);
        assertThat(teamStatsDTO1).isNotEqualTo(teamStatsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(teamStatsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(teamStatsMapper.fromId(null)).isNull();
    }
}
