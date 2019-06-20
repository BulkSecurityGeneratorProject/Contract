package test.contract.web.rest;

import test.contract.ContractApp;
import test.contract.domain.RentContract;
import test.contract.repository.RentContractRepository;
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
 * Integration tests for the {@Link RentContractResource} REST controller.
 */
@SpringBootTest(classes = ContractApp.class)
public class RentContractResourceIT {

    private static final Long DEFAULT_CONTRACT_NUMBER = 1L;
    private static final Long UPDATED_CONTRACT_NUMBER = 2L;

    private static final Instant DEFAULT_CONTRACT_SIGN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CONTRACT_SIGN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CONTRACT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CONTRACT_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CONTRACT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CONTRACT_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CONTRACT_SIGN_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_SIGN_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_EXTENDED_TO = "AAAAAAAAAA";
    private static final String UPDATED_EXTENDED_TO = "BBBBBBBBBB";

    private static final Integer DEFAULT_EXTENDED_TIMES = 1;
    private static final Integer UPDATED_EXTENDED_TIMES = 2;

    private static final Integer DEFAULT_RENT_DURATION = 1;
    private static final Integer UPDATED_RENT_DURATION = 2;

    private static final Double DEFAULT_RENT_DAY_COST = 1D;
    private static final Double UPDATED_RENT_DAY_COST = 2D;

    private static final Double DEFAULT_RENT_HOUR_COST = 1D;
    private static final Double UPDATED_RENT_HOUR_COST = 2D;

    private static final Double DEFAULT_ODOMETER_KM_BEFORE = 1D;
    private static final Double UPDATED_ODOMETER_KM_BEFORE = 2D;

    private static final Integer DEFAULT_ALLOWED_LATE_HOURS = 1;
    private static final Integer UPDATED_ALLOWED_LATE_HOURS = 2;

    private static final Double DEFAULT_LATE_HOUR_COST = 1D;
    private static final Double UPDATED_LATE_HOUR_COST = 2D;

    private static final Double DEFAULT_ALLOWED_KM_PER_DAY = 1D;
    private static final Double UPDATED_ALLOWED_KM_PER_DAY = 2D;

    private static final Double DEFAULT_ALLOWED_KM_PER_HOUR = 1D;
    private static final Double UPDATED_ALLOWED_KM_PER_HOUR = 2D;

    private static final Double DEFAULT_CAR_TRANSFER_COST = 1D;
    private static final Double UPDATED_CAR_TRANSFER_COST = 2D;

    private static final String DEFAULT_RECEIVE_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVE_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_RETURN_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_RETURN_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_EARLY_RETURN_POLICY = "AAAAAAAAAA";
    private static final String UPDATED_EARLY_RETURN_POLICY = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRACT_EXTENSION_MECHANISM = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_EXTENSION_MECHANISM = "BBBBBBBBBB";

    private static final String DEFAULT_ACCIDENTS_AND_FAULT_REPORTING_MECHANISM = "AAAAAAAAAA";
    private static final String UPDATED_ACCIDENTS_AND_FAULT_REPORTING_MECHANISM = "BBBBBBBBBB";

    private static final Double DEFAULT_ODOMETER_KM_AFTER = 1D;
    private static final Double UPDATED_ODOMETER_KM_AFTER = 2D;

    private static final Double DEFAULT_LATE_HOURS = 1D;
    private static final Double UPDATED_LATE_HOURS = 2D;

    private static final Instant DEFAULT_OIL_CHANGE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OIL_CHANGE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private RentContractRepository rentContractRepository;

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

    private MockMvc restRentContractMockMvc;

