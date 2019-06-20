package test.contract.web.rest;

import test.contract.ContractApp;
import test.contract.domain.PersonInfo;
import test.contract.repository.PersonInfoRepository;
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
 * Integration tests for the {@Link PersonInfoResource} REST controller.
 */
@SpringBootTest(classes = ContractApp.class)
public class PersonInfoResourceIT {

    private static final String DEFAULT_AR_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AR_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EN_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EN_FULL_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_BIRTH_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTH_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_HIJRI_BIRTH_DATE = 1;
    private static final Integer UPDATED_HIJRI_BIRTH_DATE = 2;

    private static final Instant DEFAULT_ID_EXPIRY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ID_EXPIRY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_HIJRI_ID_EXPIRY_DATE = 1;
    private static final Integer UPDATED_HIJRI_ID_EXPIRY_DATE = 2;

    private static final Integer DEFAULT_ID_COPY_NUMBER = 1;
    private static final Integer UPDATED_ID_COPY_NUMBER = 2;

    private static final String DEFAULT_ISSUE_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_ISSUE_PLACE = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final Instant DEFAULT_LICENSE_EXPIRY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LICENSE_EXPIRY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONAL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_NATIONAL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_WORK_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private PersonInfoRepository personInfoRepository;

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

    private MockMvc restPersonInfoMockMvc;

