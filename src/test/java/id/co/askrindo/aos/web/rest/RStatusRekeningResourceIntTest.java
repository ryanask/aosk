package id.co.askrindo.aos.web.rest;

import id.co.askrindo.aos.Application;
import id.co.askrindo.aos.domain.RStatusRekening;
import id.co.askrindo.aos.repository.RStatusRekeningRepository;
import id.co.askrindo.aos.repository.search.RStatusRekeningSearchRepository;

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
 * Test class for the RStatusRekeningResource REST controller.
 *
 * @see RStatusRekeningResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RStatusRekeningResourceIntTest {

    private static final String DEFAULT_ID_R_STATUS_REKENING = "AAAAA";
    private static final String UPDATED_ID_R_STATUS_REKENING = "BBBBB";
    private static final String DEFAULT_KETERANGAN = "AAAAA";
    private static final String UPDATED_KETERANGAN = "BBBBB";

    @Inject
    private RStatusRekeningRepository rStatusRekeningRepository;

    @Inject
    private RStatusRekeningSearchRepository rStatusRekeningSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRStatusRekeningMockMvc;

    private RStatusRekening rStatusRekening;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RStatusRekeningResource rStatusRekeningResource = new RStatusRekeningResource();
        ReflectionTestUtils.setField(rStatusRekeningResource, "rStatusRekeningSearchRepository", rStatusRekeningSearchRepository);
        ReflectionTestUtils.setField(rStatusRekeningResource, "rStatusRekeningRepository", rStatusRekeningRepository);
        this.restRStatusRekeningMockMvc = MockMvcBuilders.standaloneSetup(rStatusRekeningResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        rStatusRekening = new RStatusRekening();
        rStatusRekening.setId_r_status_rekening(DEFAULT_ID_R_STATUS_REKENING);
        rStatusRekening.setKeterangan(DEFAULT_KETERANGAN);
    }

    @Test
    @Transactional
    public void createRStatusRekening() throws Exception {
        int databaseSizeBeforeCreate = rStatusRekeningRepository.findAll().size();

        // Create the RStatusRekening

        restRStatusRekeningMockMvc.perform(post("/api/rStatusRekenings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rStatusRekening)))
                .andExpect(status().isCreated());

        // Validate the RStatusRekening in the database
        List<RStatusRekening> rStatusRekenings = rStatusRekeningRepository.findAll();
        assertThat(rStatusRekenings).hasSize(databaseSizeBeforeCreate + 1);
        RStatusRekening testRStatusRekening = rStatusRekenings.get(rStatusRekenings.size() - 1);
        assertThat(testRStatusRekening.getId_r_status_rekening()).isEqualTo(DEFAULT_ID_R_STATUS_REKENING);
        assertThat(testRStatusRekening.getKeterangan()).isEqualTo(DEFAULT_KETERANGAN);
    }

    @Test
    @Transactional
    public void checkId_r_status_rekeningIsRequired() throws Exception {
        int databaseSizeBeforeTest = rStatusRekeningRepository.findAll().size();
        // set the field null
        rStatusRekening.setId_r_status_rekening(null);

        // Create the RStatusRekening, which fails.

        restRStatusRekeningMockMvc.perform(post("/api/rStatusRekenings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rStatusRekening)))
                .andExpect(status().isBadRequest());

        List<RStatusRekening> rStatusRekenings = rStatusRekeningRepository.findAll();
        assertThat(rStatusRekenings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRStatusRekenings() throws Exception {
        // Initialize the database
        rStatusRekeningRepository.saveAndFlush(rStatusRekening);

        // Get all the rStatusRekenings
        restRStatusRekeningMockMvc.perform(get("/api/rStatusRekenings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(rStatusRekening.getId().intValue())))
                .andExpect(jsonPath("$.[*].id_r_status_rekening").value(hasItem(DEFAULT_ID_R_STATUS_REKENING.toString())))
                .andExpect(jsonPath("$.[*].keterangan").value(hasItem(DEFAULT_KETERANGAN.toString())));
    }

    @Test
    @Transactional
    public void getRStatusRekening() throws Exception {
        // Initialize the database
        rStatusRekeningRepository.saveAndFlush(rStatusRekening);

        // Get the rStatusRekening
        restRStatusRekeningMockMvc.perform(get("/api/rStatusRekenings/{id}", rStatusRekening.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(rStatusRekening.getId().intValue()))
            .andExpect(jsonPath("$.id_r_status_rekening").value(DEFAULT_ID_R_STATUS_REKENING.toString()))
            .andExpect(jsonPath("$.keterangan").value(DEFAULT_KETERANGAN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRStatusRekening() throws Exception {
        // Get the rStatusRekening
        restRStatusRekeningMockMvc.perform(get("/api/rStatusRekenings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRStatusRekening() throws Exception {
        // Initialize the database
        rStatusRekeningRepository.saveAndFlush(rStatusRekening);

		int databaseSizeBeforeUpdate = rStatusRekeningRepository.findAll().size();

        // Update the rStatusRekening
        rStatusRekening.setId_r_status_rekening(UPDATED_ID_R_STATUS_REKENING);
        rStatusRekening.setKeterangan(UPDATED_KETERANGAN);

        restRStatusRekeningMockMvc.perform(put("/api/rStatusRekenings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rStatusRekening)))
                .andExpect(status().isOk());

        // Validate the RStatusRekening in the database
        List<RStatusRekening> rStatusRekenings = rStatusRekeningRepository.findAll();
        assertThat(rStatusRekenings).hasSize(databaseSizeBeforeUpdate);
        RStatusRekening testRStatusRekening = rStatusRekenings.get(rStatusRekenings.size() - 1);
        assertThat(testRStatusRekening.getId_r_status_rekening()).isEqualTo(UPDATED_ID_R_STATUS_REKENING);
        assertThat(testRStatusRekening.getKeterangan()).isEqualTo(UPDATED_KETERANGAN);
    }

    @Test
    @Transactional
    public void deleteRStatusRekening() throws Exception {
        // Initialize the database
        rStatusRekeningRepository.saveAndFlush(rStatusRekening);

		int databaseSizeBeforeDelete = rStatusRekeningRepository.findAll().size();

        // Get the rStatusRekening
        restRStatusRekeningMockMvc.perform(delete("/api/rStatusRekenings/{id}", rStatusRekening.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RStatusRekening> rStatusRekenings = rStatusRekeningRepository.findAll();
        assertThat(rStatusRekenings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
