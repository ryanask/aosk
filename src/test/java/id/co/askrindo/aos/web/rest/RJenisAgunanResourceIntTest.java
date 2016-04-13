package id.co.askrindo.aos.web.rest;

import id.co.askrindo.aos.Application;
import id.co.askrindo.aos.domain.RJenisAgunan;
import id.co.askrindo.aos.repository.RJenisAgunanRepository;
import id.co.askrindo.aos.repository.search.RJenisAgunanSearchRepository;

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
 * Test class for the RJenisAgunanResource REST controller.
 *
 * @see RJenisAgunanResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RJenisAgunanResourceIntTest {

    private static final String DEFAULT_ID_R_JENIS_AGUNAN = "AAAAA";
    private static final String UPDATED_ID_R_JENIS_AGUNAN = "BBBBB";
    private static final String DEFAULT_URAIAN = "AAAAA";
    private static final String UPDATED_URAIAN = "BBBBB";

    @Inject
    private RJenisAgunanRepository rJenisAgunanRepository;

    @Inject
    private RJenisAgunanSearchRepository rJenisAgunanSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRJenisAgunanMockMvc;

    private RJenisAgunan rJenisAgunan;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RJenisAgunanResource rJenisAgunanResource = new RJenisAgunanResource();
        ReflectionTestUtils.setField(rJenisAgunanResource, "rJenisAgunanSearchRepository", rJenisAgunanSearchRepository);
        ReflectionTestUtils.setField(rJenisAgunanResource, "rJenisAgunanRepository", rJenisAgunanRepository);
        this.restRJenisAgunanMockMvc = MockMvcBuilders.standaloneSetup(rJenisAgunanResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        rJenisAgunan = new RJenisAgunan();
        rJenisAgunan.setId_r_jenis_agunan(DEFAULT_ID_R_JENIS_AGUNAN);
        rJenisAgunan.setUraian(DEFAULT_URAIAN);
    }

    @Test
    @Transactional
    public void createRJenisAgunan() throws Exception {
        int databaseSizeBeforeCreate = rJenisAgunanRepository.findAll().size();

        // Create the RJenisAgunan

        restRJenisAgunanMockMvc.perform(post("/api/rJenisAgunans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rJenisAgunan)))
                .andExpect(status().isCreated());

        // Validate the RJenisAgunan in the database
        List<RJenisAgunan> rJenisAgunans = rJenisAgunanRepository.findAll();
        assertThat(rJenisAgunans).hasSize(databaseSizeBeforeCreate + 1);
        RJenisAgunan testRJenisAgunan = rJenisAgunans.get(rJenisAgunans.size() - 1);
        assertThat(testRJenisAgunan.getId_r_jenis_agunan()).isEqualTo(DEFAULT_ID_R_JENIS_AGUNAN);
        assertThat(testRJenisAgunan.getUraian()).isEqualTo(DEFAULT_URAIAN);
    }

    @Test
    @Transactional
    public void checkId_r_jenis_agunanIsRequired() throws Exception {
        int databaseSizeBeforeTest = rJenisAgunanRepository.findAll().size();
        // set the field null
        rJenisAgunan.setId_r_jenis_agunan(null);

        // Create the RJenisAgunan, which fails.

        restRJenisAgunanMockMvc.perform(post("/api/rJenisAgunans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rJenisAgunan)))
                .andExpect(status().isBadRequest());

        List<RJenisAgunan> rJenisAgunans = rJenisAgunanRepository.findAll();
        assertThat(rJenisAgunans).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRJenisAgunans() throws Exception {
        // Initialize the database
        rJenisAgunanRepository.saveAndFlush(rJenisAgunan);

        // Get all the rJenisAgunans
        restRJenisAgunanMockMvc.perform(get("/api/rJenisAgunans?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(rJenisAgunan.getId().intValue())))
                .andExpect(jsonPath("$.[*].id_r_jenis_agunan").value(hasItem(DEFAULT_ID_R_JENIS_AGUNAN.toString())))
                .andExpect(jsonPath("$.[*].uraian").value(hasItem(DEFAULT_URAIAN.toString())));
    }

    @Test
    @Transactional
    public void getRJenisAgunan() throws Exception {
        // Initialize the database
        rJenisAgunanRepository.saveAndFlush(rJenisAgunan);

        // Get the rJenisAgunan
        restRJenisAgunanMockMvc.perform(get("/api/rJenisAgunans/{id}", rJenisAgunan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(rJenisAgunan.getId().intValue()))
            .andExpect(jsonPath("$.id_r_jenis_agunan").value(DEFAULT_ID_R_JENIS_AGUNAN.toString()))
            .andExpect(jsonPath("$.uraian").value(DEFAULT_URAIAN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRJenisAgunan() throws Exception {
        // Get the rJenisAgunan
        restRJenisAgunanMockMvc.perform(get("/api/rJenisAgunans/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRJenisAgunan() throws Exception {
        // Initialize the database
        rJenisAgunanRepository.saveAndFlush(rJenisAgunan);

		int databaseSizeBeforeUpdate = rJenisAgunanRepository.findAll().size();

        // Update the rJenisAgunan
        rJenisAgunan.setId_r_jenis_agunan(UPDATED_ID_R_JENIS_AGUNAN);
        rJenisAgunan.setUraian(UPDATED_URAIAN);

        restRJenisAgunanMockMvc.perform(put("/api/rJenisAgunans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rJenisAgunan)))
                .andExpect(status().isOk());

        // Validate the RJenisAgunan in the database
        List<RJenisAgunan> rJenisAgunans = rJenisAgunanRepository.findAll();
        assertThat(rJenisAgunans).hasSize(databaseSizeBeforeUpdate);
        RJenisAgunan testRJenisAgunan = rJenisAgunans.get(rJenisAgunans.size() - 1);
        assertThat(testRJenisAgunan.getId_r_jenis_agunan()).isEqualTo(UPDATED_ID_R_JENIS_AGUNAN);
        assertThat(testRJenisAgunan.getUraian()).isEqualTo(UPDATED_URAIAN);
    }

    @Test
    @Transactional
    public void deleteRJenisAgunan() throws Exception {
        // Initialize the database
        rJenisAgunanRepository.saveAndFlush(rJenisAgunan);

		int databaseSizeBeforeDelete = rJenisAgunanRepository.findAll().size();

        // Get the rJenisAgunan
        restRJenisAgunanMockMvc.perform(delete("/api/rJenisAgunans/{id}", rJenisAgunan.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RJenisAgunan> rJenisAgunans = rJenisAgunanRepository.findAll();
        assertThat(rJenisAgunans).hasSize(databaseSizeBeforeDelete - 1);
    }
}
