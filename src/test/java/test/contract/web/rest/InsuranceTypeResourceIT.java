package test.contract.web.rest;

import test.contract.ContractApp;
import test.contract.domain.InsuranceType;
import test.contract.repository.InsuranceTypeRepository;
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
 * Integration tests for the {@Link InsuranceTypeResource} REST controller.
 */
@SpringBootTest(classes = ContractApp.class)
public class InsuranceTypeResourceIT {

    private static final String DEFAULT_AR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EN_NAME = "BBBBBBBBBB";

    @Autowired
    private InsuranceTypeRepository insuranceTypeRepository;

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

    private MockMvc restInsuranceTypeMockMvc;

    private InsuranceType insuranceType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InsuranceTypeResource insuranceTypeResource = new InsuranceTypeResource(insuranceTypeRepository);
        this.restInsuranceTypeMockMvc = MockMvcBuilders.standaloneSetup(insuranceTypeResource)
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
    public static InsuranceType createEntity(EntityManager em) {
        InsuranceType insuranceType = new InsuranceType()
            .arName(DEFAULT_AR_NAME)
            .enName(DEFAULT_EN_NAME);
        return insuranceType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InsuranceType createUpdatedEntity(EntityManager em) {
        InsuranceType insuranceType = new InsuranceType()
            .arName(UPDATED_AR_NAME)
            .enName(UPDATED_EN_NAME);
        return insuranceType;
    }

    @BeforeEach
    public void initTest() {
        insuranceType = createEntity(em);
    }

    @Test
    @Transactional
    public void createInsuranceType() throws Exception {
        int databaseSizeBeforeCreate = insuranceTypeRepository.findAll().size();

        // Create the InsuranceType
        restInsuranceTypeMockMvc.perform(post("/api/insurance-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceType)))
            .andExpect(status().isCreated());

        // Validate the InsuranceType in the database
        List<InsuranceType> insuranceTypeList = insuranceTypeRepository.findAll();
        assertThat(insuranceTypeList).hasSize(databaseSizeBeforeCreate + 1);
        InsuranceType testInsuranceType = insuranceTypeList.get(insuranceTypeList.size() - 1);
        assertThat(testInsuranceType.getArName()).isEqualTo(DEFAULT_AR_NAME);
        assertThat(testInsuranceType.getEnName()).isEqualTo(DEFAULT_EN_NAME);
    }

    @Test
    @Transactional
    public void createInsuranceTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = insuranceTypeRepository.findAll().size();

        // Create the InsuranceType with an existing ID
        insuranceType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInsuranceTypeMockMvc.perform(post("/api/insurance-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceType)))
            .andExpect(status().isBadRequest());

        // Validate the InsuranceType in the database
        List<InsuranceType> insuranceTypeList = insuranceTypeRepository.findAll();
        assertThat(insuranceTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInsuranceTypes() throws Exception {
        // Initialize the database
        insuranceTypeRepository.saveAndFlush(insuranceType);

        // Get all the insuranceTypeList
        restInsuranceTypeMockMvc.perform(get("/api/insurance-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insuranceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].arName").value(hasItem(DEFAULT_AR_NAME.toString())))
            .andExpect(jsonPath("$.[*].enName").value(hasItem(DEFAULT_EN_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getInsuranceType() throws Exception {
        // Initialize the database
        insuranceTypeRepository.saveAndFlush(insuranceType);

        // Get the insuranceType
        restInsuranceTypeMockMvc.perform(get("/api/insurance-types/{id}", insuranceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(insuranceType.getId().intValue()))
            .andExpect(jsonPath("$.arName").value(DEFAULT_AR_NAME.toString()))
            .andExpect(jsonPath("$.enName").value(DEFAULT_EN_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInsuranceType() throws Exception {
        // Get the insuranceType
        restInsuranceTypeMockMvc.perform(get("/api/insurance-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInsuranceType() throws Exception {
        // Initialize the database
        insuranceTypeRepository.saveAndFlush(insuranceType);

        int databaseSizeBeforeUpdate = insuranceTypeRepository.findAll().size();

        // Update the insuranceType
        InsuranceType updatedInsuranceType = insuranceTypeRepository.findById(insuranceType.getId()).get();
        // Disconnect from session so that the updates on updatedInsuranceType are not directly saved in db
        em.detach(updatedInsuranceType);
        updatedInsuranceType
            .arName(UPDATED_AR_NAME)
            .enName(UPDATED_EN_NAME);

        restInsuranceTypeMockMvc.perform(put("/api/insurance-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInsuranceType)))
            .andExpect(status().isOk());

        // Validate the InsuranceType in the database
        List<InsuranceType> insuranceTypeList = insuranceTypeRepository.findAll();
        assertThat(insuranceTypeList).hasSize(databaseSizeBeforeUpdate);
        InsuranceType testInsuranceType = insuranceTypeList.get(insuranceTypeList.size() - 1);
        assertThat(testInsuranceType.getArName()).isEqualTo(UPDATED_AR_NAME);
        assertThat(testInsuranceType.getEnName()).isEqualTo(UPDATED_EN_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingInsuranceType() throws Exception {
        int databaseSizeBeforeUpdate = insuranceTypeRepository.findAll().size();

        // Create the InsuranceType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInsuranceTypeMockMvc.perform(put("/api/insurance-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceType)))
            .andExpect(status().isBadRequest());

        // Validate the InsuranceType in the database
        List<InsuranceType> insuranceTypeList = insuranceTypeRepository.findAll();
        assertThat(insuranceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInsuranceType() throws Exception {
        // Initialize the database
        insuranceTypeRepository.saveAndFlush(insuranceType);

        int databaseSizeBeforeDelete = insuranceTypeRepository.findAll().size();

        // Delete the insuranceType
        restInsuranceTypeMockMvc.perform(delete("/api/insurance-types/{id}", insuranceType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<InsuranceType> insuranceTypeList = insuranceTypeRepository.findAll();
        assertThat(insuranceTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InsuranceType.class);
        InsuranceType insuranceType1 = new InsuranceType();
        insuranceType1.setId(1L);
        InsuranceType insuranceType2 = new InsuranceType();
        insuranceType2.setId(insuranceType1.getId());
        assertThat(insuranceType1).isEqualTo(insuranceType2);
        insuranceType2.setId(2L);
        assertThat(insuranceType1).isNotEqualTo(insuranceType2);
        insuranceType1.setId(null);
        assertThat(insuranceType1).isNotEqualTo(insuranceType2);
    }
}
