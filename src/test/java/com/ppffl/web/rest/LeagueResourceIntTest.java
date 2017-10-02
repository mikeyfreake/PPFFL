package com.ppffl.web.rest;

import com.ppffl.ScraperApp;

import com.ppffl.domain.League;
import com.ppffl.repository.LeagueRepository;
import com.ppffl.service.dto.LeagueDTO;
import com.ppffl.service.mapper.LeagueMapper;
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

import com.ppffl.domain.enumeration.FantasyProvider;
/**
 * Test class for the LeagueResource REST controller.
 *
 * @see LeagueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ScraperApp.class)
public class LeagueResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final FantasyProvider DEFAULT_IMPORTED_FROM = FantasyProvider.ESPN;
    private static final FantasyProvider UPDATED_IMPORTED_FROM = FantasyProvider.NFL;

    private static final String DEFAULT_CONSTITUTION = "AAAAAAAAAA";
    private static final String UPDATED_CONSTITUTION = "BBBBBBBBBB";

    @Autowired
    private LeagueRepository leagueRepository;

    @Autowired
    private LeagueMapper leagueMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLeagueMockMvc;

    private League league;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LeagueResource leagueResource = new LeagueResource(leagueRepository, leagueMapper);
        this.restLeagueMockMvc = MockMvcBuilders.standaloneSetup(leagueResource)
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
    public static League createEntity(EntityManager em) {
        League league = new League()
            .name(DEFAULT_NAME)
            .importedFrom(DEFAULT_IMPORTED_FROM)
            .constitution(DEFAULT_CONSTITUTION);
        return league;
    }

    @Before
    public void initTest() {
        league = createEntity(em);
    }

    @Test
    @Transactional
    public void createLeague() throws Exception {
        int databaseSizeBeforeCreate = leagueRepository.findAll().size();

        // Create the League
        LeagueDTO leagueDTO = leagueMapper.toDto(league);
        restLeagueMockMvc.perform(post("/api/leagues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leagueDTO)))
            .andExpect(status().isCreated());

        // Validate the League in the database
        List<League> leagueList = leagueRepository.findAll();
        assertThat(leagueList).hasSize(databaseSizeBeforeCreate + 1);
        League testLeague = leagueList.get(leagueList.size() - 1);
        assertThat(testLeague.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLeague.getImportedFrom()).isEqualTo(DEFAULT_IMPORTED_FROM);
        assertThat(testLeague.getConstitution()).isEqualTo(DEFAULT_CONSTITUTION);
    }

    @Test
    @Transactional
    public void createLeagueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = leagueRepository.findAll().size();

        // Create the League with an existing ID
        league.setId(1L);
        LeagueDTO leagueDTO = leagueMapper.toDto(league);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeagueMockMvc.perform(post("/api/leagues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leagueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the League in the database
        List<League> leagueList = leagueRepository.findAll();
        assertThat(leagueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = leagueRepository.findAll().size();
        // set the field null
        league.setName(null);

        // Create the League, which fails.
        LeagueDTO leagueDTO = leagueMapper.toDto(league);

        restLeagueMockMvc.perform(post("/api/leagues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leagueDTO)))
            .andExpect(status().isBadRequest());

        List<League> leagueList = leagueRepository.findAll();
        assertThat(leagueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLeagues() throws Exception {
        // Initialize the database
        leagueRepository.saveAndFlush(league);

        // Get all the leagueList
        restLeagueMockMvc.perform(get("/api/leagues?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(league.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].importedFrom").value(hasItem(DEFAULT_IMPORTED_FROM.toString())))
            .andExpect(jsonPath("$.[*].constitution").value(hasItem(DEFAULT_CONSTITUTION.toString())));
    }

    @Test
    @Transactional
    public void getLeague() throws Exception {
        // Initialize the database
        leagueRepository.saveAndFlush(league);

        // Get the league
        restLeagueMockMvc.perform(get("/api/leagues/{id}", league.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(league.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.importedFrom").value(DEFAULT_IMPORTED_FROM.toString()))
            .andExpect(jsonPath("$.constitution").value(DEFAULT_CONSTITUTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLeague() throws Exception {
        // Get the league
        restLeagueMockMvc.perform(get("/api/leagues/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLeague() throws Exception {
        // Initialize the database
        leagueRepository.saveAndFlush(league);
        int databaseSizeBeforeUpdate = leagueRepository.findAll().size();

        // Update the league
        League updatedLeague = leagueRepository.findOne(league.getId());
        updatedLeague
            .name(UPDATED_NAME)
            .importedFrom(UPDATED_IMPORTED_FROM)
            .constitution(UPDATED_CONSTITUTION);
        LeagueDTO leagueDTO = leagueMapper.toDto(updatedLeague);

        restLeagueMockMvc.perform(put("/api/leagues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leagueDTO)))
            .andExpect(status().isOk());

        // Validate the League in the database
        List<League> leagueList = leagueRepository.findAll();
        assertThat(leagueList).hasSize(databaseSizeBeforeUpdate);
        League testLeague = leagueList.get(leagueList.size() - 1);
        assertThat(testLeague.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLeague.getImportedFrom()).isEqualTo(UPDATED_IMPORTED_FROM);
        assertThat(testLeague.getConstitution()).isEqualTo(UPDATED_CONSTITUTION);
    }

    @Test
    @Transactional
    public void updateNonExistingLeague() throws Exception {
        int databaseSizeBeforeUpdate = leagueRepository.findAll().size();

        // Create the League
        LeagueDTO leagueDTO = leagueMapper.toDto(league);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLeagueMockMvc.perform(put("/api/leagues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leagueDTO)))
            .andExpect(status().isCreated());

        // Validate the League in the database
        List<League> leagueList = leagueRepository.findAll();
        assertThat(leagueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLeague() throws Exception {
        // Initialize the database
        leagueRepository.saveAndFlush(league);
        int databaseSizeBeforeDelete = leagueRepository.findAll().size();

        // Get the league
        restLeagueMockMvc.perform(delete("/api/leagues/{id}", league.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<League> leagueList = leagueRepository.findAll();
        assertThat(leagueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(League.class);
        League league1 = new League();
        league1.setId(1L);
        League league2 = new League();
        league2.setId(league1.getId());
        assertThat(league1).isEqualTo(league2);
        league2.setId(2L);
        assertThat(league1).isNotEqualTo(league2);
        league1.setId(null);
        assertThat(league1).isNotEqualTo(league2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeagueDTO.class);
        LeagueDTO leagueDTO1 = new LeagueDTO();
        leagueDTO1.setId(1L);
        LeagueDTO leagueDTO2 = new LeagueDTO();
        assertThat(leagueDTO1).isNotEqualTo(leagueDTO2);
        leagueDTO2.setId(leagueDTO1.getId());
        assertThat(leagueDTO1).isEqualTo(leagueDTO2);
        leagueDTO2.setId(2L);
        assertThat(leagueDTO1).isNotEqualTo(leagueDTO2);
        leagueDTO1.setId(null);
        assertThat(leagueDTO1).isNotEqualTo(leagueDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(leagueMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(leagueMapper.fromId(null)).isNull();
    }
}
