package id.co.askrindo.aos.web.rest;

import id.co.askrindo.aos.Application;
import id.co.askrindo.aos.domain.BankPelaksanaOnline;
import id.co.askrindo.aos.repository.BankPelaksanaOnlineRepository;
import id.co.askrindo.aos.repository.search.BankPelaksanaOnlineSearchRepository;

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
 * Test class for the BankPelaksanaOnlineResource REST controller.
 *
 * @see BankPelaksanaOnlineResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BankPelaksanaOnlineResourceIntTest {

    private static final String DEFAULT_KODE_BANK = "AAAAA";
    private static final String UPDATED_KODE_BANK = "BBBBB";
    private static final String DEFAULT_NAMA_BANK = "AAAAA";
    private static final String UPDATED_NAMA_BANK = "BBBBB";
    private static final String DEFAULT_ID_AKTIFITAS_1 = "AAAAA";
    private static final String UPDATED_ID_AKTIFITAS_1 = "BBBBB";
    private static final String DEFAULT_ID_AKTIFITAS_2 = "AAAAA";
    private static final String UPDATED_ID_AKTIFITAS_2 = "BBBBB";

    @Inject
    private BankPelaksanaOnlineRepository bankPelaksanaOnlineRepository;

    @Inject
    private BankPelaksanaOnlineSearchRepository bankPelaksanaOnlineSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBankPelaksanaOnlineMockMvc;

    private BankPelaksanaOnline bankPelaksanaOnline;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BankPelaksanaOnlineResource bankPelaksanaOnlineResource = new BankPelaksanaOnlineResource();
        ReflectionTestUtils.setField(bankPelaksanaOnlineResource, "bankPelaksanaOnlineSearchRepository", bankPelaksanaOnlineSearchRepository);
        ReflectionTestUtils.setField(bankPelaksanaOnlineResource, "bankPelaksanaOnlineRepository", bankPelaksanaOnlineRepository);
        this.restBankPelaksanaOnlineMockMvc = MockMvcBuilders.standaloneSetup(bankPelaksanaOnlineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        bankPelaksanaOnline = new BankPelaksanaOnline();
        bankPelaksanaOnline.setKode_bank(DEFAULT_KODE_BANK);
        bankPelaksanaOnline.setNama_bank(DEFAULT_NAMA_BANK);
        bankPelaksanaOnline.setId_aktifitas_1(DEFAULT_ID_AKTIFITAS_1);
        bankPelaksanaOnline.setId_aktifitas_2(DEFAULT_ID_AKTIFITAS_2);
    }

    @Test
    @Transactional
    public void createBankPelaksanaOnline() throws Exception {
        int databaseSizeBeforeCreate = bankPelaksanaOnlineRepository.findAll().size();

        // Create the BankPelaksanaOnline

        restBankPelaksanaOnlineMockMvc.perform(post("/api/bankPelaksanaOnlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bankPelaksanaOnline)))
                .andExpect(status().isCreated());

        // Validate the BankPelaksanaOnline in the database
        List<BankPelaksanaOnline> bankPelaksanaOnlines = bankPelaksanaOnlineRepository.findAll();
        assertThat(bankPelaksanaOnlines).hasSize(databaseSizeBeforeCreate + 1);
        BankPelaksanaOnline testBankPelaksanaOnline = bankPelaksanaOnlines.get(bankPelaksanaOnlines.size() - 1);
        assertThat(testBankPelaksanaOnline.getKode_bank()).isEqualTo(DEFAULT_KODE_BANK);
        assertThat(testBankPelaksanaOnline.getNama_bank()).isEqualTo(DEFAULT_NAMA_BANK);
        assertThat(testBankPelaksanaOnline.getId_aktifitas_1()).isEqualTo(DEFAULT_ID_AKTIFITAS_1);
        assertThat(testBankPelaksanaOnline.getId_aktifitas_2()).isEqualTo(DEFAULT_ID_AKTIFITAS_2);
    }

    @Test
    @Transactional
    public void checkKode_bankIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankPelaksanaOnlineRepository.findAll().size();
        // set the field null
        bankPelaksanaOnline.setKode_bank(null);

        // Create the BankPelaksanaOnline, which fails.

        restBankPelaksanaOnlineMockMvc.perform(post("/api/bankPelaksanaOnlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bankPelaksanaOnline)))
                .andExpect(status().isBadRequest());

        List<BankPelaksanaOnline> bankPelaksanaOnlines = bankPelaksanaOnlineRepository.findAll();
        assertThat(bankPelaksanaOnlines).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkId_aktifitas_1IsRequired() throws Exception {
        int databaseSizeBeforeTest = bankPelaksanaOnlineRepository.findAll().size();
        // set the field null
        bankPelaksanaOnline.setId_aktifitas_1(null);

        // Create the BankPelaksanaOnline, which fails.

        restBankPelaksanaOnlineMockMvc.perform(post("/api/bankPelaksanaOnlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bankPelaksanaOnline)))
                .andExpect(status().isBadRequest());

        List<BankPelaksanaOnline> bankPelaksanaOnlines = bankPelaksanaOnlineRepository.findAll();
        assertThat(bankPelaksanaOnlines).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkId_aktifitas_2IsRequired() throws Exception {
        int databaseSizeBeforeTest = bankPelaksanaOnlineRepository.findAll().size();
        // set the field null
        bankPelaksanaOnline.setId_aktifitas_2(null);

        // Create the BankPelaksanaOnline, which fails.

        restBankPelaksanaOnlineMockMvc.perform(post("/api/bankPelaksanaOnlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bankPelaksanaOnline)))
                .andExpect(status().isBadRequest());

        List<BankPelaksanaOnline> bankPelaksanaOnlines = bankPelaksanaOnlineRepository.findAll();
        assertThat(bankPelaksanaOnlines).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBankPelaksanaOnlines() throws Exception {
        // Initialize the database
        bankPelaksanaOnlineRepository.saveAndFlush(bankPelaksanaOnline);

        // Get all the bankPelaksanaOnlines
        restBankPelaksanaOnlineMockMvc.perform(get("/api/bankPelaksanaOnlines?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(bankPelaksanaOnline.getId().intValue())))
                .andExpect(jsonPath("$.[*].kode_bank").value(hasItem(DEFAULT_KODE_BANK.toString())))
                .andExpect(jsonPath("$.[*].nama_bank").value(hasItem(DEFAULT_NAMA_BANK.toString())))
                .andExpect(jsonPath("$.[*].id_aktifitas_1").value(hasItem(DEFAULT_ID_AKTIFITAS_1.toString())))
                .andExpect(jsonPath("$.[*].id_aktifitas_2").value(hasItem(DEFAULT_ID_AKTIFITAS_2.toString())));
    }

    @Test
    @Transactional
    public void getBankPelaksanaOnline() throws Exception {
        // Initialize the database
        bankPelaksanaOnlineRepository.saveAndFlush(bankPelaksanaOnline);

        // Get the bankPelaksanaOnline
        restBankPelaksanaOnlineMockMvc.perform(get("/api/bankPelaksanaOnlines/{id}", bankPelaksanaOnline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(bankPelaksanaOnline.getId().intValue()))
            .andExpect(jsonPath("$.kode_bank").value(DEFAULT_KODE_BANK.toString()))
            .andExpect(jsonPath("$.nama_bank").value(DEFAULT_NAMA_BANK.toString()))
            .andExpect(jsonPath("$.id_aktifitas_1").value(DEFAULT_ID_AKTIFITAS_1.toString()))
            .andExpect(jsonPath("$.id_aktifitas_2").value(DEFAULT_ID_AKTIFITAS_2.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBankPelaksanaOnline() throws Exception {
        // Get the bankPelaksanaOnline
        restBankPelaksanaOnlineMockMvc.perform(get("/api/bankPelaksanaOnlines/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBankPelaksanaOnline() throws Exception {
        // Initialize the database
        bankPelaksanaOnlineRepository.saveAndFlush(bankPelaksanaOnline);

		int databaseSizeBeforeUpdate = bankPelaksanaOnlineRepository.findAll().size();

        // Update the bankPelaksanaOnline
        bankPelaksanaOnline.setKode_bank(UPDATED_KODE_BANK);
        bankPelaksanaOnline.setNama_bank(UPDATED_NAMA_BANK);
        bankPelaksanaOnline.setId_aktifitas_1(UPDATED_ID_AKTIFITAS_1);
        bankPelaksanaOnline.setId_aktifitas_2(UPDATED_ID_AKTIFITAS_2);

        restBankPelaksanaOnlineMockMvc.perform(put("/api/bankPelaksanaOnlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bankPelaksanaOnline)))
                .andExpect(status().isOk());

        // Validate the BankPelaksanaOnline in the database
        List<BankPelaksanaOnline> bankPelaksanaOnlines = bankPelaksanaOnlineRepository.findAll();
        assertThat(bankPelaksanaOnlines).hasSize(databaseSizeBeforeUpdate);
        BankPelaksanaOnline testBankPelaksanaOnline = bankPelaksanaOnlines.get(bankPelaksanaOnlines.size() - 1);
        assertThat(testBankPelaksanaOnline.getKode_bank()).isEqualTo(UPDATED_KODE_BANK);
        assertThat(testBankPelaksanaOnline.getNama_bank()).isEqualTo(UPDATED_NAMA_BANK);
        assertThat(testBankPelaksanaOnline.getId_aktifitas_1()).isEqualTo(UPDATED_ID_AKTIFITAS_1);
        assertThat(testBankPelaksanaOnline.getId_aktifitas_2()).isEqualTo(UPDATED_ID_AKTIFITAS_2);
    }

    @Test
    @Transactional
    public void deleteBankPelaksanaOnline() throws Exception {
        // Initialize the database
        bankPelaksanaOnlineRepository.saveAndFlush(bankPelaksanaOnline);

		int databaseSizeBeforeDelete = bankPelaksanaOnlineRepository.findAll().size();

        // Get the bankPelaksanaOnline
        restBankPelaksanaOnlineMockMvc.perform(delete("/api/bankPelaksanaOnlines/{id}", bankPelaksanaOnline.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BankPelaksanaOnline> bankPelaksanaOnlines = bankPelaksanaOnlineRepository.findAll();
        assertThat(bankPelaksanaOnlines).hasSize(databaseSizeBeforeDelete - 1);
    }
}
