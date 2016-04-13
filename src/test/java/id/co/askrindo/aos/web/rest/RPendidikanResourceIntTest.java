package id.co.askrindo.aos.web.rest;

import id.co.askrindo.aos.Application;
import id.co.askrindo.aos.domain.RPendidikan;
import id.co.askrindo.aos.repository.RPendidikanRepository;
import id.co.askrindo.aos.repository.search.RPendidikanSearchRepository;

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
 * Test class for the RPendidikanResource REST controller.
 *
 * @see RPendidikanResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RPendidikanResourceIntTest {

    private static final String DEFAULT_ID_R_PENDIDIKAN = "AAAAA";
    private static final String UPDATED_ID_R_PENDIDIKAN = "BBBBB";
    private static final String DEFAULT_KETERANGAN = "AAAAA";
    private static final String UPDATED_KETERANGAN = "BBBBB";

    @Inject
    private RPendidikanRepository rPendidikanRepository;

    @Inject
    private RPendidikanSearchRepository rPendidikanSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRPendidikanMockMvc;

    private RPendidikan rPendidikan;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RPendidikanResource rPendidikanResource = new RPendidikanResource();
        ReflectionTestUtils.setField(rPendidikanResource, "rPendidikanSearchRepository", rPendidikanSearchRepository);
        ReflectionTestUtils.setField(rPendidikanResource, "rPendidikanRepository", rPendidikanRepository);
        this.restRPendidikanMockMvc = MockMvcBuilders.standaloneSetup(rPendidikanResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        rPendidikan = new RPendidikan();
        rPendidikan.setId_r_pendidikan(DEFAULT_ID_R_PENDIDIKAN);
        rPendidikan.setKeterangan(DEFAULT_KETERANGAN);
    }

    @Test
    @Transactional
    public void createRPendidikan() throws Exception {
        int databaseSizeBeforeCreate = rPendidikanRepository.findAll().size();

        // Create the RPendidikan

        restRPendidikanMockMvc.perform(post("/api/rPendidikans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rPendidikan)))
                .andExpect(status().isCreated());

        // Validate the RPendidikan in the database
        List<RPendidikan> rPendidikans = rPendidikanRepository.findAll();
        assertThat(rPendidikans).hasSize(databaseSizeBeforeCreate + 1);
        RPendidikan testRPendidikan = rPendidikans.get(rPendidikans.size() - 1);
        assertThat(testRPendidikan.getId_r_pendidikan()).isEqualTo(DEFAULT_ID_R_PENDIDIKAN);
        assertThat(testRPendidikan.getKeterangan()).isEqualTo(DEFAULT_KETERANGAN);
    }

    @Test
    @Transactional
    public void checkId_r_pendidikanIsRequired() throws Exception {
        int databaseSizeBeforeTest = rPendidikanRepository.findAll().size();
        // set the field null
        rPendidikan.setId_r_pendidikan(null);

        // Create the RPendidikan, which fails.

        restRPendidikanMockMvc.perform(post("/api/rPendidikans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rPendidikan)))
                .andExpect(status().isBadRequest());

        List<RPendidikan> rPendidikans = rPendidikanRepository.findAll();
        assertThat(rPendidikans).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRPendidikans() throws Exception {
        // Initialize the database
        rPendidikanRepository.saveAndFlush(rPendidikan);

        // Get all the rPendidikans
        restRPendidikanMockMvc.perform(get("/api/rPendidikans?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(rPendidikan.getId().intValue())))
                .andExpect(jsonPath("$.[*].id_r_pendidikan").value(hasItem(DEFAULT_ID_R_PENDIDIKAN.toString())))
                .andExpect(jsonPath("$.[*].keterangan").value(hasItem(DEFAULT_KETERANGAN.toString())));
    }

    @Test
    @Transactional
    public void getRPendidikan() throws Exception {
        // Initialize the database
        rPendidikanRepository.saveAndFlush(rPendidikan);

        // Get the rPendidikan
        restRPendidikanMockMvc.perform(get("/api/rPendidikans/{id}", rPendidikan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(rPendidikan.getId().intValue()))
            .andExpect(jsonPath("$.id_r_pendidikan").value(DEFAULT_ID_R_PENDIDIKAN.toString()))
            .andExpect(jsonPath("$.keterangan").value(DEFAULT_KETERANGAN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRPendidikan() throws Exception {
        // Get the rPendidikan
        restRPendidikanMockMvc.perform(get("/api/rPendidikans/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRPendidikan() throws Exception {
        // Initialize the database
        rPendidikanRepository.saveAndFlush(rPendidikan);

		int databaseSizeBeforeUpdate = rPendidikanRepository.findAll().size();

        // Update the rPendidikan
        rPendidikan.setId_r_pendidikan(UPDATED_ID_R_PENDIDIKAN);
        rPendidikan.setKeterangan(UPDATED_KETERANGAN);

        restRPendidikanMockMvc.perform(put("/api/rPendidikans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rPendidikan)))
                .andExpect(status().isOk());

        // Validate the RPendidikan in the database
        List<RPendidikan> rPendidikans = rPendidikanRepository.findAll();
        assertThat(rPendidikans).hasSize(databaseSizeBeforeUpdate);
        RPendidikan testRPendidikan = rPendidikans.get(rPendidikans.size() - 1);
        assertThat(testRPendidikan.getId_r_pendidikan()).isEqualTo(UPDATED_ID_R_PENDIDIKAN);
        assertThat(testRPendidikan.getKeterangan()).isEqualTo(UPDATED_KETERANGAN);
    }

    @Test
    @Transactional
    public void deleteRPendidikan() throws Exception {
        // Initialize the database
        rPendidikanRepository.saveAndFlush(rPendidikan);

		int databaseSizeBeforeDelete = rPendidikanRepository.findAll().size();

        // Get the rPendidikan
        restRPendidikanMockMvc.perform(delete("/api/rPendidikans/{id}", rPendidikan.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RPendidikan> rPendidikans = rPendidikanRepository.findAll();
        assertThat(rPendidikans).hasSize(databaseSizeBeforeDelete - 1);
    }
}
