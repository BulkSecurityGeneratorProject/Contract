package test.contract.web.rest;

import test.contract.ContractApp;
import test.contract.domain.VehicleItemStatus;
import test.contract.repository.VehicleItemStatusRepository;
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
 * Integration tests for the {@Link VehicleItemStatusResource} REST controller.
 */
@SpringBootTest(classes = ContractApp.class)
public class VehicleItemStatusResourceIT {

    private static final Integer DEFAULT_AC = 1;
    private static final Integer UPDATED_AC = 2;

    private static final Integer DEFAULT_RADIO_STEREO = 1;
    private static final Integer UPDATED_RADIO_STEREO = 2;

    private static final Integer DEFAULT_SCREEN = 1;
    private static final Integer UPDATED_SCREEN = 2;

    private static final Integer DEFAULT_SPEEDOMETER = 1;
    private static final Integer UPDATED_SPEEDOMETER = 2;

    private static final Integer DEFAULT_CAR_SEATS = 1;
    private static final Integer UPDATED_CAR_SEATS = 2;

    private static final Integer DEFAULT_TIRES = 1;
    private static final Integer UPDATED_TIRES = 2;

    private static final Integer DEFAULT_SPARE_TIRE = 1;
    private static final Integer UPDATED_SPARE_TIRE = 2;

    private static final Integer DEFAULT_SPARE_TIRE_TOOLS = 1;
    private static final Integer UPDATED_SPARE_TIRE_TOOLS = 2;

    private static final Integer DEFAULT_FIRTS_AID_KIT = 1;
    private static final Integer UPDATED_FIRTS_AID_KIT = 2;

    private static final Integer DEFAULT_KEY = 1;
    private static final Integer UPDATED_KEY = 2;

    private static final Integer DEFAULT_FIRE_EXTINGUISHER = 1;
    private static final Integer UPDATED_FIRE_EXTINGUISHER = 2;

    private static final Integer DEFAULT_SAFETY_TRIANGLE = 1;
    private static final Integer UPDATED_SAFETY_TRIANGLE = 2;

    @Autowired
    private VehicleItemStatusRepository vehicleItemStatusRepository;

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

    private MockMvc restVehicleItemStatusMockMvc;

