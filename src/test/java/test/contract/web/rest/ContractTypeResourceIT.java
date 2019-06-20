package test.contract.web.rest;

import test.contract.ContractApp;
import test.contract.domain.ContractType;
import test.contract.repository.ContractTypeRepository;
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
 * Integration tests for the {@Link ContractTypeResource} REST controller.
 */
@SpringBootTest(classes = ContractApp.class)
public class ContractTypeResourceIT {

    private static final String DEFAULT_AR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EN_NAME = "BBBBBBBBBB";

    @Autowired
    private ContractTypeRepository contractTypeRepository;

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

    private MockMvc restContractTypeMockMvc;

    private ContractType contractType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContractTypeResource contractTypeResource = new ContractTypeResource(contractTypeRepository);
        this.restContractTypeMockMvc = MockMvcBuilders.standaloneSetup(contractTypeResource)
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
    public static ContractType createEntity(EntityManager em) {
        ContractType contractType = new ContractType()
            .arName(DEFAULT_AR_NAME)
            .enName(DEFAULT_EN_NAME);
        return contractType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContractType createUpdatedEntity(EntityManager em) {
        ContractType contractType = new ContractType()
            .arName(UPDATED_AR_NAME)
            .enName(UPDATED_EN_NAME);
        return contractType;
    }

    @BeforeEach
    public void initTest() {
        contractType = createEntity(em);
    }

    @Test
    @Transactional
    public void createContractType() throws Exception {
        int databaseSizeBeforeCreate = contractTypeRepository.findAll().size();

        // Create the ContractType
        restContractTypeMockMvc.perform(post("/api/contract-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractType)))
            .andExpect(status().isCreated());

        // Validate the ContractType in the database
        List<ContractType> contractTypeList = contractTypeRepository.findAll();
        assertThat(contractTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ContractType testContractType = contractTypeList.get(contractTypeList.size() - 1);
        assertThat(testContractType.getArName()).isEqualTo(DEFAULT_AR_NAME);
        assertThat(testContractType.getEnName()).isEqualTo(DEFAULT_EN_NAME);
    }

    @Test
    @Transactional
    public void createContractTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contractTypeRepository.findAll().size();

        // Create the ContractType with an existing ID
        contractType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContractTypeMockMvc.perform(post("/api/contract-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractType)))
            .andExpect(status().isBadRequest());

        // Validate the ContractType in the database
        List<ContractType> contractTypeList = contractTypeRepository.findAll();
        assertThat(contractTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContractTypes() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get all the contractTypeList
        restContractTypeMockMvc.perform(get("/api/contract-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contractType.getId().intValue())))
            .andExpect(jsonPath("$.[*].arName").value(hasItem(DEFAULT_AR_NAME.toString())))
            .andExpect(jsonPath("$.[*].enName").value(hasItem(DEFAULT_EN_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getContractType() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get the contractType
        restContractTypeMockMvc.perform(get("/api/contract-types/{id}", contractType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contractType.getId().intValue()))
            .andExpect(jsonPath("$.arName").value(DEFAULT_AR_NAME.toString()))
            .andExpect(jsonPath("$.enName").value(DEFAULT_EN_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContractType() throws Exception {
        // Get the contractType
        restContractTypeMockMvc.perform(get("/api/contract-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContractType() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        int databaseSizeBeforeUpdate = contractTypeRepository.findAll().size();

        // Update the contractType
        ContractType updatedContractType = contractTypeRepository.findById(contractType.getId()).get();
        // Disconnect from session so that the updates on updatedContractType are not directly saved in db
        em.detach(updatedContractType);
        updatedContractType
            .arName(UPDATED_AR_NAME)
            .enName(UPDATED_EN_NAME);

        restContractTypeMockMvc.perform(put("/api/contract-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContractType)))
            .andExpect(status().isOk());

        // Validate the ContractType in the database
        List<ContractType> contractTypeList = contractTypeRepository.findAll();
        assertThat(contractTypeList).hasSize(databaseSizeBeforeUpdate);
        ContractType testContractType = contractTypeList.get(contractTypeList.size() - 1);
        assertThat(testContractType.getArName()).isEqualTo(UPDATED_AR_NAME);
        assertThat(testContractType.getEnName()).isEqualTo(UPDATED_EN_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingContractType() throws Exception {
        int databaseSizeBeforeUpdate = contractTypeRepository.findAll().size();

        // Create the ContractType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContractTypeMockMvc.perform(put("/api/contract-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractType)))
            .andExpect(status().isBadRequest());

        // Validate the ContractType in the database
        List<ContractType> contractTypeList = contractTypeRepository.findAll();
        assertThat(contractTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContractType() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        int databaseSizeBeforeDelete = contractTypeRepository.findAll().size();

        // Delete the contractType
        restContractTypeMockMvc.perform(delete("/api/contract-types/{id}", contractType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ContractType> contractTypeList = contractTypeRepository.findAll();
        assertThat(contractTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContractType.class);
        ContractType contractType1 = new ContractType();
        contractType1.setId(1L);
        ContractType contractType2 = new ContractType();
        contractType2.setId(contractType1.getId());
        assertThat(contractType1).isEqualTo(contractType2);
        contractType2.setId(2L);
        assertThat(contractType1).isNotEqualTo(contractType2);
        contractType1.setId(null);
        assertThat(contractType1).isNotEqualTo(contractType2);
    }
}
