package id.co.askrindo.aos.web.rest;

import id.co.askrindo.aos.Application;
import id.co.askrindo.aos.domain.RJenisKelamin;
import id.co.askrindo.aos.repository.RJenisKelaminRepository;
import id.co.askrindo.aos.repository.search.RJenisKelaminSearchRepository;

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
 * Test class for the RJenisKelaminResource REST controller.
 *
 * @see RJenisKelaminResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RJenisKelaminResourceIntTest {

    private static final String DEFAULT_ID_R_JENIS_KELAMIN = "AAAAA";
    private static final String UPDATED_ID_R_JENIS_KELAMIN = "BBBBB";
    private static final String DEFAULT_KETERANGAN = "AAAAA";
    private static final String UPDATED_KETERANGAN = "BBBBB";

    @Inject
    private RJenisKelaminRepository rJenisKelaminRepository;

    @Inject
    private RJenisKelaminSearchRepository rJenisKelaminSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRJenisKelaminMockMvc;

    private RJenisKelamin rJenisKelamin;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RJenisKelaminResource rJenisKelaminResource = new RJenisKelaminResource();
        ReflectionTestUtils.setField(rJenisKelaminResource, "rJenisKelaminSearchRepository", rJenisKelaminSearchRepository);
        ReflectionTestUtils.setField(rJenisKelaminResource, "rJenisKelaminRepository", rJenisKelaminRepository);
        this.restRJenisKelaminMockMvc = MockMvcBuilders.standaloneSetup(rJenisKelaminResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        rJenisKelamin = new RJenisKelamin();
        rJenisKelamin.setId_r_jenis_kelamin(DEFAULT_ID_R_JENIS_KELAMIN);
        rJenisKelamin.setKeterangan(DEFAULT_KETERANGAN);
    }

    @Test
    @Transactional
    public void createRJenisKelamin() throws Exception {
        int databaseSizeBeforeCreate = rJenisKelaminRepository.findAll().size();

        // Create the RJenisKelamin

        restRJenisKelaminMockMvc.perform(post("/api/rJenisKelamins")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rJenisKelamin)))
                .andExpect(status().isCreated());

        // Validate the RJenisKelamin in the database
        List<RJenisKelamin> rJenisKelamins = rJenisKelaminRepository.findAll();
        assertThat(rJenisKelamins).hasSize(databaseSizeBeforeCreate + 1);
        RJenisKelamin testRJenisKelamin = rJenisKelamins.get(rJenisKelamins.size() - 1);
        assertThat(testRJenisKelamin.getId_r_jenis_kelamin()).isEqualTo(DEFAULT_ID_R_JENIS_KELAMIN);
        assertThat(testRJenisKelamin.getKeterangan()).isEqualTo(DEFAULT_KETERANGAN);
    }

    @Test
    @Transactional
    public void checkId_r_jenis_kelaminIsRequired() throws Exception {
        int databaseSizeBeforeTest = rJenisKelaminRepository.findAll().size();
        // set the field null
        rJenisKelamin.setId_r_jenis_kelamin(null);

        // Create the RJenisKelamin, which fails.

        restRJenisKelaminMockMvc.perform(post("/api/rJenisKelamins")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rJenisKelamin)))
                .andExpect(status().isBadRequest());

        List<RJenisKelamin> rJenisKelamins = rJenisKelaminRepository.findAll();
        assertThat(rJenisKelamins).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRJenisKelamins() throws Exception {
        // Initialize the database
        rJenisKelaminRepository.saveAndFlush(rJenisKelamin);

        // Get all the rJenisKelamins
        restRJenisKelaminMockMvc.perform(get("/api/rJenisKelamins?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(rJenisKelamin.getId().intValue())))
                .andExpect(jsonPath("$.[*].id_r_jenis_kelamin").value(hasItem(DEFAULT_ID_R_JENIS_KELAMIN.toString())))
                .andExpect(jsonPath("$.[*].keterangan").value(hasItem(DEFAULT_KETERANGAN.toString())));
    }

    @Test
    @Transactional
    public void getRJenisKelamin() throws Exception {
        // Initialize the database
        rJenisKelaminRepository.saveAndFlush(rJenisKelamin);

        // Get the rJenisKelamin
        restRJenisKelaminMockMvc.perform(get("/api/rJenisKelamins/{id}", rJenisKelamin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(rJenisKelamin.getId().intValue()))
            .andExpect(jsonPath("$.id_r_jenis_kelamin").value(DEFAULT_ID_R_JENIS_KELAMIN.toString()))
            .andExpect(jsonPath("$.keterangan").value(DEFAULT_KETERANGAN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRJenisKelamin() throws Exception {
        // Get the rJenisKelamin
        restRJenisKelaminMockMvc.perform(get("/api/rJenisKelamins/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRJenisKelamin() throws Exception {
        // Initialize the database
        rJenisKelaminRepository.saveAndFlush(rJenisKelamin);

		int databaseSizeBeforeUpdate = rJenisKelaminRepository.findAll().size();

        // Update the rJenisKelamin
        rJenisKelamin.setId_r_jenis_kelamin(UPDATED_ID_R_JENIS_KELAMIN);
        rJenisKelamin.setKeterangan(UPDATED_KETERANGAN);

        restRJenisKelaminMockMvc.perform(put("/api/rJenisKelamins")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rJenisKelamin)))
                .andExpect(status().isOk());

        // Validate the RJenisKelamin in the database
        List<RJenisKelamin> rJenisKelamins = rJenisKelaminRepository.findAll();
        assertThat(rJenisKelamins).hasSize(databaseSizeBeforeUpdate);
        RJenisKelamin testRJenisKelamin = rJenisKelamins.get(rJenisKelamins.size() - 1);
        assertThat(testRJenisKelamin.getId_r_jenis_kelamin()).isEqualTo(UPDATED_ID_R_JENIS_KELAMIN);
        assertThat(testRJenisKelamin.getKeterangan()).isEqualTo(UPDATED_KETERANGAN);
    }

    @Test
    @Transactional
    public void deleteRJenisKelamin() throws Exception {
        // Initialize the database
        rJenisKelaminRepository.saveAndFlush(rJenisKelamin);

		int databaseSizeBeforeDelete = rJenisKelaminRepository.findAll().size();

        // Get the rJenisKelamin
        restRJenisKelaminMockMvc.perform(delete("/api/rJenisKelamins/{id}", rJenisKelamin.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RJenisKelamin> rJenisKelamins = rJenisKelaminRepository.findAll();
        assertThat(rJenisKelamins).hasSize(databaseSizeBeforeDelete - 1);
    }
}