    private PersonInfo personInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonInfoResource personInfoResource = new PersonInfoResource(personInfoRepository);
        this.restPersonInfoMockMvc = MockMvcBuilders.standaloneSetup(personInfoResource)
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
    public static PersonInfo createEntity(EntityManager em) {
        PersonInfo personInfo = new PersonInfo()
            .arFullName(DEFAULT_AR_FULL_NAME)
            .enFullName(DEFAULT_EN_FULL_NAME)
            .birthDate(DEFAULT_BIRTH_DATE)
            .hijriBirthDate(DEFAULT_HIJRI_BIRTH_DATE)
            .idExpiryDate(DEFAULT_ID_EXPIRY_DATE)
            .hijriIdExpiryDate(DEFAULT_HIJRI_ID_EXPIRY_DATE)
            .idCopyNumber(DEFAULT_ID_COPY_NUMBER)
            .issuePlace(DEFAULT_ISSUE_PLACE)
            .mobile(DEFAULT_MOBILE)
            .licenseExpiryDate(DEFAULT_LICENSE_EXPIRY_DATE)
            .address(DEFAULT_ADDRESS)
            .nationalAddress(DEFAULT_NATIONAL_ADDRESS)
            .workAddress(DEFAULT_WORK_ADDRESS);
        return personInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonInfo createUpdatedEntity(EntityManager em) {
        PersonInfo personInfo = new PersonInfo()
            .arFullName(UPDATED_AR_FULL_NAME)
            .enFullName(UPDATED_EN_FULL_NAME)
            .birthDate(UPDATED_BIRTH_DATE)
            .hijriBirthDate(UPDATED_HIJRI_BIRTH_DATE)
            .idExpiryDate(UPDATED_ID_EXPIRY_DATE)
            .hijriIdExpiryDate(UPDATED_HIJRI_ID_EXPIRY_DATE)
            .idCopyNumber(UPDATED_ID_COPY_NUMBER)
            .issuePlace(UPDATED_ISSUE_PLACE)
            .mobile(UPDATED_MOBILE)
            .licenseExpiryDate(UPDATED_LICENSE_EXPIRY_DATE)
            .address(UPDATED_ADDRESS)
            .nationalAddress(UPDATED_NATIONAL_ADDRESS)
            .workAddress(UPDATED_WORK_ADDRESS);
        return personInfo;
    }

    @BeforeEach
    public void initTest() {
        personInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonInfo() throws Exception {
        int databaseSizeBeforeCreate = personInfoRepository.findAll().size();

        // Create the PersonInfo
        restPersonInfoMockMvc.perform(post("/api/person-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personInfo)))
            .andExpect(status().isCreated());

        // Validate the PersonInfo in the database
        List<PersonInfo> personInfoList = personInfoRepository.findAll();
        assertThat(personInfoList).hasSize(databaseSizeBeforeCreate + 1);
        PersonInfo testPersonInfo = personInfoList.get(personInfoList.size() - 1);
        assertThat(testPersonInfo.getArFullName()).isEqualTo(DEFAULT_AR_FULL_NAME);
        assertThat(testPersonInfo.getEnFullName()).isEqualTo(DEFAULT_EN_FULL_NAME);
        assertThat(testPersonInfo.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testPersonInfo.getHijriBirthDate()).isEqualTo(DEFAULT_HIJRI_BIRTH_DATE);
        assertThat(testPersonInfo.getIdExpiryDate()).isEqualTo(DEFAULT_ID_EXPIRY_DATE);
        assertThat(testPersonInfo.getHijriIdExpiryDate()).isEqualTo(DEFAULT_HIJRI_ID_EXPIRY_DATE);
        assertThat(testPersonInfo.getIdCopyNumber()).isEqualTo(DEFAULT_ID_COPY_NUMBER);
        assertThat(testPersonInfo.getIssuePlace()).isEqualTo(DEFAULT_ISSUE_PLACE);
        assertThat(testPersonInfo.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testPersonInfo.getLicenseExpiryDate()).isEqualTo(DEFAULT_LICENSE_EXPIRY_DATE);
        assertThat(testPersonInfo.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPersonInfo.getNationalAddress()).isEqualTo(DEFAULT_NATIONAL_ADDRESS);
        assertThat(testPersonInfo.getWorkAddress()).isEqualTo(DEFAULT_WORK_ADDRESS);
    }

    @Test
    @Transactional
    public void createPersonInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personInfoRepository.findAll().size();

        // Create the PersonInfo with an existing ID
        personInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonInfoMockMvc.perform(post("/api/person-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personInfo)))
            .andExpect(status().isBadRequest());

        // Validate the PersonInfo in the database
        List<PersonInfo> personInfoList = personInfoRepository.findAll();
        assertThat(personInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkArFullNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personInfoRepository.findAll().size();
        // set the field null
        personInfo.setArFullName(null);

        // Create the PersonInfo, which fails.

        restPersonInfoMockMvc.perform(post("/api/person-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personInfo)))
            .andExpect(status().isBadRequest());

        List<PersonInfo> personInfoList = personInfoRepository.findAll();
        assertThat(personInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnFullNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personInfoRepository.findAll().size();
        // set the field null
        personInfo.setEnFullName(null);

        // Create the PersonInfo, which fails.

        restPersonInfoMockMvc.perform(post("/api/person-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personInfo)))
            .andExpect(status().isBadRequest());

        List<PersonInfo> personInfoList = personInfoRepository.findAll();
        assertThat(personInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBirthDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = personInfoRepository.findAll().size();
        // set the field null
        personInfo.setBirthDate(null);

        // Create the PersonInfo, which fails.

        restPersonInfoMockMvc.perform(post("/api/person-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personInfo)))
            .andExpect(status().isBadRequest());

        List<PersonInfo> personInfoList = personInfoRepository.findAll();
        assertThat(personInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHijriBirthDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = personInfoRepository.findAll().size();
        // set the field null
        personInfo.setHijriBirthDate(null);

        // Create the PersonInfo, which fails.

        restPersonInfoMockMvc.perform(post("/api/person-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personInfo)))
            .andExpect(status().isBadRequest());

        List<PersonInfo> personInfoList = personInfoRepository.findAll();
        assertThat(personInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMobileIsRequired() throws Exception {
        int databaseSizeBeforeTest = personInfoRepository.findAll().size();
        // set the field null
        personInfo.setMobile(null);

        // Create the PersonInfo, which fails.

        restPersonInfoMockMvc.perform(post("/api/person-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personInfo)))
            .andExpect(status().isBadRequest());

        List<PersonInfo> personInfoList = personInfoRepository.findAll();
        assertThat(personInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonInfos() throws Exception {
        // Initialize the database
        personInfoRepository.saveAndFlush(personInfo);

        // Get all the personInfoList
        restPersonInfoMockMvc.perform(get("/api/person-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].arFullName").value(hasItem(DEFAULT_AR_FULL_NAME.toString())))
            .andExpect(jsonPath("$.[*].enFullName").value(hasItem(DEFAULT_EN_FULL_NAME.toString())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].hijriBirthDate").value(hasItem(DEFAULT_HIJRI_BIRTH_DATE)))
            .andExpect(jsonPath("$.[*].idExpiryDate").value(hasItem(DEFAULT_ID_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].hijriIdExpiryDate").value(hasItem(DEFAULT_HIJRI_ID_EXPIRY_DATE)))
            .andExpect(jsonPath("$.[*].idCopyNumber").value(hasItem(DEFAULT_ID_COPY_NUMBER)))
            .andExpect(jsonPath("$.[*].issuePlace").value(hasItem(DEFAULT_ISSUE_PLACE.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE.toString())))
            .andExpect(jsonPath("$.[*].licenseExpiryDate").value(hasItem(DEFAULT_LICENSE_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].nationalAddress").value(hasItem(DEFAULT_NATIONAL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].workAddress").value(hasItem(DEFAULT_WORK_ADDRESS.toString())));
    }
    
    @Test
    @Transactional
    public void getPersonInfo() throws Exception {
        // Initialize the database
        personInfoRepository.saveAndFlush(personInfo);

        // Get the personInfo
        restPersonInfoMockMvc.perform(get("/api/person-infos/{id}", personInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personInfo.getId().intValue()))
            .andExpect(jsonPath("$.arFullName").value(DEFAULT_AR_FULL_NAME.toString()))
            .andExpect(jsonPath("$.enFullName").value(DEFAULT_EN_FULL_NAME.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.hijriBirthDate").value(DEFAULT_HIJRI_BIRTH_DATE))
            .andExpect(jsonPath("$.idExpiryDate").value(DEFAULT_ID_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.hijriIdExpiryDate").value(DEFAULT_HIJRI_ID_EXPIRY_DATE))
            .andExpect(jsonPath("$.idCopyNumber").value(DEFAULT_ID_COPY_NUMBER))
            .andExpect(jsonPath("$.issuePlace").value(DEFAULT_ISSUE_PLACE.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE.toString()))
            .andExpect(jsonPath("$.licenseExpiryDate").value(DEFAULT_LICENSE_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.nationalAddress").value(DEFAULT_NATIONAL_ADDRESS.toString()))
            .andExpect(jsonPath("$.workAddress").value(DEFAULT_WORK_ADDRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonInfo() throws Exception {
        // Get the personInfo
        restPersonInfoMockMvc.perform(get("/api/person-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonInfo() throws Exception {
        // Initialize the database
        personInfoRepository.saveAndFlush(personInfo);

        int databaseSizeBeforeUpdate = personInfoRepository.findAll().size();

        // Update the personInfo
        PersonInfo updatedPersonInfo = personInfoRepository.findById(personInfo.getId()).get();
        // Disconnect from session so that the updates on updatedPersonInfo are not directly saved in db
        em.detach(updatedPersonInfo);
        updatedPersonInfo
            .arFullName(UPDATED_AR_FULL_NAME)
            .enFullName(UPDATED_EN_FULL_NAME)
            .birthDate(UPDATED_BIRTH_DATE)
            .hijriBirthDate(UPDATED_HIJRI_BIRTH_DATE)
            .idExpiryDate(UPDATED_ID_EXPIRY_DATE)
            .hijriIdExpiryDate(UPDATED_HIJRI_ID_EXPIRY_DATE)
            .idCopyNumber(UPDATED_ID_COPY_NUMBER)
            .issuePlace(UPDATED_ISSUE_PLACE)
            .mobile(UPDATED_MOBILE)
            .licenseExpiryDate(UPDATED_LICENSE_EXPIRY_DATE)
            .address(UPDATED_ADDRESS)
            .nationalAddress(UPDATED_NATIONAL_ADDRESS)
            .workAddress(UPDATED_WORK_ADDRESS);

        restPersonInfoMockMvc.perform(put("/api/person-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonInfo)))
            .andExpect(status().isOk());

        // Validate the PersonInfo in the database
        List<PersonInfo> personInfoList = personInfoRepository.findAll();
        assertThat(personInfoList).hasSize(databaseSizeBeforeUpdate);
        PersonInfo testPersonInfo = personInfoList.get(personInfoList.size() - 1);
        assertThat(testPersonInfo.getArFullName()).isEqualTo(UPDATED_AR_FULL_NAME);
        assertThat(testPersonInfo.getEnFullName()).isEqualTo(UPDATED_EN_FULL_NAME);
        assertThat(testPersonInfo.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testPersonInfo.getHijriBirthDate()).isEqualTo(UPDATED_HIJRI_BIRTH_DATE);
        assertThat(testPersonInfo.getIdExpiryDate()).isEqualTo(UPDATED_ID_EXPIRY_DATE);
        assertThat(testPersonInfo.getHijriIdExpiryDate()).isEqualTo(UPDATED_HIJRI_ID_EXPIRY_DATE);
        assertThat(testPersonInfo.getIdCopyNumber()).isEqualTo(UPDATED_ID_COPY_NUMBER);
        assertThat(testPersonInfo.getIssuePlace()).isEqualTo(UPDATED_ISSUE_PLACE);
        assertThat(testPersonInfo.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testPersonInfo.getLicenseExpiryDate()).isEqualTo(UPDATED_LICENSE_EXPIRY_DATE);
        assertThat(testPersonInfo.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPersonInfo.getNationalAddress()).isEqualTo(UPDATED_NATIONAL_ADDRESS);
        assertThat(testPersonInfo.getWorkAddress()).isEqualTo(UPDATED_WORK_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonInfo() throws Exception {
        int databaseSizeBeforeUpdate = personInfoRepository.findAll().size();

        // Create the PersonInfo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonInfoMockMvc.perform(put("/api/person-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personInfo)))
            .andExpect(status().isBadRequest());

        // Validate the PersonInfo in the database
        List<PersonInfo> personInfoList = personInfoRepository.findAll();
        assertThat(personInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonInfo() throws Exception {
        // Initialize the database
        personInfoRepository.saveAndFlush(personInfo);

        int databaseSizeBeforeDelete = personInfoRepository.findAll().size();

        // Delete the personInfo
        restPersonInfoMockMvc.perform(delete("/api/person-infos/{id}", personInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<PersonInfo> personInfoList = personInfoRepository.findAll();
        assertThat(personInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonInfo.class);
        PersonInfo personInfo1 = new PersonInfo();
        personInfo1.setId(1L);
        PersonInfo personInfo2 = new PersonInfo();
        personInfo2.setId(personInfo1.getId());
        assertThat(personInfo1).isEqualTo(personInfo2);
        personInfo2.setId(2L);
        assertThat(personInfo1).isNotEqualTo(personInfo2);
        personInfo1.setId(null);
        assertThat(personInfo1).isNotEqualTo(personInfo2);
    }
}
