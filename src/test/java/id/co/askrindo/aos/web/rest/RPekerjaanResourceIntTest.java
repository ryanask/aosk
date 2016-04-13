package id.co.askrindo.aos.web.rest;

import id.co.askrindo.aos.Application;
import id.co.askrindo.aos.domain.RPekerjaan;
import id.co.askrindo.aos.repository.RPekerjaanRepository;
import id.co.askrindo.aos.repository.search.RPekerjaanSearchRepository;

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
 * Test class for the RPekerjaanResource REST controller.
 *
 * @see RPekerjaanResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RPekerjaanResourceIntTest {

    private static final String DEFAULT_ID_R_PEKERJAAN = "AAAAA";
    private static final String UPDATED_ID_R_PEKERJAAN = "BBBBB";
    private static final String DEFAULT_KETERANGAN = "AAAAA";
    private static final String UPDATED_KETERANGAN = "BBBBB";

    @Inject
    private RPekerjaanRepository rPekerjaanRepository;

    @Inject
    private RPekerjaanSearchRepository rPekerjaanSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRPekerjaanMockMvc;

    private RPekerjaan rPekerjaan;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RPekerjaanResource rPekerjaanResource = new RPekerjaanResource();
        ReflectionTestUtils.setField(rPekerjaanResource, "rPekerjaanSearchRepository", rPekerjaanSearchRepository);
        ReflectionTestUtils.setField(rPekerjaanResource, "rPekerjaanRepository", rPekerjaanRepository);
        this.restRPekerjaanMockMvc = MockMvcBuilders.standaloneSetup(rPekerjaanResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        rPekerjaan = new RPekerjaan();
        rPekerjaan.setId_r_pekerjaan(DEFAULT_ID_R_PEKERJAAN);
        rPekerjaan.setKeterangan(DEFAULT_KETERANGAN);
    }

    @Test
    @Transactional
    public void createRPekerjaan() throws Exception {
        int databaseSizeBeforeCreate = rPekerjaanRepository.findAll().size();

        // Create the RPekerjaan

        restRPekerjaanMockMvc.perform(post("/api/rPekerjaans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rPekerjaan)))
                .andExpect(status().isCreated());

        // Validate the RPekerjaan in the database
        List<RPekerjaan> rPekerjaans = rPekerjaanRepository.findAll();
        assertThat(rPekerjaans).hasSize(databaseSizeBeforeCreate + 1);
        RPekerjaan testRPekerjaan = rPekerjaans.get(rPekerjaans.size() - 1);
        assertThat(testRPekerjaan.getId_r_pekerjaan()).isEqualTo(DEFAULT_ID_R_PEKERJAAN);
        assertThat(testRPekerjaan.getKeterangan()).isEqualTo(DEFAULT_KETERANGAN);
    }

    @Test
    @Transactional
    public void checkId_r_pekerjaanIsRequired() throws Exception {
        int databaseSizeBeforeTest = rPekerjaanRepository.findAll().size();
        // set the field null
        rPekerjaan.setId_r_pekerjaan(null);

        // Create the RPekerjaan, which fails.

        restRPekerjaanMockMvc.perform(post("/api/rPekerjaans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rPekerjaan)))
                .andExpect(status().isBadRequest());

        List<RPekerjaan> rPekerjaans = rPekerjaanRepository.findAll();
        assertThat(rPekerjaans).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRPekerjaans() throws Exception {
        // Initialize the database
        rPekerjaanRepository.saveAndFlush(rPekerjaan);

        // Get all the rPekerjaans
        restRPekerjaanMockMvc.perform(get("/api/rPekerjaans?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(rPekerjaan.getId().intValue())))
                .andExpect(jsonPath("$.[*].id_r_pekerjaan").value(hasItem(DEFAULT_ID_R_PEKERJAAN.toString())))
                .andExpect(jsonPath("$.[*].keterangan").value(hasItem(DEFAULT_KETERANGAN.toString())));
    }

    @Test
    @Transactional
    public void getRPekerjaan() throws Exception {
        // Initialize the database
        rPekerjaanRepository.saveAndFlush(rPekerjaan);

        // Get the rPekerjaan
        restRPekerjaanMockMvc.perform(get("/api/rPekerjaans/{id}", rPekerjaan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(rPekerjaan.getId().intValue()))
            .andExpect(jsonPath("$.id_r_pekerjaan").value(DEFAULT_ID_R_PEKERJAAN.toString()))
            .andExpect(jsonPath("$.keterangan").value(DEFAULT_KETERANGAN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRPekerjaan() throws Exception {
        // Get the rPekerjaan
        restRPekerjaanMockMvc.perform(get("/api/rPekerjaans/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRPekerjaan() throws Exception {
        // Initialize the database
        rPekerjaanRepository.saveAndFlush(rPekerjaan);

		int databaseSizeBeforeUpdate = rPekerjaanRepository.findAll().size();

        // Update the rPekerjaan
        rPekerjaan.setId_r_pekerjaan(UPDATED_ID_R_PEKERJAAN);
        rPekerjaan.setKeterangan(UPDATED_KETERANGAN);

        restRPekerjaanMockMvc.perform(put("/api/rPekerjaans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rPekerjaan)))
                .andExpect(status().isOk());

        // Validate the RPekerjaan in the database
        List<RPekerjaan> rPekerjaans = rPekerjaanRepository.findAll();
        assertThat(rPekerjaans).hasSize(databaseSizeBeforeUpdate);
        RPekerjaan testRPekerjaan = rPekerjaans.get(rPekerjaans.size() - 1);
        assertThat(testRPekerjaan.getId_r_pekerjaan()).isEqualTo(UPDATED_ID_R_PEKERJAAN);
        assertThat(testRPekerjaan.getKeterangan()).isEqualTo(UPDATED_KETERANGAN);
    }

    @Test
    @Transactional
    public void deleteRPekerjaan() throws Exception {
        // Initialize the database
        rPekerjaanRepository.saveAndFlush(rPekerjaan);

		int databaseSizeBeforeDelete = rPekerjaanRepository.findAll().size();

        // Get the rPekerjaan
        restRPekerjaanMockMvc.perform(delete("/api/rPekerjaans/{id}", rPekerjaan.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RPekerjaan> rPekerjaans = rPekerjaanRepository.findAll();
        assertThat(rPekerjaans).hasSize(databaseSizeBeforeDelete - 1);
    }
}
