package id.co.askrindo.aos.web.rest;

import id.co.askrindo.aos.Application;
import id.co.askrindo.aos.domain.RMaritalStatus;
import id.co.askrindo.aos.repository.RMaritalStatusRepository;
import id.co.askrindo.aos.repository.search.RMaritalStatusSearchRepository;

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
 * Test class for the RMaritalStatusResource REST controller.
 *
 * @see RMaritalStatusResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RMaritalStatusResourceIntTest {

    private static final String DEFAULT_ID_R_MARITAL_STATUS = "AAAAA";
    private static final String UPDATED_ID_R_MARITAL_STATUS = "BBBBB";
    private static final String DEFAULT_KETERANGAN = "AAAAA";
    private static final String UPDATED_KETERANGAN = "BBBBB";

    @Inject
    private RMaritalStatusRepository rMaritalStatusRepository;

    @Inject
    private RMaritalStatusSearchRepository rMaritalStatusSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRMaritalStatusMockMvc;

    private RMaritalStatus rMaritalStatus;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RMaritalStatusResource rMaritalStatusResource = new RMaritalStatusResource();
        ReflectionTestUtils.setField(rMaritalStatusResource, "rMaritalStatusSearchRepository", rMaritalStatusSearchRepository);
        ReflectionTestUtils.setField(rMaritalStatusResource, "rMaritalStatusRepository", rMaritalStatusRepository);
        this.restRMaritalStatusMockMvc = MockMvcBuilders.standaloneSetup(rMaritalStatusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        rMaritalStatus = new RMaritalStatus();
        rMaritalStatus.setId_r_marital_status(DEFAULT_ID_R_MARITAL_STATUS);
        rMaritalStatus.setKeterangan(DEFAULT_KETERANGAN);
    }

    @Test
    @Transactional
    public void createRMaritalStatus() throws Exception {
        int databaseSizeBeforeCreate = rMaritalStatusRepository.findAll().size();

        // Create the RMaritalStatus

        restRMaritalStatusMockMvc.perform(post("/api/rMaritalStatuss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rMaritalStatus)))
                .andExpect(status().isCreated());

        // Validate the RMaritalStatus in the database
        List<RMaritalStatus> rMaritalStatuss = rMaritalStatusRepository.findAll();
        assertThat(rMaritalStatuss).hasSize(databaseSizeBeforeCreate + 1);
        RMaritalStatus testRMaritalStatus = rMaritalStatuss.get(rMaritalStatuss.size() - 1);
        assertThat(testRMaritalStatus.getId_r_marital_status()).isEqualTo(DEFAULT_ID_R_MARITAL_STATUS);
        assertThat(testRMaritalStatus.getKeterangan()).isEqualTo(DEFAULT_KETERANGAN);
    }

    @Test
    @Transactional
    public void checkId_r_marital_statusIsRequired() throws Exception {
        int databaseSizeBeforeTest = rMaritalStatusRepository.findAll().size();
        // set the field null
        rMaritalStatus.setId_r_marital_status(null);

        // Create the RMaritalStatus, which fails.

        restRMaritalStatusMockMvc.perform(post("/api/rMaritalStatuss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rMaritalStatus)))
                .andExpect(status().isBadRequest());

        List<RMaritalStatus> rMaritalStatuss = rMaritalStatusRepository.findAll();
        assertThat(rMaritalStatuss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRMaritalStatuss() throws Exception {
        // Initialize the database
        rMaritalStatusRepository.saveAndFlush(rMaritalStatus);

        // Get all the rMaritalStatuss
        restRMaritalStatusMockMvc.perform(get("/api/rMaritalStatuss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(rMaritalStatus.getId().intValue())))
                .andExpect(jsonPath("$.[*].id_r_marital_status").value(hasItem(DEFAULT_ID_R_MARITAL_STATUS.toString())))
                .andExpect(jsonPath("$.[*].keterangan").value(hasItem(DEFAULT_KETERANGAN.toString())));
    }

    @Test
    @Transactional
    public void getRMaritalStatus() throws Exception {
        // Initialize the database
        rMaritalStatusRepository.saveAndFlush(rMaritalStatus);

        // Get the rMaritalStatus
        restRMaritalStatusMockMvc.perform(get("/api/rMaritalStatuss/{id}", rMaritalStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(rMaritalStatus.getId().intValue()))
            .andExpect(jsonPath("$.id_r_marital_status").value(DEFAULT_ID_R_MARITAL_STATUS.toString()))
            .andExpect(jsonPath("$.keterangan").value(DEFAULT_KETERANGAN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRMaritalStatus() throws Exception {
        // Get the rMaritalStatus
        restRMaritalStatusMockMvc.perform(get("/api/rMaritalStatuss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRMaritalStatus() throws Exception {
        // Initialize the database
        rMaritalStatusRepository.saveAndFlush(rMaritalStatus);

		int databaseSizeBeforeUpdate = rMaritalStatusRepository.findAll().size();

        // Update the rMaritalStatus
        rMaritalStatus.setId_r_marital_status(UPDATED_ID_R_MARITAL_STATUS);
        rMaritalStatus.setKeterangan(UPDATED_KETERANGAN);

        restRMaritalStatusMockMvc.perform(put("/api/rMaritalStatuss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rMaritalStatus)))
                .andExpect(status().isOk());

        // Validate the RMaritalStatus in the database
        List<RMaritalStatus> rMaritalStatuss = rMaritalStatusRepository.findAll();
        assertThat(rMaritalStatuss).hasSize(databaseSizeBeforeUpdate);
        RMaritalStatus testRMaritalStatus = rMaritalStatuss.get(rMaritalStatuss.size() - 1);
        assertThat(testRMaritalStatus.getId_r_marital_status()).isEqualTo(UPDATED_ID_R_MARITAL_STATUS);
        assertThat(testRMaritalStatus.getKeterangan()).isEqualTo(UPDATED_KETERANGAN);
    }

    @Test
    @Transactional
    public void deleteRMaritalStatus() throws Exception {
        // Initialize the database
        rMaritalStatusRepository.saveAndFlush(rMaritalStatus);

		int databaseSizeBeforeDelete = rMaritalStatusRepository.findAll().size();

        // Get the rMaritalStatus
        restRMaritalStatusMockMvc.perform(delete("/api/rMaritalStatuss/{id}", rMaritalStatus.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RMaritalStatus> rMaritalStatuss = rMaritalStatusRepository.findAll();
        assertThat(rMaritalStatuss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
