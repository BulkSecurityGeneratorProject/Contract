package test.contract.web.rest;

import test.contract.ContractApp;
import test.contract.domain.Nationality;
import test.contract.repository.NationalityRepository;
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
 * Integration tests for the {@Link NationalityResource} REST controller.
 */
@SpringBootTest(classes = ContractApp.class)
public class NationalityResourceIT {

    private static final String DEFAULT_AR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EN_NAME = "BBBBBBBBBB";

    @Autowired
    private NationalityRepository nationalityRepository;

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

    private MockMvc restNationalityMockMvc;

    private Nationality nationality;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NationalityResource nationalityResource = new NationalityResource(nationalityRepository);
        this.restNationalityMockMvc = MockMvcBuilders.standaloneSetup(nationalityResource)
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
    public static Nationality createEntity(EntityManager em) {
        Nationality nationality = new Nationality()
            .arName(DEFAULT_AR_NAME)
            .enName(DEFAULT_EN_NAME);
        return nationality;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nationality createUpdatedEntity(EntityManager em) {
        Nationality nationality = new Nationality()
            .arName(UPDATED_AR_NAME)
            .enName(UPDATED_EN_NAME);
        return nationality;
    }

    @BeforeEach
    public void initTest() {
        nationality = createEntity(em);
    }

    @Test
    @Transactional
    public void createNationality() throws Exception {
        int databaseSizeBeforeCreate = nationalityRepository.findAll().size();

        // Create the Nationality
        restNationalityMockMvc.perform(post("/api/nationalities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nationality)))
            .andExpect(status().isCreated());

        // Validate the Nationality in the database
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeCreate + 1);
        Nationality testNationality = nationalityList.get(nationalityList.size() - 1);
        assertThat(testNationality.getArName()).isEqualTo(DEFAULT_AR_NAME);
        assertThat(testNationality.getEnName()).isEqualTo(DEFAULT_EN_NAME);
    }

    @Test
    @Transactional
    public void createNationalityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nationalityRepository.findAll().size();

        // Create the Nationality with an existing ID
        nationality.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNationalityMockMvc.perform(post("/api/nationalities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nationality)))
            .andExpect(status().isBadRequest());

        // Validate the Nationality in the database
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNationalities() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get all the nationalityList
        restNationalityMockMvc.perform(get("/api/nationalities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nationality.getId().intValue())))
            .andExpect(jsonPath("$.[*].arName").value(hasItem(DEFAULT_AR_NAME.toString())))
            .andExpect(jsonPath("$.[*].enName").value(hasItem(DEFAULT_EN_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getNationality() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        // Get the nationality
        restNationalityMockMvc.perform(get("/api/nationalities/{id}", nationality.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nationality.getId().intValue()))
            .andExpect(jsonPath("$.arName").value(DEFAULT_AR_NAME.toString()))
            .andExpect(jsonPath("$.enName").value(DEFAULT_EN_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNationality() throws Exception {
        // Get the nationality
        restNationalityMockMvc.perform(get("/api/nationalities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNationality() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        int databaseSizeBeforeUpdate = nationalityRepository.findAll().size();

        // Update the nationality
        Nationality updatedNationality = nationalityRepository.findById(nationality.getId()).get();
        // Disconnect from session so that the updates on updatedNationality are not directly saved in db
        em.detach(updatedNationality);
        updatedNationality
            .arName(UPDATED_AR_NAME)
            .enName(UPDATED_EN_NAME);

        restNationalityMockMvc.perform(put("/api/nationalities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNationality)))
            .andExpect(status().isOk());

        // Validate the Nationality in the database
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeUpdate);
        Nationality testNationality = nationalityList.get(nationalityList.size() - 1);
        assertThat(testNationality.getArName()).isEqualTo(UPDATED_AR_NAME);
        assertThat(testNationality.getEnName()).isEqualTo(UPDATED_EN_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingNationality() throws Exception {
        int databaseSizeBeforeUpdate = nationalityRepository.findAll().size();

        // Create the Nationality

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNationalityMockMvc.perform(put("/api/nationalities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nationality)))
            .andExpect(status().isBadRequest());

        // Validate the Nationality in the database
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNationality() throws Exception {
        // Initialize the database
        nationalityRepository.saveAndFlush(nationality);

        int databaseSizeBeforeDelete = nationalityRepository.findAll().size();

        // Delete the nationality
        restNationalityMockMvc.perform(delete("/api/nationalities/{id}", nationality.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Nationality> nationalityList = nationalityRepository.findAll();
        assertThat(nationalityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nationality.class);
        Nationality nationality1 = new Nationality();
        nationality1.setId(1L);
        Nationality nationality2 = new Nationality();
        nationality2.setId(nationality1.getId());
        assertThat(nationality1).isEqualTo(nationality2);
        nationality2.setId(2L);
        assertThat(nationality1).isNotEqualTo(nationality2);
        nationality1.setId(null);
        assertThat(nationality1).isNotEqualTo(nationality2);
    }
}
