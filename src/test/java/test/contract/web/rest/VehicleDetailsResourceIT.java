package test.contract.web.rest;

import test.contract.ContractApp;
import test.contract.domain.VehicleDetails;
import test.contract.repository.VehicleDetailsRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static test.contract.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link VehicleDetailsResource} REST controller.
 */
@SpringBootTest(classes = ContractApp.class)
public class VehicleDetailsResourceIT {

    private static final String DEFAULT_PLATE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PLATE_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_MANUFACTURE_YEAR = 1;
    private static final Integer UPDATED_MANUFACTURE_YEAR = 2;

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_OPERATION_CARD_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_OPERATION_CARD_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_OPERATION_CARD_EXPIRY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OPERATION_CARD_EXPIRY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_INSURANCE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_INSURANCE_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_INSURANCE_EXPIRY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INSURANCE_EXPIRY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ADDITIONAL_INSURANCE = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_INSURANCE = "BBBBBBBBBB";

    private static final Instant DEFAULT_OIL_CHANGE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OIL_CHANGE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_OIL_CHANGE_KM_DISTANCE = 1;
    private static final Integer UPDATED_OIL_CHANGE_KM_DISTANCE = 2;

    private static final Integer DEFAULT_AVAILABLE_FUEL = 1;
    private static final Integer UPDATED_AVAILABLE_FUEL = 2;

    private static final Integer DEFAULT_ENDURANCE_AMOUNT = 1;
    private static final Integer UPDATED_ENDURANCE_AMOUNT = 2;

    @Autowired
    private VehicleDetailsRepository vehicleDetailsRepository;

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

    private MockMvc restVehicleDetailsMockMvc;

