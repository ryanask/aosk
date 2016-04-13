package id.co.askrindo.aos.web.rest;

import id.co.askrindo.aos.Application;
import id.co.askrindo.aos.domain.RStatusAkad;
import id.co.askrindo.aos.repository.RStatusAkadRepository;
import id.co.askrindo.aos.repository.search.RStatusAkadSearchRepository;

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
 * Test class for the RStatusAkadResource REST controller.
 *
 * @see RStatusAkadResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RStatusAkadResourceIntTest {

    private static final String DEFAULT_ID_R_STATUS_AKAD = "AAAAA";
    private static final String UPDATED_ID_R_STATUS_AKAD = "BBBBB";
    private static final String DEFAULT_KETERANGAN = "AAAAA";
    private static final String UPDATED_KETERANGAN = "BBBBB";

    @Inject
    private RStatusAkadRepository rStatusAkadRepository;

    @Inject
    private RStatusAkadSearchRepository rStatusAkadSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRStatusAkadMockMvc;

    private RStatusAkad rStatusAkad;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RStatusAkadResource rStatusAkadResource = new RStatusAkadResource();
        ReflectionTestUtils.setField(rStatusAkadResource, "rStatusAkadSearchRepository", rStatusAkadSearchRepository);
        ReflectionTestUtils.setField(rStatusAkadResource, "rStatusAkadRepository", rStatusAkadRepository);
        this.restRStatusAkadMockMvc = MockMvcBuilders.standaloneSetup(rStatusAkadResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        rStatusAkad = new RStatusAkad();
        rStatusAkad.setId_r_status_akad(DEFAULT_ID_R_STATUS_AKAD);
        rStatusAkad.setKeterangan(DEFAULT_KETERANGAN);
    }

    @Test
    @Transactional
    public void createRStatusAkad() throws Exception {
        int databaseSizeBeforeCreate = rStatusAkadRepository.findAll().size();

        // Create the RStatusAkad

        restRStatusAkadMockMvc.perform(post("/api/rStatusAkads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rStatusAkad)))
                .andExpect(status().isCreated());

        // Validate the RStatusAkad in the database
        List<RStatusAkad> rStatusAkads = rStatusAkadRepository.findAll();
        assertThat(rStatusAkads).hasSize(databaseSizeBeforeCreate + 1);
        RStatusAkad testRStatusAkad = rStatusAkads.get(rStatusAkads.size() - 1);
        assertThat(testRStatusAkad.getId_r_status_akad()).isEqualTo(DEFAULT_ID_R_STATUS_AKAD);
        assertThat(testRStatusAkad.getKeterangan()).isEqualTo(DEFAULT_KETERANGAN);
    }

    @Test
    @Transactional
    public void checkId_r_status_akadIsRequired() throws Exception {
        int databaseSizeBeforeTest = rStatusAkadRepository.findAll().size();
        // set the field null
        rStatusAkad.setId_r_status_akad(null);

        // Create the RStatusAkad, which fails.

        restRStatusAkadMockMvc.perform(post("/api/rStatusAkads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rStatusAkad)))
                .andExpect(status().isBadRequest());

        List<RStatusAkad> rStatusAkads = rStatusAkadRepository.findAll();
        assertThat(rStatusAkads).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRStatusAkads() throws Exception {
        // Initialize the database
        rStatusAkadRepository.saveAndFlush(rStatusAkad);

        // Get all the rStatusAkads
        restRStatusAkadMockMvc.perform(get("/api/rStatusAkads?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(rStatusAkad.getId().intValue())))
                .andExpect(jsonPath("$.[*].id_r_status_akad").value(hasItem(DEFAULT_ID_R_STATUS_AKAD.toString())))
                .andExpect(jsonPath("$.[*].keterangan").value(hasItem(DEFAULT_KETERANGAN.toString())));
    }

    @Test
    @Transactional
    public void getRStatusAkad() throws Exception {
        // Initialize the database
        rStatusAkadRepository.saveAndFlush(rStatusAkad);

        // Get the rStatusAkad
        restRStatusAkadMockMvc.perform(get("/api/rStatusAkads/{id}", rStatusAkad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(rStatusAkad.getId().intValue()))
            .andExpect(jsonPath("$.id_r_status_akad").value(DEFAULT_ID_R_STATUS_AKAD.toString()))
            .andExpect(jsonPath("$.keterangan").value(DEFAULT_KETERANGAN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRStatusAkad() throws Exception {
        // Get the rStatusAkad
        restRStatusAkadMockMvc.perform(get("/api/rStatusAkads/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRStatusAkad() throws Exception {
        // Initialize the database
        rStatusAkadRepository.saveAndFlush(rStatusAkad);

		int databaseSizeBeforeUpdate = rStatusAkadRepository.findAll().size();

        // Update the rStatusAkad
        rStatusAkad.setId_r_status_akad(UPDATED_ID_R_STATUS_AKAD);
        rStatusAkad.setKeterangan(UPDATED_KETERANGAN);

        restRStatusAkadMockMvc.perform(put("/api/rStatusAkads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rStatusAkad)))
                .andExpect(status().isOk());

        // Validate the RStatusAkad in the database
        List<RStatusAkad> rStatusAkads = rStatusAkadRepository.findAll();
        assertThat(rStatusAkads).hasSize(databaseSizeBeforeUpdate);
        RStatusAkad testRStatusAkad = rStatusAkads.get(rStatusAkads.size() - 1);
        assertThat(testRStatusAkad.getId_r_status_akad()).isEqualTo(UPDATED_ID_R_STATUS_AKAD);
        assertThat(testRStatusAkad.getKeterangan()).isEqualTo(UPDATED_KETERANGAN);
    }

    @Test
    @Transactional
    public void deleteRStatusAkad() throws Exception {
        // Initialize the database
        rStatusAkadRepository.saveAndFlush(rStatusAkad);

		int databaseSizeBeforeDelete = rStatusAkadRepository.findAll().size();

        // Get the rStatusAkad
        restRStatusAkadMockMvc.perform(delete("/api/rStatusAkads/{id}", rStatusAkad.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RStatusAkad> rStatusAkads = rStatusAkadRepository.findAll();
        assertThat(rStatusAkads).hasSize(databaseSizeBeforeDelete - 1);
    }
}
