package test.contract.web.rest;

import test.contract.ContractApp;
import test.contract.domain.PaymentDetails;
import test.contract.repository.PaymentDetailsRepository;
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
 * Integration tests for the {@Link PaymentDetailsResource} REST controller.
 */
@SpringBootTest(classes = ContractApp.class)
public class PaymentDetailsResourceIT {

    private static final Integer DEFAULT_TOTAL = 1;
    private static final Integer UPDATED_TOTAL = 2;

    private static final Double DEFAULT_TOTAL_RENT_COST = 1D;
    private static final Double UPDATED_TOTAL_RENT_COST = 2D;

    private static final Double DEFAULT_EXTRA_KM_COST = 1D;
    private static final Double UPDATED_EXTRA_KM_COST = 2D;

    private static final Double DEFAULT_DRIVER_COST = 1D;
    private static final Double UPDATED_DRIVER_COST = 2D;

    private static final Double DEFAULT_INTERNATIONAL_AUTHORIZATION_COST = 1D;
    private static final Double UPDATED_INTERNATIONAL_AUTHORIZATION_COST = 2D;

    private static final Double DEFAULT_VEHICLE_TRANSFER_COST = 1D;
    private static final Double UPDATED_VEHICLE_TRANSFER_COST = 2D;

    private static final Double DEFAULT_SPARE_PARTS_COST = 1D;
    private static final Double UPDATED_SPARE_PARTS_COST = 2D;

    private static final Double DEFAULT_OIL_CHANGE_COST = 1D;
    private static final Double UPDATED_OIL_CHANGE_COST = 2D;

    private static final Double DEFAULT_DAMAGE_COST = 1D;
    private static final Double UPDATED_DAMAGE_COST = 2D;

    private static final Double DEFAULT_FUEL_COST = 1D;
    private static final Double UPDATED_FUEL_COST = 2D;

    private static final Integer DEFAULT_DISCOUNT_PERCENTAGE = 1;
    private static final Integer UPDATED_DISCOUNT_PERCENTAGE = 2;

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;

    private static final Double DEFAULT_VAT = 1D;
    private static final Double UPDATED_VAT = 2D;

    private static final Double DEFAULT_PAID = 1D;
    private static final Double UPDATED_PAID = 2D;

    private static final Double DEFAULT_REMAINING = 1D;
    private static final Double UPDATED_REMAINING = 2D;

    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

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

    private MockMvc restPaymentDetailsMockMvc;