    private VehicleItemStatus vehicleItemStatus;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VehicleItemStatusResource vehicleItemStatusResource = new VehicleItemStatusResource(vehicleItemStatusRepository);
        this.restVehicleItemStatusMockMvc = MockMvcBuilders.standaloneSetup(vehicleItemStatusResource)
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
    public static VehicleItemStatus createEntity(EntityManager em) {
        VehicleItemStatus vehicleItemStatus = new VehicleItemStatus()
            .ac(DEFAULT_AC)
            .radioStereo(DEFAULT_RADIO_STEREO)
            .screen(DEFAULT_SCREEN)
            .speedometer(DEFAULT_SPEEDOMETER)
            .carSeats(DEFAULT_CAR_SEATS)
            .tires(DEFAULT_TIRES)
            .spareTire(DEFAULT_SPARE_TIRE)
            .spareTireTools(DEFAULT_SPARE_TIRE_TOOLS)
            .firtsAidKit(DEFAULT_FIRTS_AID_KIT)
            .key(DEFAULT_KEY)
            .fireExtinguisher(DEFAULT_FIRE_EXTINGUISHER)
            .safetyTriangle(DEFAULT_SAFETY_TRIANGLE);
        return vehicleItemStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleItemStatus createUpdatedEntity(EntityManager em) {
        VehicleItemStatus vehicleItemStatus = new VehicleItemStatus()
            .ac(UPDATED_AC)
            .radioStereo(UPDATED_RADIO_STEREO)
            .screen(UPDATED_SCREEN)
            .speedometer(UPDATED_SPEEDOMETER)
            .carSeats(UPDATED_CAR_SEATS)
            .tires(UPDATED_TIRES)
            .spareTire(UPDATED_SPARE_TIRE)
            .spareTireTools(UPDATED_SPARE_TIRE_TOOLS)
            .firtsAidKit(UPDATED_FIRTS_AID_KIT)
            .key(UPDATED_KEY)
            .fireExtinguisher(UPDATED_FIRE_EXTINGUISHER)
            .safetyTriangle(UPDATED_SAFETY_TRIANGLE);
        return vehicleItemStatus;
    }

    @BeforeEach
    public void initTest() {
        vehicleItemStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehicleItemStatus() throws Exception {
        int databaseSizeBeforeCreate = vehicleItemStatusRepository.findAll().size();

        // Create the VehicleItemStatus
        restVehicleItemStatusMockMvc.perform(post("/api/vehicle-item-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleItemStatus)))
            .andExpect(status().isCreated());

        // Validate the VehicleItemStatus in the database
        List<VehicleItemStatus> vehicleItemStatusList = vehicleItemStatusRepository.findAll();
        assertThat(vehicleItemStatusList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleItemStatus testVehicleItemStatus = vehicleItemStatusList.get(vehicleItemStatusList.size() - 1);
        assertThat(testVehicleItemStatus.getAc()).isEqualTo(DEFAULT_AC);
        assertThat(testVehicleItemStatus.getRadioStereo()).isEqualTo(DEFAULT_RADIO_STEREO);
        assertThat(testVehicleItemStatus.getScreen()).isEqualTo(DEFAULT_SCREEN);
        assertThat(testVehicleItemStatus.getSpeedometer()).isEqualTo(DEFAULT_SPEEDOMETER);
        assertThat(testVehicleItemStatus.getCarSeats()).isEqualTo(DEFAULT_CAR_SEATS);
        assertThat(testVehicleItemStatus.getTires()).isEqualTo(DEFAULT_TIRES);
        assertThat(testVehicleItemStatus.getSpareTire()).isEqualTo(DEFAULT_SPARE_TIRE);
        assertThat(testVehicleItemStatus.getSpareTireTools()).isEqualTo(DEFAULT_SPARE_TIRE_TOOLS);
        assertThat(testVehicleItemStatus.getFirtsAidKit()).isEqualTo(DEFAULT_FIRTS_AID_KIT);
        assertThat(testVehicleItemStatus.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testVehicleItemStatus.getFireExtinguisher()).isEqualTo(DEFAULT_FIRE_EXTINGUISHER);
        assertThat(testVehicleItemStatus.getSafetyTriangle()).isEqualTo(DEFAULT_SAFETY_TRIANGLE);
    }

    @Test
    @Transactional
    public void createVehicleItemStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehicleItemStatusRepository.findAll().size();

        // Create the VehicleItemStatus with an existing ID
        vehicleItemStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleItemStatusMockMvc.perform(post("/api/vehicle-item-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleItemStatus)))
            .andExpect(status().isBadRequest());

        // Validate the VehicleItemStatus in the database
        List<VehicleItemStatus> vehicleItemStatusList = vehicleItemStatusRepository.findAll();
        assertThat(vehicleItemStatusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAcIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleItemStatusRepository.findAll().size();
        // set the field null
        vehicleItemStatus.setAc(null);

        // Create the VehicleItemStatus, which fails.

        restVehicleItemStatusMockMvc.perform(post("/api/vehicle-item-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleItemStatus)))
            .andExpect(status().isBadRequest());

        List<VehicleItemStatus> vehicleItemStatusList = vehicleItemStatusRepository.findAll();
        assertThat(vehicleItemStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRadioStereoIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleItemStatusRepository.findAll().size();
        // set the field null
        vehicleItemStatus.setRadioStereo(null);

        // Create the VehicleItemStatus, which fails.

        restVehicleItemStatusMockMvc.perform(post("/api/vehicle-item-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleItemStatus)))
            .andExpect(status().isBadRequest());

        List<VehicleItemStatus> vehicleItemStatusList = vehicleItemStatusRepository.findAll();
        assertThat(vehicleItemStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkScreenIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleItemStatusRepository.findAll().size();
        // set the field null
        vehicleItemStatus.setScreen(null);

        // Create the VehicleItemStatus, which fails.

        restVehicleItemStatusMockMvc.perform(post("/api/vehicle-item-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleItemStatus)))
            .andExpect(status().isBadRequest());

        List<VehicleItemStatus> vehicleItemStatusList = vehicleItemStatusRepository.findAll();
        assertThat(vehicleItemStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSpeedometerIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleItemStatusRepository.findAll().size();
        // set the field null
        vehicleItemStatus.setSpeedometer(null);

        // Create the VehicleItemStatus, which fails.

        restVehicleItemStatusMockMvc.perform(post("/api/vehicle-item-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleItemStatus)))
            .andExpect(status().isBadRequest());

        List<VehicleItemStatus> vehicleItemStatusList = vehicleItemStatusRepository.findAll();
        assertThat(vehicleItemStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCarSeatsIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleItemStatusRepository.findAll().size();
        // set the field null
        vehicleItemStatus.setCarSeats(null);

        // Create the VehicleItemStatus, which fails.

        restVehicleItemStatusMockMvc.perform(post("/api/vehicle-item-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleItemStatus)))
            .andExpect(status().isBadRequest());

        List<VehicleItemStatus> vehicleItemStatusList = vehicleItemStatusRepository.findAll();
        assertThat(vehicleItemStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTiresIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleItemStatusRepository.findAll().size();
        // set the field null
        vehicleItemStatus.setTires(null);

        // Create the VehicleItemStatus, which fails.

        restVehicleItemStatusMockMvc.perform(post("/api/vehicle-item-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleItemStatus)))
            .andExpect(status().isBadRequest());

        List<VehicleItemStatus> vehicleItemStatusList = vehicleItemStatusRepository.findAll();
        assertThat(vehicleItemStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSpareTireIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleItemStatusRepository.findAll().size();
        // set the field null
        vehicleItemStatus.setSpareTire(null);

        // Create the VehicleItemStatus, which fails.

        restVehicleItemStatusMockMvc.perform(post("/api/vehicle-item-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleItemStatus)))
            .andExpect(status().isBadRequest());

        List<VehicleItemStatus> vehicleItemStatusList = vehicleItemStatusRepository.findAll();
        assertThat(vehicleItemStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSpareTireToolsIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleItemStatusRepository.findAll().size();
        // set the field null
        vehicleItemStatus.setSpareTireTools(null);

        // Create the VehicleItemStatus, which fails.

        restVehicleItemStatusMockMvc.perform(post("/api/vehicle-item-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleItemStatus)))
            .andExpect(status().isBadRequest());

        List<VehicleItemStatus> vehicleItemStatusList = vehicleItemStatusRepository.findAll();
        assertThat(vehicleItemStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirtsAidKitIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleItemStatusRepository.findAll().size();
        // set the field null
        vehicleItemStatus.setFirtsAidKit(null);

        // Create the VehicleItemStatus, which fails.

        restVehicleItemStatusMockMvc.perform(post("/api/vehicle-item-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleItemStatus)))
            .andExpect(status().isBadRequest());

        List<VehicleItemStatus> vehicleItemStatusList = vehicleItemStatusRepository.findAll();
        assertThat(vehicleItemStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleItemStatusRepository.findAll().size();
        // set the field null
        vehicleItemStatus.setKey(null);

        // Create the VehicleItemStatus, which fails.

        restVehicleItemStatusMockMvc.perform(post("/api/vehicle-item-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleItemStatus)))
            .andExpect(status().isBadRequest());

        List<VehicleItemStatus> vehicleItemStatusList = vehicleItemStatusRepository.findAll();
        assertThat(vehicleItemStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFireExtinguisherIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleItemStatusRepository.findAll().size();
        // set the field null
        vehicleItemStatus.setFireExtinguisher(null);

        // Create the VehicleItemStatus, which fails.

        restVehicleItemStatusMockMvc.perform(post("/api/vehicle-item-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleItemStatus)))
            .andExpect(status().isBadRequest());

        List<VehicleItemStatus> vehicleItemStatusList = vehicleItemStatusRepository.findAll();
        assertThat(vehicleItemStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSafetyTriangleIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleItemStatusRepository.findAll().size();
        // set the field null
        vehicleItemStatus.setSafetyTriangle(null);

        // Create the VehicleItemStatus, which fails.

        restVehicleItemStatusMockMvc.perform(post("/api/vehicle-item-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleItemStatus)))
            .andExpect(status().isBadRequest());

        List<VehicleItemStatus> vehicleItemStatusList = vehicleItemStatusRepository.findAll();
        assertThat(vehicleItemStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVehicleItemStatuses() throws Exception {
        // Initialize the database
        vehicleItemStatusRepository.saveAndFlush(vehicleItemStatus);

        // Get all the vehicleItemStatusList
        restVehicleItemStatusMockMvc.perform(get("/api/vehicle-item-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleItemStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].ac").value(hasItem(DEFAULT_AC)))
            .andExpect(jsonPath("$.[*].radioStereo").value(hasItem(DEFAULT_RADIO_STEREO)))
            .andExpect(jsonPath("$.[*].screen").value(hasItem(DEFAULT_SCREEN)))
            .andExpect(jsonPath("$.[*].speedometer").value(hasItem(DEFAULT_SPEEDOMETER)))
            .andExpect(jsonPath("$.[*].carSeats").value(hasItem(DEFAULT_CAR_SEATS)))
            .andExpect(jsonPath("$.[*].tires").value(hasItem(DEFAULT_TIRES)))
            .andExpect(jsonPath("$.[*].spareTire").value(hasItem(DEFAULT_SPARE_TIRE)))
            .andExpect(jsonPath("$.[*].spareTireTools").value(hasItem(DEFAULT_SPARE_TIRE_TOOLS)))
            .andExpect(jsonPath("$.[*].firtsAidKit").value(hasItem(DEFAULT_FIRTS_AID_KIT)))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].fireExtinguisher").value(hasItem(DEFAULT_FIRE_EXTINGUISHER)))
            .andExpect(jsonPath("$.[*].safetyTriangle").value(hasItem(DEFAULT_SAFETY_TRIANGLE)));
    }
    
    @Test
    @Transactional
    public void getVehicleItemStatus() throws Exception {
        // Initialize the database
        vehicleItemStatusRepository.saveAndFlush(vehicleItemStatus);

        // Get the vehicleItemStatus
        restVehicleItemStatusMockMvc.perform(get("/api/vehicle-item-statuses/{id}", vehicleItemStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleItemStatus.getId().intValue()))
            .andExpect(jsonPath("$.ac").value(DEFAULT_AC))
            .andExpect(jsonPath("$.radioStereo").value(DEFAULT_RADIO_STEREO))
            .andExpect(jsonPath("$.screen").value(DEFAULT_SCREEN))
            .andExpect(jsonPath("$.speedometer").value(DEFAULT_SPEEDOMETER))
            .andExpect(jsonPath("$.carSeats").value(DEFAULT_CAR_SEATS))
            .andExpect(jsonPath("$.tires").value(DEFAULT_TIRES))
            .andExpect(jsonPath("$.spareTire").value(DEFAULT_SPARE_TIRE))
            .andExpect(jsonPath("$.spareTireTools").value(DEFAULT_SPARE_TIRE_TOOLS))
            .andExpect(jsonPath("$.firtsAidKit").value(DEFAULT_FIRTS_AID_KIT))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.fireExtinguisher").value(DEFAULT_FIRE_EXTINGUISHER))
            .andExpect(jsonPath("$.safetyTriangle").value(DEFAULT_SAFETY_TRIANGLE));
    }

    @Test
    @Transactional
    public void getNonExistingVehicleItemStatus() throws Exception {
        // Get the vehicleItemStatus
        restVehicleItemStatusMockMvc.perform(get("/api/vehicle-item-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehicleItemStatus() throws Exception {
        // Initialize the database
        vehicleItemStatusRepository.saveAndFlush(vehicleItemStatus);

        int databaseSizeBeforeUpdate = vehicleItemStatusRepository.findAll().size();

        // Update the vehicleItemStatus
        VehicleItemStatus updatedVehicleItemStatus = vehicleItemStatusRepository.findById(vehicleItemStatus.getId()).get();
        // Disconnect from session so that the updates on updatedVehicleItemStatus are not directly saved in db
        em.detach(updatedVehicleItemStatus);
        updatedVehicleItemStatus
            .ac(UPDATED_AC)
            .radioStereo(UPDATED_RADIO_STEREO)
            .screen(UPDATED_SCREEN)
            .speedometer(UPDATED_SPEEDOMETER)
            .carSeats(UPDATED_CAR_SEATS)
            .tires(UPDATED_TIRES)
            .spareTire(UPDATED_SPARE_TIRE)
            .spareTireTools(UPDATED_SPARE_TIRE_TOOLS)
            .firtsAidKit(UPDATED_FIRTS_AID_KIT)
            .key(UPDATED_KEY)
            .fireExtinguisher(UPDATED_FIRE_EXTINGUISHER)
            .safetyTriangle(UPDATED_SAFETY_TRIANGLE);

        restVehicleItemStatusMockMvc.perform(put("/api/vehicle-item-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVehicleItemStatus)))
            .andExpect(status().isOk());

        // Validate the VehicleItemStatus in the database
        List<VehicleItemStatus> vehicleItemStatusList = vehicleItemStatusRepository.findAll();
        assertThat(vehicleItemStatusList).hasSize(databaseSizeBeforeUpdate);
        VehicleItemStatus testVehicleItemStatus = vehicleItemStatusList.get(vehicleItemStatusList.size() - 1);
        assertThat(testVehicleItemStatus.getAc()).isEqualTo(UPDATED_AC);
        assertThat(testVehicleItemStatus.getRadioStereo()).isEqualTo(UPDATED_RADIO_STEREO);
        assertThat(testVehicleItemStatus.getScreen()).isEqualTo(UPDATED_SCREEN);
        assertThat(testVehicleItemStatus.getSpeedometer()).isEqualTo(UPDATED_SPEEDOMETER);
        assertThat(testVehicleItemStatus.getCarSeats()).isEqualTo(UPDATED_CAR_SEATS);
        assertThat(testVehicleItemStatus.getTires()).isEqualTo(UPDATED_TIRES);
        assertThat(testVehicleItemStatus.getSpareTire()).isEqualTo(UPDATED_SPARE_TIRE);
        assertThat(testVehicleItemStatus.getSpareTireTools()).isEqualTo(UPDATED_SPARE_TIRE_TOOLS);
        assertThat(testVehicleItemStatus.getFirtsAidKit()).isEqualTo(UPDATED_FIRTS_AID_KIT);
        assertThat(testVehicleItemStatus.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testVehicleItemStatus.getFireExtinguisher()).isEqualTo(UPDATED_FIRE_EXTINGUISHER);
        assertThat(testVehicleItemStatus.getSafetyTriangle()).isEqualTo(UPDATED_SAFETY_TRIANGLE);
    }

    @Test
    @Transactional
    public void updateNonExistingVehicleItemStatus() throws Exception {
        int databaseSizeBeforeUpdate = vehicleItemStatusRepository.findAll().size();

        // Create the VehicleItemStatus

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleItemStatusMockMvc.perform(put("/api/vehicle-item-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleItemStatus)))
            .andExpect(status().isBadRequest());

        // Validate the VehicleItemStatus in the database
        List<VehicleItemStatus> vehicleItemStatusList = vehicleItemStatusRepository.findAll();
        assertThat(vehicleItemStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVehicleItemStatus() throws Exception {
        // Initialize the database
        vehicleItemStatusRepository.saveAndFlush(vehicleItemStatus);

        int databaseSizeBeforeDelete = vehicleItemStatusRepository.findAll().size();

        // Delete the vehicleItemStatus
        restVehicleItemStatusMockMvc.perform(delete("/api/vehicle-item-statuses/{id}", vehicleItemStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<VehicleItemStatus> vehicleItemStatusList = vehicleItemStatusRepository.findAll();
        assertThat(vehicleItemStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleItemStatus.class);
        VehicleItemStatus vehicleItemStatus1 = new VehicleItemStatus();
        vehicleItemStatus1.setId(1L);
        VehicleItemStatus vehicleItemStatus2 = new VehicleItemStatus();
        vehicleItemStatus2.setId(vehicleItemStatus1.getId());
        assertThat(vehicleItemStatus1).isEqualTo(vehicleItemStatus2);
        vehicleItemStatus2.setId(2L);
        assertThat(vehicleItemStatus1).isNotEqualTo(vehicleItemStatus2);
        vehicleItemStatus1.setId(null);
        assertThat(vehicleItemStatus1).isNotEqualTo(vehicleItemStatus2);
    }
}
