package id.co.askrindo.aos.web.rest;

import id.co.askrindo.aos.Application;
import id.co.askrindo.aos.domain.RKantor;
import id.co.askrindo.aos.repository.RKantorRepository;
import id.co.askrindo.aos.repository.search.RKantorSearchRepository;

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
 * Test class for the RKantorResource REST controller.
 *
 * @see RKantorResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RKantorResourceIntTest {

    private static final String DEFAULT_ID_KANTOR = "AAAAA";
    private static final String UPDATED_ID_KANTOR = "BBBBB";
    private static final String DEFAULT_KANTOR = "AAAAA";
    private static final String UPDATED_KANTOR = "BBBBB";
    private static final String DEFAULT_ALAMAT = "AAAAA";
    private static final String UPDATED_ALAMAT = "BBBBB";
    private static final String DEFAULT_TELEPON = "AAAAA";
    private static final String UPDATED_TELEPON = "BBBBB";

    @Inject
    private RKantorRepository rKantorRepository;

    @Inject
    private RKantorSearchRepository rKantorSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRKantorMockMvc;

    private RKantor rKantor;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RKantorResource rKantorResource = new RKantorResource();
        ReflectionTestUtils.setField(rKantorResource, "rKantorSearchRepository", rKantorSearchRepository);
        ReflectionTestUtils.setField(rKantorResource, "rKantorRepository", rKantorRepository);
        this.restRKantorMockMvc = MockMvcBuilders.standaloneSetup(rKantorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        rKantor = new RKantor();
        rKantor.setId_kantor(DEFAULT_ID_KANTOR);
        rKantor.setKantor(DEFAULT_KANTOR);
        rKantor.setAlamat(DEFAULT_ALAMAT);
        rKantor.setTelepon(DEFAULT_TELEPON);
    }

    @Test
    @Transactional
    public void createRKantor() throws Exception {
        int databaseSizeBeforeCreate = rKantorRepository.findAll().size();

        // Create the RKantor

        restRKantorMockMvc.perform(post("/api/rKantors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rKantor)))
                .andExpect(status().isCreated());

        // Validate the RKantor in the database
        List<RKantor> rKantors = rKantorRepository.findAll();
        assertThat(rKantors).hasSize(databaseSizeBeforeCreate + 1);
        RKantor testRKantor = rKantors.get(rKantors.size() - 1);
        assertThat(testRKantor.getId_kantor()).isEqualTo(DEFAULT_ID_KANTOR);
        assertThat(testRKantor.getKantor()).isEqualTo(DEFAULT_KANTOR);
        assertThat(testRKantor.getAlamat()).isEqualTo(DEFAULT_ALAMAT);
        assertThat(testRKantor.getTelepon()).isEqualTo(DEFAULT_TELEPON);
    }

    @Test
    @Transactional
    public void checkId_kantorIsRequired() throws Exception {
        int databaseSizeBeforeTest = rKantorRepository.findAll().size();
        // set the field null
        rKantor.setId_kantor(null);

        // Create the RKantor, which fails.

        restRKantorMockMvc.perform(post("/api/rKantors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rKantor)))
                .andExpect(status().isBadRequest());

        List<RKantor> rKantors = rKantorRepository.findAll();
        assertThat(rKantors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRKantors() throws Exception {
        // Initialize the database
        rKantorRepository.saveAndFlush(rKantor);

        // Get all the rKantors
        restRKantorMockMvc.perform(get("/api/rKantors?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(rKantor.getId().intValue())))
                .andExpect(jsonPath("$.[*].id_kantor").value(hasItem(DEFAULT_ID_KANTOR.toString())))
                .andExpect(jsonPath("$.[*].kantor").value(hasItem(DEFAULT_KANTOR.toString())))
                .andExpect(jsonPath("$.[*].alamat").value(hasItem(DEFAULT_ALAMAT.toString())))
                .andExpect(jsonPath("$.[*].telepon").value(hasItem(DEFAULT_TELEPON.toString())));
    }

    @Test
    @Transactional
    public void getRKantor() throws Exception {
        // Initialize the database
        rKantorRepository.saveAndFlush(rKantor);

        // Get the rKantor
        restRKantorMockMvc.perform(get("/api/rKantors/{id}", rKantor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(rKantor.getId().intValue()))
            .andExpect(jsonPath("$.id_kantor").value(DEFAULT_ID_KANTOR.toString()))
            .andExpect(jsonPath("$.kantor").value(DEFAULT_KANTOR.toString()))
            .andExpect(jsonPath("$.alamat").value(DEFAULT_ALAMAT.toString()))
            .andExpect(jsonPath("$.telepon").value(DEFAULT_TELEPON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRKantor() throws Exception {
        // Get the rKantor
        restRKantorMockMvc.perform(get("/api/rKantors/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRKantor() throws Exception {
        // Initialize the database
        rKantorRepository.saveAndFlush(rKantor);

		int databaseSizeBeforeUpdate = rKantorRepository.findAll().size();

        // Update the rKantor
        rKantor.setId_kantor(UPDATED_ID_KANTOR);
        rKantor.setKantor(UPDATED_KANTOR);
        rKantor.setAlamat(UPDATED_ALAMAT);
        rKantor.setTelepon(UPDATED_TELEPON);

        restRKantorMockMvc.perform(put("/api/rKantors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rKantor)))
                .andExpect(status().isOk());

        // Validate the RKantor in the database
        List<RKantor> rKantors = rKantorRepository.findAll();
        assertThat(rKantors).hasSize(databaseSizeBeforeUpdate);
        RKantor testRKantor = rKantors.get(rKantors.size() - 1);
        assertThat(testRKantor.getId_kantor()).isEqualTo(UPDATED_ID_KANTOR);
        assertThat(testRKantor.getKantor()).isEqualTo(UPDATED_KANTOR);
        assertThat(testRKantor.getAlamat()).isEqualTo(UPDATED_ALAMAT);
        assertThat(testRKantor.getTelepon()).isEqualTo(UPDATED_TELEPON);
    }

    @Test
    @Transactional
    public void deleteRKantor() throws Exception {
        // Initialize the database
        rKantorRepository.saveAndFlush(rKantor);

		int databaseSizeBeforeDelete = rKantorRepository.findAll().size();

        // Get the rKantor
        restRKantorMockMvc.perform(delete("/api/rKantors/{id}", rKantor.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RKantor> rKantors = rKantorRepository.findAll();
        assertThat(rKantors).hasSize(databaseSizeBeforeDelete - 1);
    }
}