    private PaymentDetails paymentDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaymentDetailsResource paymentDetailsResource = new PaymentDetailsResource(paymentDetailsRepository);
        this.restPaymentDetailsMockMvc = MockMvcBuilders.standaloneSetup(paymentDetailsResource)
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
    public static PaymentDetails createEntity(EntityManager em) {
        PaymentDetails paymentDetails = new PaymentDetails()
            .total(DEFAULT_TOTAL)
            .totalRentCost(DEFAULT_TOTAL_RENT_COST)
            .extraKmCost(DEFAULT_EXTRA_KM_COST)
            .driverCost(DEFAULT_DRIVER_COST)
            .internationalAuthorizationCost(DEFAULT_INTERNATIONAL_AUTHORIZATION_COST)
            .vehicleTransferCost(DEFAULT_VEHICLE_TRANSFER_COST)
            .sparePartsCost(DEFAULT_SPARE_PARTS_COST)
            .oilChangeCost(DEFAULT_OIL_CHANGE_COST)
            .damageCost(DEFAULT_DAMAGE_COST)
            .fuelCost(DEFAULT_FUEL_COST)
            .discountPercentage(DEFAULT_DISCOUNT_PERCENTAGE)
            .discount(DEFAULT_DISCOUNT)
            .vat(DEFAULT_VAT)
            .paid(DEFAULT_PAID)
            .remaining(DEFAULT_REMAINING);
        return paymentDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentDetails createUpdatedEntity(EntityManager em) {
        PaymentDetails paymentDetails = new PaymentDetails()
            .total(UPDATED_TOTAL)
            .totalRentCost(UPDATED_TOTAL_RENT_COST)
            .extraKmCost(UPDATED_EXTRA_KM_COST)
            .driverCost(UPDATED_DRIVER_COST)
            .internationalAuthorizationCost(UPDATED_INTERNATIONAL_AUTHORIZATION_COST)
            .vehicleTransferCost(UPDATED_VEHICLE_TRANSFER_COST)
            .sparePartsCost(UPDATED_SPARE_PARTS_COST)
            .oilChangeCost(UPDATED_OIL_CHANGE_COST)
            .damageCost(UPDATED_DAMAGE_COST)
            .fuelCost(UPDATED_FUEL_COST)
            .discountPercentage(UPDATED_DISCOUNT_PERCENTAGE)
            .discount(UPDATED_DISCOUNT)
            .vat(UPDATED_VAT)
            .paid(UPDATED_PAID)
            .remaining(UPDATED_REMAINING);
        return paymentDetails;
    }

    @BeforeEach
    public void initTest() {
        paymentDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentDetails() throws Exception {
        int databaseSizeBeforeCreate = paymentDetailsRepository.findAll().size();

        // Create the PaymentDetails
        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isCreated());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentDetails testPaymentDetails = paymentDetailsList.get(paymentDetailsList.size() - 1);
        assertThat(testPaymentDetails.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testPaymentDetails.getTotalRentCost()).isEqualTo(DEFAULT_TOTAL_RENT_COST);
        assertThat(testPaymentDetails.getExtraKmCost()).isEqualTo(DEFAULT_EXTRA_KM_COST);
        assertThat(testPaymentDetails.getDriverCost()).isEqualTo(DEFAULT_DRIVER_COST);
        assertThat(testPaymentDetails.getInternationalAuthorizationCost()).isEqualTo(DEFAULT_INTERNATIONAL_AUTHORIZATION_COST);
        assertThat(testPaymentDetails.getVehicleTransferCost()).isEqualTo(DEFAULT_VEHICLE_TRANSFER_COST);
        assertThat(testPaymentDetails.getSparePartsCost()).isEqualTo(DEFAULT_SPARE_PARTS_COST);
        assertThat(testPaymentDetails.getOilChangeCost()).isEqualTo(DEFAULT_OIL_CHANGE_COST);
        assertThat(testPaymentDetails.getDamageCost()).isEqualTo(DEFAULT_DAMAGE_COST);
        assertThat(testPaymentDetails.getFuelCost()).isEqualTo(DEFAULT_FUEL_COST);
        assertThat(testPaymentDetails.getDiscountPercentage()).isEqualTo(DEFAULT_DISCOUNT_PERCENTAGE);
        assertThat(testPaymentDetails.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testPaymentDetails.getVat()).isEqualTo(DEFAULT_VAT);
        assertThat(testPaymentDetails.getPaid()).isEqualTo(DEFAULT_PAID);
        assertThat(testPaymentDetails.getRemaining()).isEqualTo(DEFAULT_REMAINING);
    }

    @Test
    @Transactional
    public void createPaymentDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentDetailsRepository.findAll().size();

        // Create the PaymentDetails with an existing ID
        paymentDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentDetailsRepository.findAll().size();
        // set the field null
        paymentDetails.setTotal(null);

        // Create the PaymentDetails, which fails.

        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isBadRequest());

        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalRentCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentDetailsRepository.findAll().size();
        // set the field null
        paymentDetails.setTotalRentCost(null);

