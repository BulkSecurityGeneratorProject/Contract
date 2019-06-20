package test.contract.web.rest;

import test.contract.ContractApp;
import test.contract.domain.FuelType;
import test.contract.repository.FuelTypeRepository;
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
 * Integration tests for the {@Link FuelTypeResource} REST controller.
 */
@SpringBootTest(classes = ContractApp.class)
public class FuelTypeResourceIT {

    private static final String DEFAULT_AR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EN_NAME = "BBBBBBBBBB";

    @Autowired
    private FuelTypeRepository fuelTypeRepository;

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

    private MockMvc restFuelTypeMockMvc;

    private FuelType fuelType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FuelTypeResource fuelTypeResource = new FuelTypeResource(fuelTypeRepository);
        this.restFuelTypeMockMvc = MockMvcBuilders.standaloneSetup(fuelTypeResource)
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
    public static FuelType createEntity(EntityManager em) {
        FuelType fuelType = new FuelType()
            .arName(DEFAULT_AR_NAME)
            .enName(DEFAULT_EN_NAME);
        return fuelType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuelType createUpdatedEntity(EntityManager em) {
        FuelType fuelType = new FuelType()
            .arName(UPDATED_AR_NAME)
            .enName(UPDATED_EN_NAME);
        return fuelType;
    }

    @BeforeEach
    public void initTest() {
        fuelType = createEntity(em);
    }

    @Test
    @Transactional
    public void createFuelType() throws Exception {
        int databaseSizeBeforeCreate = fuelTypeRepository.findAll().size();

        // Create the FuelType
        restFuelTypeMockMvc.perform(post("/api/fuel-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fuelType)))
            .andExpect(status().isCreated());

        // Validate the FuelType in the database
        List<FuelType> fuelTypeList = fuelTypeRepository.findAll();
        assertThat(fuelTypeList).hasSize(databaseSizeBeforeCreate + 1);
        FuelType testFuelType = fuelTypeList.get(fuelTypeList.size() - 1);
        assertThat(testFuelType.getArName()).isEqualTo(DEFAULT_AR_NAME);
        assertThat(testFuelType.getEnName()).isEqualTo(DEFAULT_EN_NAME);
    }

    @Test
    @Transactional
    public void createFuelTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fuelTypeRepository.findAll().size();

        // Create the FuelType with an existing ID
        fuelType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuelTypeMockMvc.perform(post("/api/fuel-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fuelType)))
            .andExpect(status().isBadRequest());

        // Validate the FuelType in the database
        List<FuelType> fuelTypeList = fuelTypeRepository.findAll();
        assertThat(fuelTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFuelTypes() throws Exception {
        // Initialize the database
        fuelTypeRepository.saveAndFlush(fuelType);

        // Get all the fuelTypeList
        restFuelTypeMockMvc.perform(get("/api/fuel-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fuelType.getId().intValue())))
            .andExpect(jsonPath("$.[*].arName").value(hasItem(DEFAULT_AR_NAME.toString())))
            .andExpect(jsonPath("$.[*].enName").value(hasItem(DEFAULT_EN_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getFuelType() throws Exception {
        // Initialize the database
        fuelTypeRepository.saveAndFlush(fuelType);

        // Get the fuelType
        restFuelTypeMockMvc.perform(get("/api/fuel-types/{id}", fuelType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fuelType.getId().intValue()))
            .andExpect(jsonPath("$.arName").value(DEFAULT_AR_NAME.toString()))
            .andExpect(jsonPath("$.enName").value(DEFAULT_EN_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFuelType() throws Exception {
        // Get the fuelType
        restFuelTypeMockMvc.perform(get("/api/fuel-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFuelType() throws Exception {
        // Initialize the database
        fuelTypeRepository.saveAndFlush(fuelType);

        int databaseSizeBeforeUpdate = fuelTypeRepository.findAll().size();

        // Update the fuelType
        FuelType updatedFuelType = fuelTypeRepository.findById(fuelType.getId()).get();
        // Disconnect from session so that the updates on updatedFuelType are not directly saved in db
        em.detach(updatedFuelType);
        updatedFuelType
            .arName(UPDATED_AR_NAME)
            .enName(UPDATED_EN_NAME);

        restFuelTypeMockMvc.perform(put("/api/fuel-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFuelType)))
            .andExpect(status().isOk());

        // Validate the FuelType in the database
        List<FuelType> fuelTypeList = fuelTypeRepository.findAll();
        assertThat(fuelTypeList).hasSize(databaseSizeBeforeUpdate);
        FuelType testFuelType = fuelTypeList.get(fuelTypeList.size() - 1);
        assertThat(testFuelType.getArName()).isEqualTo(UPDATED_AR_NAME);
        assertThat(testFuelType.getEnName()).isEqualTo(UPDATED_EN_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingFuelType() throws Exception {
        int databaseSizeBeforeUpdate = fuelTypeRepository.findAll().size();

        // Create the FuelType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuelTypeMockMvc.perform(put("/api/fuel-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fuelType)))
            .andExpect(status().isBadRequest());

        // Validate the FuelType in the database
        List<FuelType> fuelTypeList = fuelTypeRepository.findAll();
        assertThat(fuelTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFuelType() throws Exception {
        // Initialize the database
        fuelTypeRepository.saveAndFlush(fuelType);

        int databaseSizeBeforeDelete = fuelTypeRepository.findAll().size();

        // Delete the fuelType
        restFuelTypeMockMvc.perform(delete("/api/fuel-types/{id}", fuelType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<FuelType> fuelTypeList = fuelTypeRepository.findAll();
        assertThat(fuelTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuelType.class);
        FuelType fuelType1 = new FuelType();
        fuelType1.setId(1L);
        FuelType fuelType2 = new FuelType();
        fuelType2.setId(fuelType1.getId());
        assertThat(fuelType1).isEqualTo(fuelType2);
        fuelType2.setId(2L);
        assertThat(fuelType1).isNotEqualTo(fuelType2);
        fuelType1.setId(null);
        assertThat(fuelType1).isNotEqualTo(fuelType2);
    }
}
