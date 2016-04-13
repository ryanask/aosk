package id.co.askrindo.aos.web.rest;

import id.co.askrindo.aos.Application;
import id.co.askrindo.aos.domain.RNegaraTujuan;
import id.co.askrindo.aos.repository.RNegaraTujuanRepository;
import id.co.askrindo.aos.repository.search.RNegaraTujuanSearchRepository;

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
 * Test class for the RNegaraTujuanResource REST controller.
 *
 * @see RNegaraTujuanResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RNegaraTujuanResourceIntTest {

    private static final String DEFAULT_ID_R_NEGARA_TUJUAN = "AAAAA";
    private static final String UPDATED_ID_R_NEGARA_TUJUAN = "BBBBB";
    private static final String DEFAULT_KETERANGAN = "AAAAA";
    private static final String UPDATED_KETERANGAN = "BBBBB";

    @Inject
    private RNegaraTujuanRepository rNegaraTujuanRepository;

    @Inject
    private RNegaraTujuanSearchRepository rNegaraTujuanSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRNegaraTujuanMockMvc;

    private RNegaraTujuan rNegaraTujuan;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RNegaraTujuanResource rNegaraTujuanResource = new RNegaraTujuanResource();
        ReflectionTestUtils.setField(rNegaraTujuanResource, "rNegaraTujuanSearchRepository", rNegaraTujuanSearchRepository);
        ReflectionTestUtils.setField(rNegaraTujuanResource, "rNegaraTujuanRepository", rNegaraTujuanRepository);
        this.restRNegaraTujuanMockMvc = MockMvcBuilders.standaloneSetup(rNegaraTujuanResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        rNegaraTujuan = new RNegaraTujuan();
        rNegaraTujuan.setId_r_negara_tujuan(DEFAULT_ID_R_NEGARA_TUJUAN);
        rNegaraTujuan.setKeterangan(DEFAULT_KETERANGAN);
    }

    @Test
    @Transactional
    public void createRNegaraTujuan() throws Exception {
        int databaseSizeBeforeCreate = rNegaraTujuanRepository.findAll().size();

        // Create the RNegaraTujuan

        restRNegaraTujuanMockMvc.perform(post("/api/rNegaraTujuans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rNegaraTujuan)))
                .andExpect(status().isCreated());

        // Validate the RNegaraTujuan in the database
        List<RNegaraTujuan> rNegaraTujuans = rNegaraTujuanRepository.findAll();
        assertThat(rNegaraTujuans).hasSize(databaseSizeBeforeCreate + 1);
        RNegaraTujuan testRNegaraTujuan = rNegaraTujuans.get(rNegaraTujuans.size() - 1);
        assertThat(testRNegaraTujuan.getId_r_negara_tujuan()).isEqualTo(DEFAULT_ID_R_NEGARA_TUJUAN);
        assertThat(testRNegaraTujuan.getKeterangan()).isEqualTo(DEFAULT_KETERANGAN);
    }

    @Test
    @Transactional
    public void checkId_r_negara_tujuanIsRequired() throws Exception {
        int databaseSizeBeforeTest = rNegaraTujuanRepository.findAll().size();
        // set the field null
        rNegaraTujuan.setId_r_negara_tujuan(null);

        // Create the RNegaraTujuan, which fails.

        restRNegaraTujuanMockMvc.perform(post("/api/rNegaraTujuans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rNegaraTujuan)))
                .andExpect(status().isBadRequest());

        List<RNegaraTujuan> rNegaraTujuans = rNegaraTujuanRepository.findAll();
        assertThat(rNegaraTujuans).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRNegaraTujuans() throws Exception {
        // Initialize the database
        rNegaraTujuanRepository.saveAndFlush(rNegaraTujuan);

        // Get all the rNegaraTujuans
        restRNegaraTujuanMockMvc.perform(get("/api/rNegaraTujuans?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(rNegaraTujuan.getId().intValue())))
                .andExpect(jsonPath("$.[*].id_r_negara_tujuan").value(hasItem(DEFAULT_ID_R_NEGARA_TUJUAN.toString())))
                .andExpect(jsonPath("$.[*].keterangan").value(hasItem(DEFAULT_KETERANGAN.toString())));
    }

    @Test
    @Transactional
    public void getRNegaraTujuan() throws Exception {
        // Initialize the database
        rNegaraTujuanRepository.saveAndFlush(rNegaraTujuan);

        // Get the rNegaraTujuan
        restRNegaraTujuanMockMvc.perform(get("/api/rNegaraTujuans/{id}", rNegaraTujuan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(rNegaraTujuan.getId().intValue()))
            .andExpect(jsonPath("$.id_r_negara_tujuan").value(DEFAULT_ID_R_NEGARA_TUJUAN.toString()))
            .andExpect(jsonPath("$.keterangan").value(DEFAULT_KETERANGAN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRNegaraTujuan() throws Exception {
        // Get the rNegaraTujuan
        restRNegaraTujuanMockMvc.perform(get("/api/rNegaraTujuans/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRNegaraTujuan() throws Exception {
        // Initialize the database
        rNegaraTujuanRepository.saveAndFlush(rNegaraTujuan);

		int databaseSizeBeforeUpdate = rNegaraTujuanRepository.findAll().size();

        // Update the rNegaraTujuan
        rNegaraTujuan.setId_r_negara_tujuan(UPDATED_ID_R_NEGARA_TUJUAN);
        rNegaraTujuan.setKeterangan(UPDATED_KETERANGAN);

        restRNegaraTujuanMockMvc.perform(put("/api/rNegaraTujuans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rNegaraTujuan)))
                .andExpect(status().isOk());

        // Validate the RNegaraTujuan in the database
        List<RNegaraTujuan> rNegaraTujuans = rNegaraTujuanRepository.findAll();
        assertThat(rNegaraTujuans).hasSize(databaseSizeBeforeUpdate);
        RNegaraTujuan testRNegaraTujuan = rNegaraTujuans.get(rNegaraTujuans.size() - 1);
        assertThat(testRNegaraTujuan.getId_r_negara_tujuan()).isEqualTo(UPDATED_ID_R_NEGARA_TUJUAN);
        assertThat(testRNegaraTujuan.getKeterangan()).isEqualTo(UPDATED_KETERANGAN);
    }

    @Test
    @Transactional
    public void deleteRNegaraTujuan() throws Exception {
        // Initialize the database
        rNegaraTujuanRepository.saveAndFlush(rNegaraTujuan);

		int databaseSizeBeforeDelete = rNegaraTujuanRepository.findAll().size();

        // Get the rNegaraTujuan
        restRNegaraTujuanMockMvc.perform(delete("/api/rNegaraTujuans/{id}", rNegaraTujuan.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RNegaraTujuan> rNegaraTujuans = rNegaraTujuanRepository.findAll();
        assertThat(rNegaraTujuans).hasSize(databaseSizeBeforeDelete - 1);
    }
}