    private RentContract rentContract;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RentContractResource rentContractResource = new RentContractResource(rentContractRepository);
        this.restRentContractMockMvc = MockMvcBuilders.standaloneSetup(rentContractResource)
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
    public static RentContract createEntity(EntityManager em) {
        RentContract rentContract = new RentContract()
            .contractNumber(DEFAULT_CONTRACT_NUMBER)
            .contractSignDate(DEFAULT_CONTRACT_SIGN_DATE)
            .contractStartDate(DEFAULT_CONTRACT_START_DATE)
            .contractEndDate(DEFAULT_CONTRACT_END_DATE)
            .contractSignLocation(DEFAULT_CONTRACT_SIGN_LOCATION)
            .extendedTo(DEFAULT_EXTENDED_TO)
            .extendedTimes(DEFAULT_EXTENDED_TIMES)
            .rentDuration(DEFAULT_RENT_DURATION)
            .rentDayCost(DEFAULT_RENT_DAY_COST)
            .rentHourCost(DEFAULT_RENT_HOUR_COST)
            .odometerKmBefore(DEFAULT_ODOMETER_KM_BEFORE)
            .allowedLateHours(DEFAULT_ALLOWED_LATE_HOURS)
            .lateHourCost(DEFAULT_LATE_HOUR_COST)
            .allowedKmPerDay(DEFAULT_ALLOWED_KM_PER_DAY)
            .allowedKmPerHour(DEFAULT_ALLOWED_KM_PER_HOUR)
            .carTransferCost(DEFAULT_CAR_TRANSFER_COST)
            .receiveLocation(DEFAULT_RECEIVE_LOCATION)
            .returnLocation(DEFAULT_RETURN_LOCATION)
            .earlyReturnPolicy(DEFAULT_EARLY_RETURN_POLICY)
            .contractExtensionMechanism(DEFAULT_CONTRACT_EXTENSION_MECHANISM)
            .accidentsAndFaultReportingMechanism(DEFAULT_ACCIDENTS_AND_FAULT_REPORTING_MECHANISM)
            .odometerKmAfter(DEFAULT_ODOMETER_KM_AFTER)
            .lateHours(DEFAULT_LATE_HOURS)
            .oilChangeTime(DEFAULT_OIL_CHANGE_TIME);
        return rentContract;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RentContract createUpdatedEntity(EntityManager em) {
        RentContract rentContract = new RentContract()
            .contractNumber(UPDATED_CONTRACT_NUMBER)
            .contractSignDate(UPDATED_CONTRACT_SIGN_DATE)
            .contractStartDate(UPDATED_CONTRACT_START_DATE)
            .contractEndDate(UPDATED_CONTRACT_END_DATE)
            .contractSignLocation(UPDATED_CONTRACT_SIGN_LOCATION)
            .extendedTo(UPDATED_EXTENDED_TO)
            .extendedTimes(UPDATED_EXTENDED_TIMES)
            .rentDuration(UPDATED_RENT_DURATION)
            .rentDayCost(UPDATED_RENT_DAY_COST)
            .rentHourCost(UPDATED_RENT_HOUR_COST)
            .odometerKmBefore(UPDATED_ODOMETER_KM_BEFORE)
            .allowedLateHours(UPDATED_ALLOWED_LATE_HOURS)
            .lateHourCost(UPDATED_LATE_HOUR_COST)
            .allowedKmPerDay(UPDATED_ALLOWED_KM_PER_DAY)
            .allowedKmPerHour(UPDATED_ALLOWED_KM_PER_HOUR)
            .carTransferCost(UPDATED_CAR_TRANSFER_COST)
            .receiveLocation(UPDATED_RECEIVE_LOCATION)
            .returnLocation(UPDATED_RETURN_LOCATION)
            .earlyReturnPolicy(UPDATED_EARLY_RETURN_POLICY)
            .contractExtensionMechanism(UPDATED_CONTRACT_EXTENSION_MECHANISM)
            .accidentsAndFaultReportingMechanism(UPDATED_ACCIDENTS_AND_FAULT_REPORTING_MECHANISM)
            .odometerKmAfter(UPDATED_ODOMETER_KM_AFTER)
            .lateHours(UPDATED_LATE_HOURS)
            .oilChangeTime(UPDATED_OIL_CHANGE_TIME);
        return rentContract;
    }

    @BeforeEach
    public void initTest() {
        rentContract = createEntity(em);
    }

    @Test
    @Transactional
    public void createRentContract() throws Exception {
        int databaseSizeBeforeCreate = rentContractRepository.findAll().size();

        // Create the RentContract
        restRentContractMockMvc.perform(post("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentContract)))
            .andExpect(status().isCreated());

        // Validate the RentContract in the database
        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeCreate + 1);
        RentContract testRentContract = rentContractList.get(rentContractList.size() - 1);
        assertThat(testRentContract.getContractNumber()).isEqualTo(DEFAULT_CONTRACT_NUMBER);
        assertThat(testRentContract.getContractSignDate()).isEqualTo(DEFAULT_CONTRACT_SIGN_DATE);
        assertThat(testRentContract.getContractStartDate()).isEqualTo(DEFAULT_CONTRACT_START_DATE);
        assertThat(testRentContract.getContractEndDate()).isEqualTo(DEFAULT_CONTRACT_END_DATE);
        assertThat(testRentContract.getContractSignLocation()).isEqualTo(DEFAULT_CONTRACT_SIGN_LOCATION);
        assertThat(testRentContract.getExtendedTo()).isEqualTo(DEFAULT_EXTENDED_TO);
        assertThat(testRentContract.getExtendedTimes()).isEqualTo(DEFAULT_EXTENDED_TIMES);
        assertThat(testRentContract.getRentDuration()).isEqualTo(DEFAULT_RENT_DURATION);
        assertThat(testRentContract.getRentDayCost()).isEqualTo(DEFAULT_RENT_DAY_COST);
        assertThat(testRentContract.getRentHourCost()).isEqualTo(DEFAULT_RENT_HOUR_COST);
        assertThat(testRentContract.getOdometerKmBefore()).isEqualTo(DEFAULT_ODOMETER_KM_BEFORE);
        assertThat(testRentContract.getAllowedLateHours()).isEqualTo(DEFAULT_ALLOWED_LATE_HOURS);
        assertThat(testRentContract.getLateHourCost()).isEqualTo(DEFAULT_LATE_HOUR_COST);
        assertThat(testRentContract.getAllowedKmPerDay()).isEqualTo(DEFAULT_ALLOWED_KM_PER_DAY);
        assertThat(testRentContract.getAllowedKmPerHour()).isEqualTo(DEFAULT_ALLOWED_KM_PER_HOUR);
        assertThat(testRentContract.getCarTransferCost()).isEqualTo(DEFAULT_CAR_TRANSFER_COST);
        assertThat(testRentContract.getReceiveLocation()).isEqualTo(DEFAULT_RECEIVE_LOCATION);
        assertThat(testRentContract.getReturnLocation()).isEqualTo(DEFAULT_RETURN_LOCATION);
        assertThat(testRentContract.getEarlyReturnPolicy()).isEqualTo(DEFAULT_EARLY_RETURN_POLICY);
        assertThat(testRentContract.getContractExtensionMechanism()).isEqualTo(DEFAULT_CONTRACT_EXTENSION_MECHANISM);
        assertThat(testRentContract.getAccidentsAndFaultReportingMechanism()).isEqualTo(DEFAULT_ACCIDENTS_AND_FAULT_REPORTING_MECHANISM);
        assertThat(testRentContract.getOdometerKmAfter()).isEqualTo(DEFAULT_ODOMETER_KM_AFTER);
        assertThat(testRentContract.getLateHours()).isEqualTo(DEFAULT_LATE_HOURS);
        assertThat(testRentContract.getOilChangeTime()).isEqualTo(DEFAULT_OIL_CHANGE_TIME);
    }

    @Test
    @Transactional
    public void createRentContractWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rentContractRepository.findAll().size();

        // Create the RentContract with an existing ID
        rentContract.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRentContractMockMvc.perform(post("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentContract)))
            .andExpect(status().isBadRequest());

        // Validate the RentContract in the database
        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkContractNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentContractRepository.findAll().size();
        // set the field null
        rentContract.setContractNumber(null);

        // Create the RentContract, which fails.

        restRentContractMockMvc.perform(post("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentContract)))
            .andExpect(status().isBadRequest());

        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContractSignDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentContractRepository.findAll().size();
        // set the field null
        rentContract.setContractSignDate(null);

        // Create the RentContract, which fails.

        restRentContractMockMvc.perform(post("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentContract)))
            .andExpect(status().isBadRequest());

        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContractStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentContractRepository.findAll().size();
        // set the field null
        rentContract.setContractStartDate(null);

        // Create the RentContract, which fails.

        restRentContractMockMvc.perform(post("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentContract)))
            .andExpect(status().isBadRequest());

        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContractEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentContractRepository.findAll().size();
        // set the field null
        rentContract.setContractEndDate(null);

        // Create the RentContract, which fails.

        restRentContractMockMvc.perform(post("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentContract)))
            .andExpect(status().isBadRequest());

        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContractSignLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentContractRepository.findAll().size();
        // set the field null
        rentContract.setContractSignLocation(null);

        // Create the RentContract, which fails.

        restRentContractMockMvc.perform(post("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentContract)))
            .andExpect(status().isBadRequest());

        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExtendedToIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentContractRepository.findAll().size();
        // set the field null
        rentContract.setExtendedTo(null);

        // Create the RentContract, which fails.

        restRentContractMockMvc.perform(post("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentContract)))
            .andExpect(status().isBadRequest());

        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExtendedTimesIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentContractRepository.findAll().size();
        // set the field null
        rentContract.setExtendedTimes(null);

        // Create the RentContract, which fails.

        restRentContractMockMvc.perform(post("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentContract)))
            .andExpect(status().isBadRequest());

        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRentDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentContractRepository.findAll().size();
        // set the field null
        rentContract.setRentDuration(null);

        // Create the RentContract, which fails.

        restRentContractMockMvc.perform(post("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentContract)))
            .andExpect(status().isBadRequest());

        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRentDayCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentContractRepository.findAll().size();
        // set the field null
        rentContract.setRentDayCost(null);

        // Create the RentContract, which fails.

        restRentContractMockMvc.perform(post("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentContract)))
            .andExpect(status().isBadRequest());

        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRentHourCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentContractRepository.findAll().size();
        // set the field null
        rentContract.setRentHourCost(null);

        // Create the RentContract, which fails.

        restRentContractMockMvc.perform(post("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentContract)))
            .andExpect(status().isBadRequest());

        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOdometerKmBeforeIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentContractRepository.findAll().size();
        // set the field null
        rentContract.setOdometerKmBefore(null);

        // Create the RentContract, which fails.

        restRentContractMockMvc.perform(post("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentContract)))
            .andExpect(status().isBadRequest());

        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAllowedLateHoursIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentContractRepository.findAll().size();
        // set the field null
        rentContract.setAllowedLateHours(null);

        // Create the RentContract, which fails.

        restRentContractMockMvc.perform(post("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentContract)))
            .andExpect(status().isBadRequest());

        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLateHourCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentContractRepository.findAll().size();
        // set the field null
        rentContract.setLateHourCost(null);

        // Create the RentContract, which fails.

        restRentContractMockMvc.perform(post("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentContract)))
            .andExpect(status().isBadRequest());

        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAllowedKmPerDayIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentContractRepository.findAll().size();
        // set the field null
        rentContract.setAllowedKmPerDay(null);

        // Create the RentContract, which fails.

        restRentContractMockMvc.perform(post("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentContract)))
            .andExpect(status().isBadRequest());

        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAllowedKmPerHourIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentContractRepository.findAll().size();
        // set the field null
        rentContract.setAllowedKmPerHour(null);

        // Create the RentContract, which fails.

        restRentContractMockMvc.perform(post("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentContract)))
            .andExpect(status().isBadRequest());

        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCarTransferCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentContractRepository.findAll().size();
        // set the field null
        rentContract.setCarTransferCost(null);

        // Create the RentContract, which fails.

        restRentContractMockMvc.perform(post("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentContract)))
            .andExpect(status().isBadRequest());

        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReceiveLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentContractRepository.findAll().size();
        // set the field null
        rentContract.setReceiveLocation(null);

        // Create the RentContract, which fails.

        restRentContractMockMvc.perform(post("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentContract)))
            .andExpect(status().isBadRequest());

        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReturnLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentContractRepository.findAll().size();
        // set the field null
        rentContract.setReturnLocation(null);

        // Create the RentContract, which fails.

        restRentContractMockMvc.perform(post("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentContract)))
            .andExpect(status().isBadRequest());

        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEarlyReturnPolicyIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentContractRepository.findAll().size();
        // set the field null
        rentContract.setEarlyReturnPolicy(null);

        // Create the RentContract, which fails.

        restRentContractMockMvc.perform(post("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentContract)))
            .andExpect(status().isBadRequest());

        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContractExtensionMechanismIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentContractRepository.findAll().size();
        // set the field null
        rentContract.setContractExtensionMechanism(null);

        // Create the RentContract, which fails.

        restRentContractMockMvc.perform(post("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentContract)))
            .andExpect(status().isBadRequest());

        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccidentsAndFaultReportingMechanismIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentContractRepository.findAll().size();
        // set the field null
        rentContract.setAccidentsAndFaultReportingMechanism(null);

        // Create the RentContract, which fails.

        restRentContractMockMvc.perform(post("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentContract)))
            .andExpect(status().isBadRequest());

        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOdometerKmAfterIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentContractRepository.findAll().size();
        // set the field null
        rentContract.setOdometerKmAfter(null);

        // Create the RentContract, which fails.

        restRentContractMockMvc.perform(post("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentContract)))
            .andExpect(status().isBadRequest());

        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLateHoursIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentContractRepository.findAll().size();
        // set the field null
        rentContract.setLateHours(null);

        // Create the RentContract, which fails.

        restRentContractMockMvc.perform(post("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentContract)))
            .andExpect(status().isBadRequest());

        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOilChangeTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = rentContractRepository.findAll().size();
        // set the field null
        rentContract.setOilChangeTime(null);

        // Create the RentContract, which fails.

        restRentContractMockMvc.perform(post("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentContract)))
            .andExpect(status().isBadRequest());

        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRentContracts() throws Exception {
        // Initialize the database
        rentContractRepository.saveAndFlush(rentContract);

        // Get all the rentContractList
        restRentContractMockMvc.perform(get("/api/rent-contracts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rentContract.getId().intValue())))
            .andExpect(jsonPath("$.[*].contractNumber").value(hasItem(DEFAULT_CONTRACT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].contractSignDate").value(hasItem(DEFAULT_CONTRACT_SIGN_DATE.toString())))
            .andExpect(jsonPath("$.[*].contractStartDate").value(hasItem(DEFAULT_CONTRACT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].contractEndDate").value(hasItem(DEFAULT_CONTRACT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].contractSignLocation").value(hasItem(DEFAULT_CONTRACT_SIGN_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].extendedTo").value(hasItem(DEFAULT_EXTENDED_TO.toString())))
            .andExpect(jsonPath("$.[*].extendedTimes").value(hasItem(DEFAULT_EXTENDED_TIMES)))
            .andExpect(jsonPath("$.[*].rentDuration").value(hasItem(DEFAULT_RENT_DURATION)))
            .andExpect(jsonPath("$.[*].rentDayCost").value(hasItem(DEFAULT_RENT_DAY_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].rentHourCost").value(hasItem(DEFAULT_RENT_HOUR_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].odometerKmBefore").value(hasItem(DEFAULT_ODOMETER_KM_BEFORE.doubleValue())))
            .andExpect(jsonPath("$.[*].allowedLateHours").value(hasItem(DEFAULT_ALLOWED_LATE_HOURS)))
            .andExpect(jsonPath("$.[*].lateHourCost").value(hasItem(DEFAULT_LATE_HOUR_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].allowedKmPerDay").value(hasItem(DEFAULT_ALLOWED_KM_PER_DAY.doubleValue())))
            .andExpect(jsonPath("$.[*].allowedKmPerHour").value(hasItem(DEFAULT_ALLOWED_KM_PER_HOUR.doubleValue())))
            .andExpect(jsonPath("$.[*].carTransferCost").value(hasItem(DEFAULT_CAR_TRANSFER_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].receiveLocation").value(hasItem(DEFAULT_RECEIVE_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].returnLocation").value(hasItem(DEFAULT_RETURN_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].earlyReturnPolicy").value(hasItem(DEFAULT_EARLY_RETURN_POLICY.toString())))
            .andExpect(jsonPath("$.[*].contractExtensionMechanism").value(hasItem(DEFAULT_CONTRACT_EXTENSION_MECHANISM.toString())))
            .andExpect(jsonPath("$.[*].accidentsAndFaultReportingMechanism").value(hasItem(DEFAULT_ACCIDENTS_AND_FAULT_REPORTING_MECHANISM.toString())))
            .andExpect(jsonPath("$.[*].odometerKmAfter").value(hasItem(DEFAULT_ODOMETER_KM_AFTER.doubleValue())))
            .andExpect(jsonPath("$.[*].lateHours").value(hasItem(DEFAULT_LATE_HOURS.doubleValue())))
            .andExpect(jsonPath("$.[*].oilChangeTime").value(hasItem(DEFAULT_OIL_CHANGE_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getRentContract() throws Exception {
        // Initialize the database
        rentContractRepository.saveAndFlush(rentContract);

        // Get the rentContract
        restRentContractMockMvc.perform(get("/api/rent-contracts/{id}", rentContract.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rentContract.getId().intValue()))
            .andExpect(jsonPath("$.contractNumber").value(DEFAULT_CONTRACT_NUMBER.intValue()))
            .andExpect(jsonPath("$.contractSignDate").value(DEFAULT_CONTRACT_SIGN_DATE.toString()))
            .andExpect(jsonPath("$.contractStartDate").value(DEFAULT_CONTRACT_START_DATE.toString()))
            .andExpect(jsonPath("$.contractEndDate").value(DEFAULT_CONTRACT_END_DATE.toString()))
            .andExpect(jsonPath("$.contractSignLocation").value(DEFAULT_CONTRACT_SIGN_LOCATION.toString()))
            .andExpect(jsonPath("$.extendedTo").value(DEFAULT_EXTENDED_TO.toString()))
            .andExpect(jsonPath("$.extendedTimes").value(DEFAULT_EXTENDED_TIMES))
            .andExpect(jsonPath("$.rentDuration").value(DEFAULT_RENT_DURATION))
            .andExpect(jsonPath("$.rentDayCost").value(DEFAULT_RENT_DAY_COST.doubleValue()))
            .andExpect(jsonPath("$.rentHourCost").value(DEFAULT_RENT_HOUR_COST.doubleValue()))
            .andExpect(jsonPath("$.odometerKmBefore").value(DEFAULT_ODOMETER_KM_BEFORE.doubleValue()))
            .andExpect(jsonPath("$.allowedLateHours").value(DEFAULT_ALLOWED_LATE_HOURS))
            .andExpect(jsonPath("$.lateHourCost").value(DEFAULT_LATE_HOUR_COST.doubleValue()))
            .andExpect(jsonPath("$.allowedKmPerDay").value(DEFAULT_ALLOWED_KM_PER_DAY.doubleValue()))
            .andExpect(jsonPath("$.allowedKmPerHour").value(DEFAULT_ALLOWED_KM_PER_HOUR.doubleValue()))
            .andExpect(jsonPath("$.carTransferCost").value(DEFAULT_CAR_TRANSFER_COST.doubleValue()))
            .andExpect(jsonPath("$.receiveLocation").value(DEFAULT_RECEIVE_LOCATION.toString()))
            .andExpect(jsonPath("$.returnLocation").value(DEFAULT_RETURN_LOCATION.toString()))
            .andExpect(jsonPath("$.earlyReturnPolicy").value(DEFAULT_EARLY_RETURN_POLICY.toString()))
            .andExpect(jsonPath("$.contractExtensionMechanism").value(DEFAULT_CONTRACT_EXTENSION_MECHANISM.toString()))
            .andExpect(jsonPath("$.accidentsAndFaultReportingMechanism").value(DEFAULT_ACCIDENTS_AND_FAULT_REPORTING_MECHANISM.toString()))
            .andExpect(jsonPath("$.odometerKmAfter").value(DEFAULT_ODOMETER_KM_AFTER.doubleValue()))
            .andExpect(jsonPath("$.lateHours").value(DEFAULT_LATE_HOURS.doubleValue()))
            .andExpect(jsonPath("$.oilChangeTime").value(DEFAULT_OIL_CHANGE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRentContract() throws Exception {
        // Get the rentContract
        restRentContractMockMvc.perform(get("/api/rent-contracts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRentContract() throws Exception {
        // Initialize the database
        rentContractRepository.saveAndFlush(rentContract);

        int databaseSizeBeforeUpdate = rentContractRepository.findAll().size();

        // Update the rentContract
        RentContract updatedRentContract = rentContractRepository.findById(rentContract.getId()).get();
        // Disconnect from session so that the updates on updatedRentContract are not directly saved in db
        em.detach(updatedRentContract);
        updatedRentContract
            .contractNumber(UPDATED_CONTRACT_NUMBER)
            .contractSignDate(UPDATED_CONTRACT_SIGN_DATE)
            .contractStartDate(UPDATED_CONTRACT_START_DATE)
            .contractEndDate(UPDATED_CONTRACT_END_DATE)
            .contractSignLocation(UPDATED_CONTRACT_SIGN_LOCATION)
            .extendedTo(UPDATED_EXTENDED_TO)
            .extendedTimes(UPDATED_EXTENDED_TIMES)
            .rentDuration(UPDATED_RENT_DURATION)
            .rentDayCost(UPDATED_RENT_DAY_COST)
            .rentHourCost(UPDATED_RENT_HOUR_COST)
            .odometerKmBefore(UPDATED_ODOMETER_KM_BEFORE)
            .allowedLateHours(UPDATED_ALLOWED_LATE_HOURS)
            .lateHourCost(UPDATED_LATE_HOUR_COST)
            .allowedKmPerDay(UPDATED_ALLOWED_KM_PER_DAY)
            .allowedKmPerHour(UPDATED_ALLOWED_KM_PER_HOUR)
            .carTransferCost(UPDATED_CAR_TRANSFER_COST)
            .receiveLocation(UPDATED_RECEIVE_LOCATION)
            .returnLocation(UPDATED_RETURN_LOCATION)
            .earlyReturnPolicy(UPDATED_EARLY_RETURN_POLICY)
            .contractExtensionMechanism(UPDATED_CONTRACT_EXTENSION_MECHANISM)
            .accidentsAndFaultReportingMechanism(UPDATED_ACCIDENTS_AND_FAULT_REPORTING_MECHANISM)
            .odometerKmAfter(UPDATED_ODOMETER_KM_AFTER)
            .lateHours(UPDATED_LATE_HOURS)
            .oilChangeTime(UPDATED_OIL_CHANGE_TIME);

        restRentContractMockMvc.perform(put("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRentContract)))
            .andExpect(status().isOk());

        // Validate the RentContract in the database
        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeUpdate);
        RentContract testRentContract = rentContractList.get(rentContractList.size() - 1);
        assertThat(testRentContract.getContractNumber()).isEqualTo(UPDATED_CONTRACT_NUMBER);
        assertThat(testRentContract.getContractSignDate()).isEqualTo(UPDATED_CONTRACT_SIGN_DATE);
        assertThat(testRentContract.getContractStartDate()).isEqualTo(UPDATED_CONTRACT_START_DATE);
        assertThat(testRentContract.getContractEndDate()).isEqualTo(UPDATED_CONTRACT_END_DATE);
        assertThat(testRentContract.getContractSignLocation()).isEqualTo(UPDATED_CONTRACT_SIGN_LOCATION);
        assertThat(testRentContract.getExtendedTo()).isEqualTo(UPDATED_EXTENDED_TO);
        assertThat(testRentContract.getExtendedTimes()).isEqualTo(UPDATED_EXTENDED_TIMES);
        assertThat(testRentContract.getRentDuration()).isEqualTo(UPDATED_RENT_DURATION);
        assertThat(testRentContract.getRentDayCost()).isEqualTo(UPDATED_RENT_DAY_COST);
        assertThat(testRentContract.getRentHourCost()).isEqualTo(UPDATED_RENT_HOUR_COST);
        assertThat(testRentContract.getOdometerKmBefore()).isEqualTo(UPDATED_ODOMETER_KM_BEFORE);
        assertThat(testRentContract.getAllowedLateHours()).isEqualTo(UPDATED_ALLOWED_LATE_HOURS);
        assertThat(testRentContract.getLateHourCost()).isEqualTo(UPDATED_LATE_HOUR_COST);
        assertThat(testRentContract.getAllowedKmPerDay()).isEqualTo(UPDATED_ALLOWED_KM_PER_DAY);
        assertThat(testRentContract.getAllowedKmPerHour()).isEqualTo(UPDATED_ALLOWED_KM_PER_HOUR);
        assertThat(testRentContract.getCarTransferCost()).isEqualTo(UPDATED_CAR_TRANSFER_COST);
        assertThat(testRentContract.getReceiveLocation()).isEqualTo(UPDATED_RECEIVE_LOCATION);
        assertThat(testRentContract.getReturnLocation()).isEqualTo(UPDATED_RETURN_LOCATION);
        assertThat(testRentContract.getEarlyReturnPolicy()).isEqualTo(UPDATED_EARLY_RETURN_POLICY);
        assertThat(testRentContract.getContractExtensionMechanism()).isEqualTo(UPDATED_CONTRACT_EXTENSION_MECHANISM);
        assertThat(testRentContract.getAccidentsAndFaultReportingMechanism()).isEqualTo(UPDATED_ACCIDENTS_AND_FAULT_REPORTING_MECHANISM);
        assertThat(testRentContract.getOdometerKmAfter()).isEqualTo(UPDATED_ODOMETER_KM_AFTER);
        assertThat(testRentContract.getLateHours()).isEqualTo(UPDATED_LATE_HOURS);
        assertThat(testRentContract.getOilChangeTime()).isEqualTo(UPDATED_OIL_CHANGE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingRentContract() throws Exception {
        int databaseSizeBeforeUpdate = rentContractRepository.findAll().size();

        // Create the RentContract

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRentContractMockMvc.perform(put("/api/rent-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentContract)))
            .andExpect(status().isBadRequest());

        // Validate the RentContract in the database
        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRentContract() throws Exception {
        // Initialize the database
        rentContractRepository.saveAndFlush(rentContract);

        int databaseSizeBeforeDelete = rentContractRepository.findAll().size();

        // Delete the rentContract
        restRentContractMockMvc.perform(delete("/api/rent-contracts/{id}", rentContract.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<RentContract> rentContractList = rentContractRepository.findAll();
        assertThat(rentContractList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RentContract.class);
        RentContract rentContract1 = new RentContract();
        rentContract1.setId(1L);
        RentContract rentContract2 = new RentContract();
        rentContract2.setId(rentContract1.getId());
        assertThat(rentContract1).isEqualTo(rentContract2);
        rentContract2.setId(2L);
        assertThat(rentContract1).isNotEqualTo(rentContract2);
        rentContract1.setId(null);
        assertThat(rentContract1).isNotEqualTo(rentContract2);
    }
}
