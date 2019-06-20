package test.contract.web.rest;

import test.contract.ContractApp;
import test.contract.domain.LicenseType;
import test.contract.repository.LicenseTypeRepository;
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
 * Integration tests for the {@Link LicenseTypeResource} REST controller.
 */
@SpringBootTest(classes = ContractApp.class)
public class LicenseTypeResourceIT {

    private static final String DEFAULT_AR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EN_NAME = "BBBBBBBBBB";

    @Autowired
    private LicenseTypeRepository licenseTypeRepository;

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

    private MockMvc restLicenseTypeMockMvc;

    private LicenseType licenseType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LicenseTypeResource licenseTypeResource = new LicenseTypeResource(licenseTypeRepository);
        this.restLicenseTypeMockMvc = MockMvcBuilders.standaloneSetup(licenseTypeResource)
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
    public static LicenseType createEntity(EntityManager em) {
        LicenseType licenseType = new LicenseType()
            .arName(DEFAULT_AR_NAME)
            .enName(DEFAULT_EN_NAME);
        return licenseType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LicenseType createUpdatedEntity(EntityManager em) {
        LicenseType licenseType = new LicenseType()
            .arName(UPDATED_AR_NAME)
            .enName(UPDATED_EN_NAME);
        return licenseType;
    }

    @BeforeEach
    public void initTest() {
        licenseType = createEntity(em);
    }

    @Test
    @Transactional
    public void createLicenseType() throws Exception {
        int databaseSizeBeforeCreate = licenseTypeRepository.findAll().size();

        // Create the LicenseType
        restLicenseTypeMockMvc.perform(post("/api/license-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licenseType)))
            .andExpect(status().isCreated());

        // Validate the LicenseType in the database
        List<LicenseType> licenseTypeList = licenseTypeRepository.findAll();
        assertThat(licenseTypeList).hasSize(databaseSizeBeforeCreate + 1);
        LicenseType testLicenseType = licenseTypeList.get(licenseTypeList.size() - 1);
        assertThat(testLicenseType.getArName()).isEqualTo(DEFAULT_AR_NAME);
        assertThat(testLicenseType.getEnName()).isEqualTo(DEFAULT_EN_NAME);
    }

    @Test
    @Transactional
    public void createLicenseTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = licenseTypeRepository.findAll().size();

        // Create the LicenseType with an existing ID
        licenseType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLicenseTypeMockMvc.perform(post("/api/license-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licenseType)))
            .andExpect(status().isBadRequest());

        // Validate the LicenseType in the database
        List<LicenseType> licenseTypeList = licenseTypeRepository.findAll();
        assertThat(licenseTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLicenseTypes() throws Exception {
        // Initialize the database
        licenseTypeRepository.saveAndFlush(licenseType);

        // Get all the licenseTypeList
        restLicenseTypeMockMvc.perform(get("/api/license-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(licenseType.getId().intValue())))
            .andExpect(jsonPath("$.[*].arName").value(hasItem(DEFAULT_AR_NAME.toString())))
            .andExpect(jsonPath("$.[*].enName").value(hasItem(DEFAULT_EN_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getLicenseType() throws Exception {
        // Initialize the database
        licenseTypeRepository.saveAndFlush(licenseType);

        // Get the licenseType
        restLicenseTypeMockMvc.perform(get("/api/license-types/{id}", licenseType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(licenseType.getId().intValue()))
            .andExpect(jsonPath("$.arName").value(DEFAULT_AR_NAME.toString()))
            .andExpect(jsonPath("$.enName").value(DEFAULT_EN_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLicenseType() throws Exception {
        // Get the licenseType
        restLicenseTypeMockMvc.perform(get("/api/license-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLicenseType() throws Exception {
        // Initialize the database
        licenseTypeRepository.saveAndFlush(licenseType);

        int databaseSizeBeforeUpdate = licenseTypeRepository.findAll().size();

        // Update the licenseType
        LicenseType updatedLicenseType = licenseTypeRepository.findById(licenseType.getId()).get();
        // Disconnect from session so that the updates on updatedLicenseType are not directly saved in db
        em.detach(updatedLicenseType);
        updatedLicenseType
            .arName(UPDATED_AR_NAME)
            .enName(UPDATED_EN_NAME);

        restLicenseTypeMockMvc.perform(put("/api/license-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLicenseType)))
            .andExpect(status().isOk());

        // Validate the LicenseType in the database
        List<LicenseType> licenseTypeList = licenseTypeRepository.findAll();
        assertThat(licenseTypeList).hasSize(databaseSizeBeforeUpdate);
        LicenseType testLicenseType = licenseTypeList.get(licenseTypeList.size() - 1);
        assertThat(testLicenseType.getArName()).isEqualTo(UPDATED_AR_NAME);
        assertThat(testLicenseType.getEnName()).isEqualTo(UPDATED_EN_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingLicenseType() throws Exception {
        int databaseSizeBeforeUpdate = licenseTypeRepository.findAll().size();

        // Create the LicenseType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLicenseTypeMockMvc.perform(put("/api/license-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licenseType)))
            .andExpect(status().isBadRequest());

        // Validate the LicenseType in the database
        List<LicenseType> licenseTypeList = licenseTypeRepository.findAll();
        assertThat(licenseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLicenseType() throws Exception {
        // Initialize the database
        licenseTypeRepository.saveAndFlush(licenseType);

        int databaseSizeBeforeDelete = licenseTypeRepository.findAll().size();

        // Delete the licenseType
        restLicenseTypeMockMvc.perform(delete("/api/license-types/{id}", licenseType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<LicenseType> licenseTypeList = licenseTypeRepository.findAll();
        assertThat(licenseTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LicenseType.class);
        LicenseType licenseType1 = new LicenseType();
        licenseType1.setId(1L);
        LicenseType licenseType2 = new LicenseType();
        licenseType2.setId(licenseType1.getId());
        assertThat(licenseType1).isEqualTo(licenseType2);
        licenseType2.setId(2L);
        assertThat(licenseType1).isNotEqualTo(licenseType2);
        licenseType1.setId(null);
        assertThat(licenseType1).isNotEqualTo(licenseType2);
    }
}