    private VehicleDetails vehicleDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VehicleDetailsResource vehicleDetailsResource = new VehicleDetailsResource(vehicleDetailsRepository);
        this.restVehicleDetailsMockMvc = MockMvcBuilders.standaloneSetup(vehicleDetailsResource)
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
    public static VehicleDetails createEntity(EntityManager em) {
        VehicleDetails vehicleDetails = new VehicleDetails()
            .plateNumber(DEFAULT_PLATE_NUMBER)
            .manufactureYear(DEFAULT_MANUFACTURE_YEAR)
            .color(DEFAULT_COLOR)
            .operationCardNumber(DEFAULT_OPERATION_CARD_NUMBER)
            .operationCardExpiryDate(DEFAULT_OPERATION_CARD_EXPIRY_DATE)
            .insuranceNumber(DEFAULT_INSURANCE_NUMBER)
            .insuranceExpiryDate(DEFAULT_INSURANCE_EXPIRY_DATE)
            .additionalInsurance(DEFAULT_ADDITIONAL_INSURANCE)
            .oilChangeDate(DEFAULT_OIL_CHANGE_DATE)
            .oilChangeKmDistance(DEFAULT_OIL_CHANGE_KM_DISTANCE)
            .availableFuel(DEFAULT_AVAILABLE_FUEL)
            .enduranceAmount(DEFAULT_ENDURANCE_AMOUNT);
        return vehicleDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleDetails createUpdatedEntity(EntityManager em) {
        VehicleDetails vehicleDetails = new VehicleDetails()
            .plateNumber(UPDATED_PLATE_NUMBER)
            .manufactureYear(UPDATED_MANUFACTURE_YEAR)
            .color(UPDATED_COLOR)
            .operationCardNumber(UPDATED_OPERATION_CARD_NUMBER)
            .operationCardExpiryDate(UPDATED_OPERATION_CARD_EXPIRY_DATE)
            .insuranceNumber(UPDATED_INSURANCE_NUMBER)
            .insuranceExpiryDate(UPDATED_INSURANCE_EXPIRY_DATE)
            .additionalInsurance(UPDATED_ADDITIONAL_INSURANCE)
            .oilChangeDate(UPDATED_OIL_CHANGE_DATE)
            .oilChangeKmDistance(UPDATED_OIL_CHANGE_KM_DISTANCE)
            .availableFuel(UPDATED_AVAILABLE_FUEL)
            .enduranceAmount(UPDATED_ENDURANCE_AMOUNT);
        return vehicleDetails;
    }

    @BeforeEach
    public void initTest() {
        vehicleDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehicleDetails() throws Exception {
        int databaseSizeBeforeCreate = vehicleDetailsRepository.findAll().size();

        // Create the VehicleDetails
        restVehicleDetailsMockMvc.perform(post("/api/vehicle-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDetails)))
            .andExpect(status().isCreated());

        // Validate the VehicleDetails in the database
        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleDetails testVehicleDetails = vehicleDetailsList.get(vehicleDetailsList.size() - 1);
        assertThat(testVehicleDetails.getPlateNumber()).isEqualTo(DEFAULT_PLATE_NUMBER);
        assertThat(testVehicleDetails.getManufactureYear()).isEqualTo(DEFAULT_MANUFACTURE_YEAR);
        assertThat(testVehicleDetails.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testVehicleDetails.getOperationCardNumber()).isEqualTo(DEFAULT_OPERATION_CARD_NUMBER);
        assertThat(testVehicleDetails.getOperationCardExpiryDate()).isEqualTo(DEFAULT_OPERATION_CARD_EXPIRY_DATE);
        assertThat(testVehicleDetails.getInsuranceNumber()).isEqualTo(DEFAULT_INSURANCE_NUMBER);
        assertThat(testVehicleDetails.getInsuranceExpiryDate()).isEqualTo(DEFAULT_INSURANCE_EXPIRY_DATE);
        assertThat(testVehicleDetails.getAdditionalInsurance()).isEqualTo(DEFAULT_ADDITIONAL_INSURANCE);
        assertThat(testVehicleDetails.getOilChangeDate()).isEqualTo(DEFAULT_OIL_CHANGE_DATE);
        assertThat(testVehicleDetails.getOilChangeKmDistance()).isEqualTo(DEFAULT_OIL_CHANGE_KM_DISTANCE);
        assertThat(testVehicleDetails.getAvailableFuel()).isEqualTo(DEFAULT_AVAILABLE_FUEL);
        assertThat(testVehicleDetails.getEnduranceAmount()).isEqualTo(DEFAULT_ENDURANCE_AMOUNT);
    }

    @Test
    @Transactional
    public void createVehicleDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehicleDetailsRepository.findAll().size();

        // Create the VehicleDetails with an existing ID
        vehicleDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleDetailsMockMvc.perform(post("/api/vehicle-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDetails)))
            .andExpect(status().isBadRequest());

        // Validate the VehicleDetails in the database
        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPlateNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleDetailsRepository.findAll().size();
        // set the field null
        vehicleDetails.setPlateNumber(null);

        // Create the VehicleDetails, which fails.

        restVehicleDetailsMockMvc.perform(post("/api/vehicle-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDetails)))
            .andExpect(status().isBadRequest());

        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkManufactureYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleDetailsRepository.findAll().size();
        // set the field null
        vehicleDetails.setManufactureYear(null);

        // Create the VehicleDetails, which fails.

        restVehicleDetailsMockMvc.perform(post("/api/vehicle-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDetails)))
            .andExpect(status().isBadRequest());

        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkColorIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleDetailsRepository.findAll().size();
        // set the field null
        vehicleDetails.setColor(null);

        // Create the VehicleDetails, which fails.

        restVehicleDetailsMockMvc.perform(post("/api/vehicle-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDetails)))
            .andExpect(status().isBadRequest());

        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOperationCardNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleDetailsRepository.findAll().size();
        // set the field null
        vehicleDetails.setOperationCardNumber(null);

        // Create the VehicleDetails, which fails.

        restVehicleDetailsMockMvc.perform(post("/api/vehicle-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDetails)))
            .andExpect(status().isBadRequest());

        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOperationCardExpiryDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleDetailsRepository.findAll().size();
        // set the field null
        vehicleDetails.setOperationCardExpiryDate(null);

        // Create the VehicleDetails, which fails.

        restVehicleDetailsMockMvc.perform(post("/api/vehicle-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDetails)))
            .andExpect(status().isBadRequest());

        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInsuranceNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleDetailsRepository.findAll().size();
        // set the field null
        vehicleDetails.setInsuranceNumber(null);

        // Create the VehicleDetails, which fails.

        restVehicleDetailsMockMvc.perform(post("/api/vehicle-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDetails)))
            .andExpect(status().isBadRequest());

        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInsuranceExpiryDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleDetailsRepository.findAll().size();
        // set the field null
        vehicleDetails.setInsuranceExpiryDate(null);

        // Create the VehicleDetails, which fails.

        restVehicleDetailsMockMvc.perform(post("/api/vehicle-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDetails)))
            .andExpect(status().isBadRequest());

        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOilChangeDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleDetailsRepository.findAll().size();
        // set the field null
        vehicleDetails.setOilChangeDate(null);

        // Create the VehicleDetails, which fails.

        restVehicleDetailsMockMvc.perform(post("/api/vehicle-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDetails)))
            .andExpect(status().isBadRequest());

        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOilChangeKmDistanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleDetailsRepository.findAll().size();
        // set the field null
        vehicleDetails.setOilChangeKmDistance(null);

        // Create the VehicleDetails, which fails.

        restVehicleDetailsMockMvc.perform(post("/api/vehicle-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDetails)))
            .andExpect(status().isBadRequest());

        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAvailableFuelIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleDetailsRepository.findAll().size();
        // set the field null
        vehicleDetails.setAvailableFuel(null);

        // Create the VehicleDetails, which fails.

        restVehicleDetailsMockMvc.perform(post("/api/vehicle-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDetails)))
            .andExpect(status().isBadRequest());

        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnduranceAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleDetailsRepository.findAll().size();
        // set the field null
        vehicleDetails.setEnduranceAmount(null);

        // Create the VehicleDetails, which fails.

        restVehicleDetailsMockMvc.perform(post("/api/vehicle-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDetails)))
            .andExpect(status().isBadRequest());

        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVehicleDetails() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList
        restVehicleDetailsMockMvc.perform(get("/api/vehicle-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].plateNumber").value(hasItem(DEFAULT_PLATE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].manufactureYear").value(hasItem(DEFAULT_MANUFACTURE_YEAR)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())))
            .andExpect(jsonPath("$.[*].operationCardNumber").value(hasItem(DEFAULT_OPERATION_CARD_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].operationCardExpiryDate").value(hasItem(DEFAULT_OPERATION_CARD_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].insuranceNumber").value(hasItem(DEFAULT_INSURANCE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].insuranceExpiryDate").value(hasItem(DEFAULT_INSURANCE_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].additionalInsurance").value(hasItem(DEFAULT_ADDITIONAL_INSURANCE.toString())))
            .andExpect(jsonPath("$.[*].oilChangeDate").value(hasItem(DEFAULT_OIL_CHANGE_DATE.toString())))
            .andExpect(jsonPath("$.[*].oilChangeKmDistance").value(hasItem(DEFAULT_OIL_CHANGE_KM_DISTANCE)))
            .andExpect(jsonPath("$.[*].availableFuel").value(hasItem(DEFAULT_AVAILABLE_FUEL)))
            .andExpect(jsonPath("$.[*].enduranceAmount").value(hasItem(DEFAULT_ENDURANCE_AMOUNT)));
    }
    
    @Test
    @Transactional
    public void getVehicleDetails() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get the vehicleDetails
        restVehicleDetailsMockMvc.perform(get("/api/vehicle-details/{id}", vehicleDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleDetails.getId().intValue()))
            .andExpect(jsonPath("$.plateNumber").value(DEFAULT_PLATE_NUMBER.toString()))
            .andExpect(jsonPath("$.manufactureYear").value(DEFAULT_MANUFACTURE_YEAR))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.toString()))
            .andExpect(jsonPath("$.operationCardNumber").value(DEFAULT_OPERATION_CARD_NUMBER.toString()))
            .andExpect(jsonPath("$.operationCardExpiryDate").value(DEFAULT_OPERATION_CARD_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.insuranceNumber").value(DEFAULT_INSURANCE_NUMBER.toString()))
            .andExpect(jsonPath("$.insuranceExpiryDate").value(DEFAULT_INSURANCE_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.additionalInsurance").value(DEFAULT_ADDITIONAL_INSURANCE.toString()))
            .andExpect(jsonPath("$.oilChangeDate").value(DEFAULT_OIL_CHANGE_DATE.toString()))
            .andExpect(jsonPath("$.oilChangeKmDistance").value(DEFAULT_OIL_CHANGE_KM_DISTANCE))
            .andExpect(jsonPath("$.availableFuel").value(DEFAULT_AVAILABLE_FUEL))
            .andExpect(jsonPath("$.enduranceAmount").value(DEFAULT_ENDURANCE_AMOUNT));
    }

    @Test
    @Transactional
    public void getNonExistingVehicleDetails() throws Exception {
        // Get the vehicleDetails
        restVehicleDetailsMockMvc.perform(get("/api/vehicle-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehicleDetails() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        int databaseSizeBeforeUpdate = vehicleDetailsRepository.findAll().size();

        // Update the vehicleDetails
        VehicleDetails updatedVehicleDetails = vehicleDetailsRepository.findById(vehicleDetails.getId()).get();
        // Disconnect from session so that the updates on updatedVehicleDetails are not directly saved in db
        em.detach(updatedVehicleDetails);
        updatedVehicleDetails
            .plateNumber(UPDATED_PLATE_NUMBER)
            .manufactureYear(UPDATED_MANUFACTURE_YEAR)
            .color(UPDATED_COLOR)
            .operationCardNumber(UPDATED_OPERATION_CARD_NUMBER)
            .operationCardExpiryDate(UPDATED_OPERATION_CARD_EXPIRY_DATE)
            .insuranceNumber(UPDATED_INSURANCE_NUMBER)
            .insuranceExpiryDate(UPDATED_INSURANCE_EXPIRY_DATE)
            .additionalInsurance(UPDATED_ADDITIONAL_INSURANCE)
            .oilChangeDate(UPDATED_OIL_CHANGE_DATE)
            .oilChangeKmDistance(UPDATED_OIL_CHANGE_KM_DISTANCE)
            .availableFuel(UPDATED_AVAILABLE_FUEL)
            .enduranceAmount(UPDATED_ENDURANCE_AMOUNT);

        restVehicleDetailsMockMvc.perform(put("/api/vehicle-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVehicleDetails)))
            .andExpect(status().isOk());

        // Validate the VehicleDetails in the database
        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeUpdate);
        VehicleDetails testVehicleDetails = vehicleDetailsList.get(vehicleDetailsList.size() - 1);
        assertThat(testVehicleDetails.getPlateNumber()).isEqualTo(UPDATED_PLATE_NUMBER);
        assertThat(testVehicleDetails.getManufactureYear()).isEqualTo(UPDATED_MANUFACTURE_YEAR);
        assertThat(testVehicleDetails.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testVehicleDetails.getOperationCardNumber()).isEqualTo(UPDATED_OPERATION_CARD_NUMBER);
        assertThat(testVehicleDetails.getOperationCardExpiryDate()).isEqualTo(UPDATED_OPERATION_CARD_EXPIRY_DATE);
        assertThat(testVehicleDetails.getInsuranceNumber()).isEqualTo(UPDATED_INSURANCE_NUMBER);
        assertThat(testVehicleDetails.getInsuranceExpiryDate()).isEqualTo(UPDATED_INSURANCE_EXPIRY_DATE);
        assertThat(testVehicleDetails.getAdditionalInsurance()).isEqualTo(UPDATED_ADDITIONAL_INSURANCE);
        assertThat(testVehicleDetails.getOilChangeDate()).isEqualTo(UPDATED_OIL_CHANGE_DATE);
        assertThat(testVehicleDetails.getOilChangeKmDistance()).isEqualTo(UPDATED_OIL_CHANGE_KM_DISTANCE);
        assertThat(testVehicleDetails.getAvailableFuel()).isEqualTo(UPDATED_AVAILABLE_FUEL);
        assertThat(testVehicleDetails.getEnduranceAmount()).isEqualTo(UPDATED_ENDURANCE_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingVehicleDetails() throws Exception {
        int databaseSizeBeforeUpdate = vehicleDetailsRepository.findAll().size();

        // Create the VehicleDetails

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleDetailsMockMvc.perform(put("/api/vehicle-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDetails)))
            .andExpect(status().isBadRequest());

        // Validate the VehicleDetails in the database
        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVehicleDetails() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        int databaseSizeBeforeDelete = vehicleDetailsRepository.findAll().size();

        // Delete the vehicleDetails
        restVehicleDetailsMockMvc.perform(delete("/api/vehicle-details/{id}", vehicleDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleDetails.class);
        VehicleDetails vehicleDetails1 = new VehicleDetails();
        vehicleDetails1.setId(1L);
        VehicleDetails vehicleDetails2 = new VehicleDetails();
        vehicleDetails2.setId(vehicleDetails1.getId());
        assertThat(vehicleDetails1).isEqualTo(vehicleDetails2);
        vehicleDetails2.setId(2L);
        assertThat(vehicleDetails1).isNotEqualTo(vehicleDetails2);
        vehicleDetails1.setId(null);
        assertThat(vehicleDetails1).isNotEqualTo(vehicleDetails2);
    }
}
