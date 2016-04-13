package id.co.askrindo.aos.web.rest;

import id.co.askrindo.aos.Application;
import id.co.askrindo.aos.domain.RKolektibilitas;
import id.co.askrindo.aos.repository.RKolektibilitasRepository;
import id.co.askrindo.aos.repository.search.RKolektibilitasSearchRepository;

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
 * Test class for the RKolektibilitasResource REST controller.
 *
 * @see RKolektibilitasResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RKolektibilitasResourceIntTest {

    private static final String DEFAULT_ID_R_KOLEKTIBILITAS = "AAAAA";
    private static final String UPDATED_ID_R_KOLEKTIBILITAS = "BBBBB";
    private static final String DEFAULT_KETERANGAN = "AAAAA";
    private static final String UPDATED_KETERANGAN = "BBBBB";

    @Inject
    private RKolektibilitasRepository rKolektibilitasRepository;

    @Inject
    private RKolektibilitasSearchRepository rKolektibilitasSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRKolektibilitasMockMvc;

    private RKolektibilitas rKolektibilitas;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RKolektibilitasResource rKolektibilitasResource = new RKolektibilitasResource();
        ReflectionTestUtils.setField(rKolektibilitasResource, "rKolektibilitasSearchRepository", rKolektibilitasSearchRepository);
        ReflectionTestUtils.setField(rKolektibilitasResource, "rKolektibilitasRepository", rKolektibilitasRepository);
        this.restRKolektibilitasMockMvc = MockMvcBuilders.standaloneSetup(rKolektibilitasResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        rKolektibilitas = new RKolektibilitas();
        rKolektibilitas.setId_r_kolektibilitas(DEFAULT_ID_R_KOLEKTIBILITAS);
        rKolektibilitas.setKeterangan(DEFAULT_KETERANGAN);
    }

    @Test
    @Transactional
    public void createRKolektibilitas() throws Exception {
        int databaseSizeBeforeCreate = rKolektibilitasRepository.findAll().size();

        // Create the RKolektibilitas

        restRKolektibilitasMockMvc.perform(post("/api/rKolektibilitass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rKolektibilitas)))
                .andExpect(status().isCreated());

        // Validate the RKolektibilitas in the database
        List<RKolektibilitas> rKolektibilitass = rKolektibilitasRepository.findAll();
        assertThat(rKolektibilitass).hasSize(databaseSizeBeforeCreate + 1);
        RKolektibilitas testRKolektibilitas = rKolektibilitass.get(rKolektibilitass.size() - 1);
        assertThat(testRKolektibilitas.getId_r_kolektibilitas()).isEqualTo(DEFAULT_ID_R_KOLEKTIBILITAS);
        assertThat(testRKolektibilitas.getKeterangan()).isEqualTo(DEFAULT_KETERANGAN);
    }

    @Test
    @Transactional
    public void checkId_r_kolektibilitasIsRequired() throws Exception {
        int databaseSizeBeforeTest = rKolektibilitasRepository.findAll().size();
        // set the field null
        rKolektibilitas.setId_r_kolektibilitas(null);

        // Create the RKolektibilitas, which fails.

        restRKolektibilitasMockMvc.perform(post("/api/rKolektibilitass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rKolektibilitas)))
                .andExpect(status().isBadRequest());

        List<RKolektibilitas> rKolektibilitass = rKolektibilitasRepository.findAll();
        assertThat(rKolektibilitass).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRKolektibilitass() throws Exception {
        // Initialize the database
        rKolektibilitasRepository.saveAndFlush(rKolektibilitas);

        // Get all the rKolektibilitass
        restRKolektibilitasMockMvc.perform(get("/api/rKolektibilitass?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(rKolektibilitas.getId().intValue())))
                .andExpect(jsonPath("$.[*].id_r_kolektibilitas").value(hasItem(DEFAULT_ID_R_KOLEKTIBILITAS.toString())))
                .andExpect(jsonPath("$.[*].keterangan").value(hasItem(DEFAULT_KETERANGAN.toString())));
    }

    @Test
    @Transactional
    public void getRKolektibilitas() throws Exception {
        // Initialize the database
        rKolektibilitasRepository.saveAndFlush(rKolektibilitas);

        // Get the rKolektibilitas
        restRKolektibilitasMockMvc.perform(get("/api/rKolektibilitass/{id}", rKolektibilitas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(rKolektibilitas.getId().intValue()))
            .andExpect(jsonPath("$.id_r_kolektibilitas").value(DEFAULT_ID_R_KOLEKTIBILITAS.toString()))
            .andExpect(jsonPath("$.keterangan").value(DEFAULT_KETERANGAN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRKolektibilitas() throws Exception {
        // Get the rKolektibilitas
        restRKolektibilitasMockMvc.perform(get("/api/rKolektibilitass/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRKolektibilitas() throws Exception {
        // Initialize the database
        rKolektibilitasRepository.saveAndFlush(rKolektibilitas);

		int databaseSizeBeforeUpdate = rKolektibilitasRepository.findAll().size();

        // Update the rKolektibilitas
        rKolektibilitas.setId_r_kolektibilitas(UPDATED_ID_R_KOLEKTIBILITAS);
        rKolektibilitas.setKeterangan(UPDATED_KETERANGAN);

        restRKolektibilitasMockMvc.perform(put("/api/rKolektibilitass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rKolektibilitas)))
                .andExpect(status().isOk());

        // Validate the RKolektibilitas in the database
        List<RKolektibilitas> rKolektibilitass = rKolektibilitasRepository.findAll();
        assertThat(rKolektibilitass).hasSize(databaseSizeBeforeUpdate);
        RKolektibilitas testRKolektibilitas = rKolektibilitass.get(rKolektibilitass.size() - 1);
        assertThat(testRKolektibilitas.getId_r_kolektibilitas()).isEqualTo(UPDATED_ID_R_KOLEKTIBILITAS);
        assertThat(testRKolektibilitas.getKeterangan()).isEqualTo(UPDATED_KETERANGAN);
    }

    @Test
    @Transactional
    public void deleteRKolektibilitas() throws Exception {
        // Initialize the database
        rKolektibilitasRepository.saveAndFlush(rKolektibilitas);

		int databaseSizeBeforeDelete = rKolektibilitasRepository.findAll().size();

        // Get the rKolektibilitas
        restRKolektibilitasMockMvc.perform(delete("/api/rKolektibilitass/{id}", rKolektibilitas.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RKolektibilitas> rKolektibilitass = rKolektibilitasRepository.findAll();
        assertThat(rKolektibilitass).hasSize(databaseSizeBeforeDelete - 1);
    }
}
