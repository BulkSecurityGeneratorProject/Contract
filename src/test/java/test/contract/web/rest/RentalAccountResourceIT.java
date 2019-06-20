package test.contract.web.rest;

import test.contract.ContractApp;
import test.contract.domain.RentalAccount;
import test.contract.repository.RentalAccountRepository;
import test.contract.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static test.contract.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link RentalAccountResource} REST controller.
 */
@SpringBootTest(classes = ContractApp.class)
public class RentalAccountResourceIT {

    private static final String DEFAULT_AR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EN_NAME = "BBBBBBBBBB";

    @Autowired
    private RentalAccountRepository rentalAccountRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restRentalAccountMockMvc;

    private RentalAccount rentalAccount;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RentalAccountResource rentalAccountResource = new RentalAccountResource(rentalAccountRepository);
        this.restRentalAccountMockMvc = MockMvcBuilders.standaloneSetup(rentalAccountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RentalAccount createEntity(EntityManager em) {
        RentalAccount rentalAccount = new RentalAccount()
            .arName(DEFAULT_AR_NAME)
            .enName(DEFAULT_EN_NAME);
        return rentalAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RentalAccount createUpdatedEntity(EntityManager em) {
        RentalAccount rentalAccount = new RentalAccount()
            .arName(UPDATED_AR_NAME)
            .enName(UPDATED_EN_NAME);
        return rentalAccount;
    }

    @BeforeEach
    public void initTest() {
        rentalAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createRentalAccount() throws Exception {
        int databaseSizeBeforeCreate = rentalAccountRepository.findAll().size();

        // Create the RentalAccount
        restRentalAccountMockMvc.perform(post("/api/rental-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentalAccount)))
            .andExpect(status().isCreated());

        // Validate the RentalAccount in the database
        List<RentalAccount> rentalAccountList = rentalAccountRepository.findAll();
        assertThat(rentalAccountList).hasSize(databaseSizeBeforeCreate + 1);
        RentalAccount testRentalAccount = rentalAccountList.get(rentalAccountList.size() - 1);
        assertThat(testRentalAccount.getArName()).isEqualTo(DEFAULT_AR_NAME);
        assertThat(testRentalAccount.getEnName()).isEqualTo(DEFAULT_EN_NAME);
    }

    @Test
    @Transactional
    public void createRentalAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rentalAccountRepository.findAll().size();

        // Create the RentalAccount with an existing ID
        rentalAccount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRentalAccountMockMvc.perform(post("/api/rental-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentalAccount)))
            .andExpect(status().isBadRequest());

        // Validate the RentalAccount in the database
        List<RentalAccount> rentalAccountList = rentalAccountRepository.findAll();
        assertThat(rentalAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRentalAccounts() throws Exception {
        // Initialize the database
        rentalAccountRepository.saveAndFlush(rentalAccount);

        // Get all the rentalAccountList
        restRentalAccountMockMvc.perform(get("/api/rental-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rentalAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].arName").value(hasItem(DEFAULT_AR_NAME.toString())))
            .andExpect(jsonPath("$.[*].enName").value(hasItem(DEFAULT_EN_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getRentalAccount() throws Exception {
        // Initialize the database
        rentalAccountRepository.saveAndFlush(rentalAccount);

        // Get the rentalAccount
        restRentalAccountMockMvc.perform(get("/api/rental-accounts/{id}", rentalAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rentalAccount.getId().intValue()))
            .andExpect(jsonPath("$.arName").value(DEFAULT_AR_NAME.toString()))
            .andExpect(jsonPath("$.enName").value(DEFAULT_EN_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRentalAccount() throws Exception {
        // Get the rentalAccount
        restRentalAccountMockMvc.perform(get("/api/rental-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRentalAccount() throws Exception {
        // Initialize the database
        rentalAccountRepository.saveAndFlush(rentalAccount);

        int databaseSizeBeforeUpdate = rentalAccountRepository.findAll().size();

        // Update the rentalAccount
        RentalAccount updatedRentalAccount = rentalAccountRepository.findById(rentalAccount.getId()).get();
        // Disconnect from session so that the updates on updatedRentalAccount are not directly saved in db
        em.detach(updatedRentalAccount);
        updatedRentalAccount
            .arName(UPDATED_AR_NAME)
            .enName(UPDATED_EN_NAME);

        restRentalAccountMockMvc.perform(put("/api/rental-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRentalAccount)))
            .andExpect(status().isOk());

        // Validate the RentalAccount in the database
        List<RentalAccount> rentalAccountList = rentalAccountRepository.findAll();
        assertThat(rentalAccountList).hasSize(databaseSizeBeforeUpdate);
        RentalAccount testRentalAccount = rentalAccountList.get(rentalAccountList.size() - 1);
        assertThat(testRentalAccount.getArName()).isEqualTo(UPDATED_AR_NAME);
        assertThat(testRentalAccount.getEnName()).isEqualTo(UPDATED_EN_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingRentalAccount() throws Exception {
        int databaseSizeBeforeUpdate = rentalAccountRepository.findAll().size();

        // Create the RentalAccount

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRentalAccountMockMvc.perform(put("/api/rental-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentalAccount)))
            .andExpect(status().isBadRequest());

        // Validate the RentalAccount in the database
        List<RentalAccount> rentalAccountList = rentalAccountRepository.findAll();
        assertThat(rentalAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRentalAccount() throws Exception {
        // Initialize the database
        rentalAccountRepository.saveAndFlush(rentalAccount);

        int databaseSizeBeforeDelete = rentalAccountRepository.findAll().size();

        // Delete the rentalAccount
        restRentalAccountMockMvc.perform(delete("/api/rental-accounts/{id}", rentalAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<RentalAccount> rentalAccountList = rentalAccountRepository.findAll();
        assertThat(rentalAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RentalAccount.class);
        RentalAccount rentalAccount1 = new RentalAccount();
        rentalAccount1.setId(1L);
        RentalAccount rentalAccount2 = new RentalAccount();
        rentalAccount2.setId(rentalAccount1.getId());
        assertThat(rentalAccount1).isEqualTo(rentalAccount2);
        rentalAccount2.setId(2L);
        assertThat(rentalAccount1).isNotEqualTo(rentalAccount2);
        rentalAccount1.setId(null);
        assertThat(rentalAccount1).isNotEqualTo(rentalAccount2);
    }
}