        // Create the PaymentDetails, which fails.

        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isBadRequest());

        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExtraKmCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentDetailsRepository.findAll().size();
        // set the field null
        paymentDetails.setExtraKmCost(null);

        // Create the PaymentDetails, which fails.

        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isBadRequest());

        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDriverCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentDetailsRepository.findAll().size();
        // set the field null
        paymentDetails.setDriverCost(null);

        // Create the PaymentDetails, which fails.

        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isBadRequest());

        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInternationalAuthorizationCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentDetailsRepository.findAll().size();
        // set the field null
        paymentDetails.setInternationalAuthorizationCost(null);

        // Create the PaymentDetails, which fails.

        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isBadRequest());

        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVehicleTransferCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentDetailsRepository.findAll().size();
        // set the field null
        paymentDetails.setVehicleTransferCost(null);

        // Create the PaymentDetails, which fails.

        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isBadRequest());

        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSparePartsCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentDetailsRepository.findAll().size();
        // set the field null
        paymentDetails.setSparePartsCost(null);

        // Create the PaymentDetails, which fails.

        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isBadRequest());

        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOilChangeCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentDetailsRepository.findAll().size();
        // set the field null
        paymentDetails.setOilChangeCost(null);

        // Create the PaymentDetails, which fails.

        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isBadRequest());

        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDamageCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentDetailsRepository.findAll().size();
        // set the field null
        paymentDetails.setDamageCost(null);

        // Create the PaymentDetails, which fails.

        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isBadRequest());

        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFuelCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentDetailsRepository.findAll().size();
        // set the field null
        paymentDetails.setFuelCost(null);

        // Create the PaymentDetails, which fails.

        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isBadRequest());

        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiscountPercentageIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentDetailsRepository.findAll().size();
        // set the field null
        paymentDetails.setDiscountPercentage(null);

        // Create the PaymentDetails, which fails.

        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isBadRequest());

        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiscountIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentDetailsRepository.findAll().size();
        // set the field null
        paymentDetails.setDiscount(null);

        // Create the PaymentDetails, which fails.

        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isBadRequest());

        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVatIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentDetailsRepository.findAll().size();
        // set the field null
        paymentDetails.setVat(null);

        // Create the PaymentDetails, which fails.

        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isBadRequest());

        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaidIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentDetailsRepository.findAll().size();
        // set the field null
        paymentDetails.setPaid(null);

        // Create the PaymentDetails, which fails.

        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isBadRequest());

        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRemainingIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentDetailsRepository.findAll().size();
        // set the field null
        paymentDetails.setRemaining(null);

        // Create the PaymentDetails, which fails.

        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isBadRequest());

        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaymentDetails() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get all the paymentDetailsList
        restPaymentDetailsMockMvc.perform(get("/api/payment-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL)))
            .andExpect(jsonPath("$.[*].totalRentCost").value(hasItem(DEFAULT_TOTAL_RENT_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].extraKmCost").value(hasItem(DEFAULT_EXTRA_KM_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].driverCost").value(hasItem(DEFAULT_DRIVER_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].internationalAuthorizationCost").value(hasItem(DEFAULT_INTERNATIONAL_AUTHORIZATION_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].vehicleTransferCost").value(hasItem(DEFAULT_VEHICLE_TRANSFER_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].sparePartsCost").value(hasItem(DEFAULT_SPARE_PARTS_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].oilChangeCost").value(hasItem(DEFAULT_OIL_CHANGE_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].damageCost").value(hasItem(DEFAULT_DAMAGE_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].fuelCost").value(hasItem(DEFAULT_FUEL_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].discountPercentage").value(hasItem(DEFAULT_DISCOUNT_PERCENTAGE)))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].vat").value(hasItem(DEFAULT_VAT.doubleValue())))
            .andExpect(jsonPath("$.[*].paid").value(hasItem(DEFAULT_PAID.doubleValue())))
            .andExpect(jsonPath("$.[*].remaining").value(hasItem(DEFAULT_REMAINING.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getPaymentDetails() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get the paymentDetails
        restPaymentDetailsMockMvc.perform(get("/api/payment-details/{id}", paymentDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paymentDetails.getId().intValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL))
            .andExpect(jsonPath("$.totalRentCost").value(DEFAULT_TOTAL_RENT_COST.doubleValue()))
            .andExpect(jsonPath("$.extraKmCost").value(DEFAULT_EXTRA_KM_COST.doubleValue()))
            .andExpect(jsonPath("$.driverCost").value(DEFAULT_DRIVER_COST.doubleValue()))
            .andExpect(jsonPath("$.internationalAuthorizationCost").value(DEFAULT_INTERNATIONAL_AUTHORIZATION_COST.doubleValue()))
            .andExpect(jsonPath("$.vehicleTransferCost").value(DEFAULT_VEHICLE_TRANSFER_COST.doubleValue()))
            .andExpect(jsonPath("$.sparePartsCost").value(DEFAULT_SPARE_PARTS_COST.doubleValue()))
            .andExpect(jsonPath("$.oilChangeCost").value(DEFAULT_OIL_CHANGE_COST.doubleValue()))
            .andExpect(jsonPath("$.damageCost").value(DEFAULT_DAMAGE_COST.doubleValue()))
            .andExpect(jsonPath("$.fuelCost").value(DEFAULT_FUEL_COST.doubleValue()))
            .andExpect(jsonPath("$.discountPercentage").value(DEFAULT_DISCOUNT_PERCENTAGE))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.vat").value(DEFAULT_VAT.doubleValue()))
            .andExpect(jsonPath("$.paid").value(DEFAULT_PAID.doubleValue()))
            .andExpect(jsonPath("$.remaining").value(DEFAULT_REMAINING.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPaymentDetails() throws Exception {
        // Get the paymentDetails
        restPaymentDetailsMockMvc.perform(get("/api/payment-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentDetails() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        int databaseSizeBeforeUpdate = paymentDetailsRepository.findAll().size();

        // Update the paymentDetails
        PaymentDetails updatedPaymentDetails = paymentDetailsRepository.findById(paymentDetails.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentDetails are not directly saved in db
        em.detach(updatedPaymentDetails);
        updatedPaymentDetails
            .total(UPDATED_TOTAL)
            .totalRentCost(UPDATED_TOTAL_RENT_COST)
            .extraKmCost(UPDATED_EXTRA_KM_COST)
            .driverCost(UPDATED_DRIVER_COST)
            .internationalAuthorizationCost(UPDATED_INTERNATIONAL_AUTHORIZATION_COST)
            .vehicleTransferCost(UPDATED_VEHICLE_TRANSFER_COST)
            .sparePartsCost(UPDATED_SPARE_PARTS_COST)
            .oilChangeCost(UPDATED_OIL_CHANGE_COST)
            .damageCost(UPDATED_DAMAGE_COST)
            .fuelCost(UPDATED_FUEL_COST)
            .discountPercentage(UPDATED_DISCOUNT_PERCENTAGE)
            .discount(UPDATED_DISCOUNT)
            .vat(UPDATED_VAT)
            .paid(UPDATED_PAID)
            .remaining(UPDATED_REMAINING);

        restPaymentDetailsMockMvc.perform(put("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaymentDetails)))
            .andExpect(status().isOk());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeUpdate);
        PaymentDetails testPaymentDetails = paymentDetailsList.get(paymentDetailsList.size() - 1);
        assertThat(testPaymentDetails.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testPaymentDetails.getTotalRentCost()).isEqualTo(UPDATED_TOTAL_RENT_COST);
        assertThat(testPaymentDetails.getExtraKmCost()).isEqualTo(UPDATED_EXTRA_KM_COST);
        assertThat(testPaymentDetails.getDriverCost()).isEqualTo(UPDATED_DRIVER_COST);
        assertThat(testPaymentDetails.getInternationalAuthorizationCost()).isEqualTo(UPDATED_INTERNATIONAL_AUTHORIZATION_COST);
        assertThat(testPaymentDetails.getVehicleTransferCost()).isEqualTo(UPDATED_VEHICLE_TRANSFER_COST);
        assertThat(testPaymentDetails.getSparePartsCost()).isEqualTo(UPDATED_SPARE_PARTS_COST);
        assertThat(testPaymentDetails.getOilChangeCost()).isEqualTo(UPDATED_OIL_CHANGE_COST);
        assertThat(testPaymentDetails.getDamageCost()).isEqualTo(UPDATED_DAMAGE_COST);
        assertThat(testPaymentDetails.getFuelCost()).isEqualTo(UPDATED_FUEL_COST);
        assertThat(testPaymentDetails.getDiscountPercentage()).isEqualTo(UPDATED_DISCOUNT_PERCENTAGE);
        assertThat(testPaymentDetails.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testPaymentDetails.getVat()).isEqualTo(UPDATED_VAT);
        assertThat(testPaymentDetails.getPaid()).isEqualTo(UPDATED_PAID);
        assertThat(testPaymentDetails.getRemaining()).isEqualTo(UPDATED_REMAINING);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentDetails() throws Exception {
        int databaseSizeBeforeUpdate = paymentDetailsRepository.findAll().size();

        // Create the PaymentDetails

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentDetailsMockMvc.perform(put("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetails)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaymentDetails() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        int databaseSizeBeforeDelete = paymentDetailsRepository.findAll().size();

        // Delete the paymentDetails
        restPaymentDetailsMockMvc.perform(delete("/api/payment-details/{id}", paymentDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentDetails.class);
        PaymentDetails paymentDetails1 = new PaymentDetails();
        paymentDetails1.setId(1L);
        PaymentDetails paymentDetails2 = new PaymentDetails();
        paymentDetails2.setId(paymentDetails1.getId());
        assertThat(paymentDetails1).isEqualTo(paymentDetails2);
        paymentDetails2.setId(2L);
        assertThat(paymentDetails1).isNotEqualTo(paymentDetails2);
        paymentDetails1.setId(null);
        assertThat(paymentDetails1).isNotEqualTo(paymentDetails2);
    }
}
