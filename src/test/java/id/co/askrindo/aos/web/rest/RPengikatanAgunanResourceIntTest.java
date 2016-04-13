package id.co.askrindo.aos.web.rest;

import id.co.askrindo.aos.Application;
import id.co.askrindo.aos.domain.RPengikatanAgunan;
import id.co.askrindo.aos.repository.RPengikatanAgunanRepository;
import id.co.askrindo.aos.repository.search.RPengikatanAgunanSearchRepository;

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
 * Test class for the RPengikatanAgunanResource REST controller.
 *
 * @see RPengikatanAgunanResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RPengikatanAgunanResourceIntTest {


    private static final Integer DEFAULT_ID_R_PENGIKATAN_AGUNAN = 1;
    private static final Integer UPDATED_ID_R_PENGIKATAN_AGUNAN = 2;
    private static final String DEFAULT_URAIAN = "AAAAA";
    private static final String UPDATED_URAIAN = "BBBBB";

    @Inject
    private RPengikatanAgunanRepository rPengikatanAgunanRepository;

    @Inject
    private RPengikatanAgunanSearchRepository rPengikatanAgunanSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRPengikatanAgunanMockMvc;

    private RPengikatanAgunan rPengikatanAgunan;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RPengikatanAgunanResource rPengikatanAgunanResource = new RPengikatanAgunanResource();
        ReflectionTestUtils.setField(rPengikatanAgunanResource, "rPengikatanAgunanSearchRepository", rPengikatanAgunanSearchRepository);
        ReflectionTestUtils.setField(rPengikatanAgunanResource, "rPengikatanAgunanRepository", rPengikatanAgunanRepository);
        this.restRPengikatanAgunanMockMvc = MockMvcBuilders.standaloneSetup(rPengikatanAgunanResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        rPengikatanAgunan = new RPengikatanAgunan();
        rPengikatanAgunan.setId_r_pengikatan_agunan(DEFAULT_ID_R_PENGIKATAN_AGUNAN);
        rPengikatanAgunan.setUraian(DEFAULT_URAIAN);
    }

    @Test
    @Transactional
    public void createRPengikatanAgunan() throws Exception {
        int databaseSizeBeforeCreate = rPengikatanAgunanRepository.findAll().size();

        // Create the RPengikatanAgunan

        restRPengikatanAgunanMockMvc.perform(post("/api/rPengikatanAgunans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rPengikatanAgunan)))
                .andExpect(status().isCreated());

        // Validate the RPengikatanAgunan in the database
        List<RPengikatanAgunan> rPengikatanAgunans = rPengikatanAgunanRepository.findAll();
        assertThat(rPengikatanAgunans).hasSize(databaseSizeBeforeCreate + 1);
        RPengikatanAgunan testRPengikatanAgunan = rPengikatanAgunans.get(rPengikatanAgunans.size() - 1);
        assertThat(testRPengikatanAgunan.getId_r_pengikatan_agunan()).isEqualTo(DEFAULT_ID_R_PENGIKATAN_AGUNAN);
        assertThat(testRPengikatanAgunan.getUraian()).isEqualTo(DEFAULT_URAIAN);
    }

    @Test
    @Transactional
    public void checkId_r_pengikatan_agunanIsRequired() throws Exception {
        int databaseSizeBeforeTest = rPengikatanAgunanRepository.findAll().size();
        // set the field null
        rPengikatanAgunan.setId_r_pengikatan_agunan(null);

        // Create the RPengikatanAgunan, which fails.

        restRPengikatanAgunanMockMvc.perform(post("/api/rPengikatanAgunans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rPengikatanAgunan)))
                .andExpect(status().isBadRequest());

        List<RPengikatanAgunan> rPengikatanAgunans = rPengikatanAgunanRepository.findAll();
        assertThat(rPengikatanAgunans).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRPengikatanAgunans() throws Exception {
        // Initialize the database
        rPengikatanAgunanRepository.saveAndFlush(rPengikatanAgunan);

        // Get all the rPengikatanAgunans
        restRPengikatanAgunanMockMvc.perform(get("/api/rPengikatanAgunans?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(rPengikatanAgunan.getId().intValue())))
                .andExpect(jsonPath("$.[*].id_r_pengikatan_agunan").value(hasItem(DEFAULT_ID_R_PENGIKATAN_AGUNAN)))
                .andExpect(jsonPath("$.[*].uraian").value(hasItem(DEFAULT_URAIAN.toString())));
    }

    @Test
    @Transactional
    public void getRPengikatanAgunan() throws Exception {
        // Initialize the database
        rPengikatanAgunanRepository.saveAndFlush(rPengikatanAgunan);

        // Get the rPengikatanAgunan
        restRPengikatanAgunanMockMvc.perform(get("/api/rPengikatanAgunans/{id}", rPengikatanAgunan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(rPengikatanAgunan.getId().intValue()))
            .andExpect(jsonPath("$.id_r_pengikatan_agunan").value(DEFAULT_ID_R_PENGIKATAN_AGUNAN))
            .andExpect(jsonPath("$.uraian").value(DEFAULT_URAIAN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRPengikatanAgunan() throws Exception {
        // Get the rPengikatanAgunan
        restRPengikatanAgunanMockMvc.perform(get("/api/rPengikatanAgunans/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRPengikatanAgunan() throws Exception {
        // Initialize the database
        rPengikatanAgunanRepository.saveAndFlush(rPengikatanAgunan);

		int databaseSizeBeforeUpdate = rPengikatanAgunanRepository.findAll().size();

        // Update the rPengikatanAgunan
        rPengikatanAgunan.setId_r_pengikatan_agunan(UPDATED_ID_R_PENGIKATAN_AGUNAN);
        rPengikatanAgunan.setUraian(UPDATED_URAIAN);

        restRPengikatanAgunanMockMvc.perform(put("/api/rPengikatanAgunans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rPengikatanAgunan)))
                .andExpect(status().isOk());

        // Validate the RPengikatanAgunan in the database
        List<RPengikatanAgunan> rPengikatanAgunans = rPengikatanAgunanRepository.findAll();
        assertThat(rPengikatanAgunans).hasSize(databaseSizeBeforeUpdate);
        RPengikatanAgunan testRPengikatanAgunan = rPengikatanAgunans.get(rPengikatanAgunans.size() - 1);
        assertThat(testRPengikatanAgunan.getId_r_pengikatan_agunan()).isEqualTo(UPDATED_ID_R_PENGIKATAN_AGUNAN);
        assertThat(testRPengikatanAgunan.getUraian()).isEqualTo(UPDATED_URAIAN);
    }

    @Test
    @Transactional
    public void deleteRPengikatanAgunan() throws Exception {
        // Initialize the database
        rPengikatanAgunanRepository.saveAndFlush(rPengikatanAgunan);

		int databaseSizeBeforeDelete = rPengikatanAgunanRepository.findAll().size();

        // Get the rPengikatanAgunan
        restRPengikatanAgunanMockMvc.perform(delete("/api/rPengikatanAgunans/{id}", rPengikatanAgunan.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RPengikatanAgunan> rPengikatanAgunans = rPengikatanAgunanRepository.findAll();
        assertThat(rPengikatanAgunans).hasSize(databaseSizeBeforeDelete - 1);
    }
}
