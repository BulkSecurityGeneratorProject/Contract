package test.contract.web.rest;

import test.contract.ContractApp;
import test.contract.domain.VehicleType;
import test.contract.repository.VehicleTypeRepository;
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
 * Integration tests for the {@Link VehicleTypeResource} REST controller.
 */
@SpringBootTest(classes = ContractApp.class)
public class VehicleTypeResourceIT {

    private static final String DEFAULT_AR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EN_NAME = "BBBBBBBBBB";

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

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

    private MockMvc restVehicleTypeMockMvc;

    private VehicleType vehicleType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VehicleTypeResource vehicleTypeResource = new VehicleTypeResource(vehicleTypeRepository);
        this.restVehicleTypeMockMvc = MockMvcBuilders.standaloneSetup(vehicleTypeResource)
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
    public static VehicleType createEntity(EntityManager em) {
        VehicleType vehicleType = new VehicleType()
            .arName(DEFAULT_AR_NAME)
            .enName(DEFAULT_EN_NAME);
        return vehicleType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleType createUpdatedEntity(EntityManager em) {
        VehicleType vehicleType = new VehicleType()
            .arName(UPDATED_AR_NAME)
            .enName(UPDATED_EN_NAME);
        return vehicleType;
    }

    @BeforeEach
    public void initTest() {
        vehicleType = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehicleType() throws Exception {
        int databaseSizeBeforeCreate = vehicleTypeRepository.findAll().size();

        // Create the VehicleType
        restVehicleTypeMockMvc.perform(post("/api/vehicle-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleType)))
            .andExpect(status().isCreated());

        // Validate the VehicleType in the database
        List<VehicleType> vehicleTypeList = vehicleTypeRepository.findAll();
        assertThat(vehicleTypeList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleType testVehicleType = vehicleTypeList.get(vehicleTypeList.size() - 1);
        assertThat(testVehicleType.getArName()).isEqualTo(DEFAULT_AR_NAME);
        assertThat(testVehicleType.getEnName()).isEqualTo(DEFAULT_EN_NAME);
    }

    @Test
    @Transactional
    public void createVehicleTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehicleTypeRepository.findAll().size();

        // Create the VehicleType with an existing ID
        vehicleType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleTypeMockMvc.perform(post("/api/vehicle-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleType)))
            .andExpect(status().isBadRequest());

        // Validate the VehicleType in the database
        List<VehicleType> vehicleTypeList = vehicleTypeRepository.findAll();
        assertThat(vehicleTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVehicleTypes() throws Exception {
        // Initialize the database
        vehicleTypeRepository.saveAndFlush(vehicleType);

        // Get all the vehicleTypeList
        restVehicleTypeMockMvc.perform(get("/api/vehicle-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleType.getId().intValue())))
            .andExpect(jsonPath("$.[*].arName").value(hasItem(DEFAULT_AR_NAME.toString())))
            .andExpect(jsonPath("$.[*].enName").value(hasItem(DEFAULT_EN_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getVehicleType() throws Exception {
        // Initialize the database
        vehicleTypeRepository.saveAndFlush(vehicleType);

        // Get the vehicleType
        restVehicleTypeMockMvc.perform(get("/api/vehicle-types/{id}", vehicleType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleType.getId().intValue()))
            .andExpect(jsonPath("$.arName").value(DEFAULT_AR_NAME.toString()))
            .andExpect(jsonPath("$.enName").value(DEFAULT_EN_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVehicleType() throws Exception {
        // Get the vehicleType
        restVehicleTypeMockMvc.perform(get("/api/vehicle-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehicleType() throws Exception {
        // Initialize the database
        vehicleTypeRepository.saveAndFlush(vehicleType);

        int databaseSizeBeforeUpdate = vehicleTypeRepository.findAll().size();

        // Update the vehicleType
        VehicleType updatedVehicleType = vehicleTypeRepository.findById(vehicleType.getId()).get();
        // Disconnect from session so that the updates on updatedVehicleType are not directly saved in db
        em.detach(updatedVehicleType);
        updatedVehicleType
            .arName(UPDATED_AR_NAME)
            .enName(UPDATED_EN_NAME);

        restVehicleTypeMockMvc.perform(put("/api/vehicle-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVehicleType)))
            .andExpect(status().isOk());

        // Validate the VehicleType in the database
        List<VehicleType> vehicleTypeList = vehicleTypeRepository.findAll();
        assertThat(vehicleTypeList).hasSize(databaseSizeBeforeUpdate);
        VehicleType testVehicleType = vehicleTypeList.get(vehicleTypeList.size() - 1);
        assertThat(testVehicleType.getArName()).isEqualTo(UPDATED_AR_NAME);
        assertThat(testVehicleType.getEnName()).isEqualTo(UPDATED_EN_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingVehicleType() throws Exception {
        int databaseSizeBeforeUpdate = vehicleTypeRepository.findAll().size();

        // Create the VehicleType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleTypeMockMvc.perform(put("/api/vehicle-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleType)))
            .andExpect(status().isBadRequest());

        // Validate the VehicleType in the database
        List<VehicleType> vehicleTypeList = vehicleTypeRepository.findAll();
        assertThat(vehicleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVehicleType() throws Exception {
        // Initialize the database
        vehicleTypeRepository.saveAndFlush(vehicleType);

        int databaseSizeBeforeDelete = vehicleTypeRepository.findAll().size();

        // Delete the vehicleType
        restVehicleTypeMockMvc.perform(delete("/api/vehicle-types/{id}", vehicleType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<VehicleType> vehicleTypeList = vehicleTypeRepository.findAll();
        assertThat(vehicleTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleType.class);
        VehicleType vehicleType1 = new VehicleType();
        vehicleType1.setId(1L);
        VehicleType vehicleType2 = new VehicleType();
        vehicleType2.setId(vehicleType1.getId());
        assertThat(vehicleType1).isEqualTo(vehicleType2);
        vehicleType2.setId(2L);
        assertThat(vehicleType1).isNotEqualTo(vehicleType2);
        vehicleType1.setId(null);
        assertThat(vehicleType1).isNotEqualTo(vehicleType2);
    }
}
