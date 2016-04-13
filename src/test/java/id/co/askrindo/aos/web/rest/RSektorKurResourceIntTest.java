package id.co.askrindo.aos.web.rest;

import id.co.askrindo.aos.Application;
import id.co.askrindo.aos.domain.RSektorKur;
import id.co.askrindo.aos.repository.RSektorKurRepository;
import id.co.askrindo.aos.repository.search.RSektorKurSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the RSektorKurResource REST controller.
 *
 * @see RSektorKurResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RSektorKurResourceIntTest {

    private static final String DEFAULT_ID_SEKTOR = "AAAAA";
    private static final String UPDATED_ID_SEKTOR = "BBBBB";
    private static final String DEFAULT_URAIAN = "AAAAA";
    private static final String UPDATED_URAIAN = "BBBBB";
    private static final String DEFAULT_TANAMAN_KERAS = "AAAAA";
    private static final String UPDATED_TANAMAN_KERAS = "BBBBB";

    @Inject
    private RSektorKurRepository rSektorKurRepository;

    @Inject
    private RSektorKurSearchRepository rSektorKurSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRSektorKurMockMvc;

    private RSektorKur rSektorKur;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RSektorKurResource rSektorKurResource = new RSektorKurResource();
        ReflectionTestUtils.setField(rSektorKurResource, "rSektorKurSearchRepository", rSektorKurSearchRepository);
        ReflectionTestUtils.setField(rSektorKurResource, "rSektorKurRepository", rSektorKurRepository);
        this.restRSektorKurMockMvc = MockMvcBuilders.standaloneSetup(rSektorKurResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        rSektorKur = new RSektorKur();
        rSektorKur.setId_sektor(DEFAULT_ID_SEKTOR);
        rSektorKur.setUraian(DEFAULT_URAIAN);
        rSektorKur.setTanaman_keras(DEFAULT_TANAMAN_KERAS);
    }

    @Test
    @Transactional
    public void createRSektorKur() throws Exception {
        int databaseSizeBeforeCreate = rSektorKurRepository.findAll().size();

        // Create the RSektorKur

        restRSektorKurMockMvc.perform(post("/api/rSektorKurs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rSektorKur)))
                .andExpect(status().isCreated());

        // Validate the RSektorKur in the database
        List<RSektorKur> rSektorKurs = rSektorKurRepository.findAll();
        assertThat(rSektorKurs).hasSize(databaseSizeBeforeCreate + 1);
        RSektorKur testRSektorKur = rSektorKurs.get(rSektorKurs.size() - 1);
        assertThat(testRSektorKur.getId_sektor()).isEqualTo(DEFAULT_ID_SEKTOR);
        assertThat(testRSektorKur.getUraian()).isEqualTo(DEFAULT_URAIAN);
        assertThat(testRSektorKur.getTanaman_keras()).isEqualTo(DEFAULT_TANAMAN_KERAS);
    }

    @Test
    @Transactional
    public void checkId_sektorIsRequired() throws Exception {
        int databaseSizeBeforeTest = rSektorKurRepository.findAll().size();
        // set the field null
        rSektorKur.setId_sektor(null);

        // Create the RSektorKur, which fails.

        restRSektorKurMockMvc.perform(post("/api/rSektorKurs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rSektorKur)))
                .andExpect(status().isBadRequest());

        List<RSektorKur> rSektorKurs = rSektorKurRepository.findAll();
        assertThat(rSektorKurs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRSektorKurs() throws Exception {
        // Initialize the database
        rSektorKurRepository.saveAndFlush(rSektorKur);

        // Get all the rSektorKurs
        restRSektorKurMockMvc.perform(get("/api/rSektorKurs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(rSektorKur.getId().intValue())))
                .andExpect(jsonPath("$.[*].id_sektor").value(hasItem(DEFAULT_ID_SEKTOR.toString())))
                .andExpect(jsonPath("$.[*].uraian").value(hasItem(DEFAULT_URAIAN.toString())))
                .andExpect(jsonPath("$.[*].tanaman_keras").value(hasItem(DEFAULT_TANAMAN_KERAS.toString())));
    }

    @Test
    @Transactional
    public void getRSektorKur() throws Exception {
        // Initialize the database
        rSektorKurRepository.saveAndFlush(rSektorKur);

        // Get the rSektorKur
        restRSektorKurMockMvc.perform(get("/api/rSektorKurs/{id}", rSektorKur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(rSektorKur.getId().intValue()))
            .andExpect(jsonPath("$.id_sektor").value(DEFAULT_ID_SEKTOR.toString()))
            .andExpect(jsonPath("$.uraian").value(DEFAULT_URAIAN.toString()))
            .andExpect(jsonPath("$.tanaman_keras").value(DEFAULT_TANAMAN_KERAS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRSektorKur() throws Exception {
        // Get the rSektorKur
        restRSektorKurMockMvc.perform(get("/api/rSektorKurs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRSektorKur() throws Exception {
        // Initialize the database
        rSektorKurRepository.saveAndFlush(rSektorKur);

		int databaseSizeBeforeUpdate = rSektorKurRepository.findAll().size();

        // Update the rSektorKur
        rSektorKur.setId_sektor(UPDATED_ID_SEKTOR);
        rSektorKur.setUraian(UPDATED_URAIAN);
        rSektorKur.setTanaman_keras(UPDATED_TANAMAN_KERAS);

        restRSektorKurMockMvc.perform(put("/api/rSektorKurs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rSektorKur)))
                .andExpect(status().isOk());

        // Validate the RSektorKur in the database
        List<RSektorKur> rSektorKurs = rSektorKurRepository.findAll();
        assertThat(rSektorKurs).hasSize(databaseSizeBeforeUpdate);
        RSektorKur testRSektorKur = rSektorKurs.get(rSektorKurs.size() - 1);
        assertThat(testRSektorKur.getId_sektor()).isEqualTo(UPDATED_ID_SEKTOR);
        assertThat(testRSektorKur.getUraian()).isEqualTo(UPDATED_URAIAN);
        assertThat(testRSektorKur.getTanaman_keras()).isEqualTo(UPDATED_TANAMAN_KERAS);
    }

    @Test
    @Transactional
    public void deleteRSektorKur() throws Exception {
        // Initialize the database
        rSektorKurRepository.saveAndFlush(rSektorKur);

		int databaseSizeBeforeDelete = rSektorKurRepository.findAll().size();

        // Get the rSektorKur
        restRSektorKurMockMvc.perform(delete("/api/rSektorKurs/{id}", rSektorKur.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RSektorKur> rSektorKurs = rSektorKurRepository.findAll();
        assertThat(rSektorKurs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
