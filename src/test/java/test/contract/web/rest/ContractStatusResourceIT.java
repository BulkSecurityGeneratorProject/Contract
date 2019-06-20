package test.contract.web.rest;

import test.contract.ContractApp;
import test.contract.domain.ContractStatus;
import test.contract.repository.ContractStatusRepository;
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
 * Integration tests for the {@Link ContractStatusResource} REST controller.
 */
@SpringBootTest(classes = ContractApp.class)
public class ContractStatusResourceIT {

    private static final String DEFAULT_AR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EN_NAME = "BBBBBBBBBB";

    @Autowired
    private ContractStatusRepository contractStatusRepository;

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

    private MockMvc restContractStatusMockMvc;

    private ContractStatus contractStatus;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContractStatusResource contractStatusResource = new ContractStatusResource(contractStatusRepository);
        this.restContractStatusMockMvc = MockMvcBuilders.standaloneSetup(contractStatusResource)
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
    public static ContractStatus createEntity(EntityManager em) {
        ContractStatus contractStatus = new ContractStatus()
            .arName(DEFAULT_AR_NAME)
            .enName(DEFAULT_EN_NAME);
        return contractStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContractStatus createUpdatedEntity(EntityManager em) {
        ContractStatus contractStatus = new ContractStatus()
            .arName(UPDATED_AR_NAME)
            .enName(UPDATED_EN_NAME);
        return contractStatus;
    }

    @BeforeEach
    public void initTest() {
        contractStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createContractStatus() throws Exception {
        int databaseSizeBeforeCreate = contractStatusRepository.findAll().size();

        // Create the ContractStatus
        restContractStatusMockMvc.perform(post("/api/contract-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractStatus)))
            .andExpect(status().isCreated());

        // Validate the ContractStatus in the database
        List<ContractStatus> contractStatusList = contractStatusRepository.findAll();
        assertThat(contractStatusList).hasSize(databaseSizeBeforeCreate + 1);
        ContractStatus testContractStatus = contractStatusList.get(contractStatusList.size() - 1);
        assertThat(testContractStatus.getArName()).isEqualTo(DEFAULT_AR_NAME);
        assertThat(testContractStatus.getEnName()).isEqualTo(DEFAULT_EN_NAME);
    }

    @Test
    @Transactional
    public void createContractStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contractStatusRepository.findAll().size();

        // Create the ContractStatus with an existing ID
        contractStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContractStatusMockMvc.perform(post("/api/contract-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractStatus)))
            .andExpect(status().isBadRequest());

        // Validate the ContractStatus in the database
        List<ContractStatus> contractStatusList = contractStatusRepository.findAll();
        assertThat(contractStatusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContractStatuses() throws Exception {
        // Initialize the database
        contractStatusRepository.saveAndFlush(contractStatus);

        // Get all the contractStatusList
        restContractStatusMockMvc.perform(get("/api/contract-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contractStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].arName").value(hasItem(DEFAULT_AR_NAME.toString())))
            .andExpect(jsonPath("$.[*].enName").value(hasItem(DEFAULT_EN_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getContractStatus() throws Exception {
        // Initialize the database
        contractStatusRepository.saveAndFlush(contractStatus);

        // Get the contractStatus
        restContractStatusMockMvc.perform(get("/api/contract-statuses/{id}", contractStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contractStatus.getId().intValue()))
            .andExpect(jsonPath("$.arName").value(DEFAULT_AR_NAME.toString()))
            .andExpect(jsonPath("$.enName").value(DEFAULT_EN_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContractStatus() throws Exception {
        // Get the contractStatus
        restContractStatusMockMvc.perform(get("/api/contract-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContractStatus() throws Exception {
        // Initialize the database
        contractStatusRepository.saveAndFlush(contractStatus);

        int databaseSizeBeforeUpdate = contractStatusRepository.findAll().size();

        // Update the contractStatus
        ContractStatus updatedContractStatus = contractStatusRepository.findById(contractStatus.getId()).get();
        // Disconnect from session so that the updates on updatedContractStatus are not directly saved in db
        em.detach(updatedContractStatus);
        updatedContractStatus
            .arName(UPDATED_AR_NAME)
            .enName(UPDATED_EN_NAME);

        restContractStatusMockMvc.perform(put("/api/contract-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContractStatus)))
            .andExpect(status().isOk());

        // Validate the ContractStatus in the database
        List<ContractStatus> contractStatusList = contractStatusRepository.findAll();
        assertThat(contractStatusList).hasSize(databaseSizeBeforeUpdate);
        ContractStatus testContractStatus = contractStatusList.get(contractStatusList.size() - 1);
        assertThat(testContractStatus.getArName()).isEqualTo(UPDATED_AR_NAME);
        assertThat(testContractStatus.getEnName()).isEqualTo(UPDATED_EN_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingContractStatus() throws Exception {
        int databaseSizeBeforeUpdate = contractStatusRepository.findAll().size();

        // Create the ContractStatus

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContractStatusMockMvc.perform(put("/api/contract-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractStatus)))
            .andExpect(status().isBadRequest());

        // Validate the ContractStatus in the database
        List<ContractStatus> contractStatusList = contractStatusRepository.findAll();
        assertThat(contractStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContractStatus() throws Exception {
        // Initialize the database
        contractStatusRepository.saveAndFlush(contractStatus);

        int databaseSizeBeforeDelete = contractStatusRepository.findAll().size();

        // Delete the contractStatus
        restContractStatusMockMvc.perform(delete("/api/contract-statuses/{id}", contractStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ContractStatus> contractStatusList = contractStatusRepository.findAll();
        assertThat(contractStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContractStatus.class);
        ContractStatus contractStatus1 = new ContractStatus();
        contractStatus1.setId(1L);
        ContractStatus contractStatus2 = new ContractStatus();
        contractStatus2.setId(contractStatus1.getId());
        assertThat(contractStatus1).isEqualTo(contractStatus2);
        contractStatus2.setId(2L);
        assertThat(contractStatus1).isNotEqualTo(contractStatus2);
        contractStatus1.setId(null);
        assertThat(contractStatus1).isNotEqualTo(contractStatus2);
    }
}
