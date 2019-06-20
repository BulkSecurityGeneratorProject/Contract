package test.contract.web.rest;

import test.contract.ContractApp;
import test.contract.domain.OilType;
import test.contract.repository.OilTypeRepository;
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
 * Integration tests for the {@Link OilTypeResource} REST controller.
 */
@SpringBootTest(classes = ContractApp.class)
public class OilTypeResourceIT {

    private static final String DEFAULT_AR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EN_NAME = "BBBBBBBBBB";

    @Autowired
    private OilTypeRepository oilTypeRepository;

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

    private MockMvc restOilTypeMockMvc;

    private OilType oilType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OilTypeResource oilTypeResource = new OilTypeResource(oilTypeRepository);
        this.restOilTypeMockMvc = MockMvcBuilders.standaloneSetup(oilTypeResource)
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
    public static OilType createEntity(EntityManager em) {
        OilType oilType = new OilType()
            .arName(DEFAULT_AR_NAME)
            .enName(DEFAULT_EN_NAME);
        return oilType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OilType createUpdatedEntity(EntityManager em) {
        OilType oilType = new OilType()
            .arName(UPDATED_AR_NAME)
            .enName(UPDATED_EN_NAME);
        return oilType;
    }

    @BeforeEach
    public void initTest() {
        oilType = createEntity(em);
    }

    @Test
    @Transactional
    public void createOilType() throws Exception {
        int databaseSizeBeforeCreate = oilTypeRepository.findAll().size();

        // Create the OilType
        restOilTypeMockMvc.perform(post("/api/oil-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oilType)))
            .andExpect(status().isCreated());

        // Validate the OilType in the database
        List<OilType> oilTypeList = oilTypeRepository.findAll();
        assertThat(oilTypeList).hasSize(databaseSizeBeforeCreate + 1);
        OilType testOilType = oilTypeList.get(oilTypeList.size() - 1);
        assertThat(testOilType.getArName()).isEqualTo(DEFAULT_AR_NAME);
        assertThat(testOilType.getEnName()).isEqualTo(DEFAULT_EN_NAME);
    }

    @Test
    @Transactional
    public void createOilTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = oilTypeRepository.findAll().size();

        // Create the OilType with an existing ID
        oilType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOilTypeMockMvc.perform(post("/api/oil-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oilType)))
            .andExpect(status().isBadRequest());

        // Validate the OilType in the database
        List<OilType> oilTypeList = oilTypeRepository.findAll();
        assertThat(oilTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOilTypes() throws Exception {
        // Initialize the database
        oilTypeRepository.saveAndFlush(oilType);

        // Get all the oilTypeList
        restOilTypeMockMvc.perform(get("/api/oil-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oilType.getId().intValue())))
            .andExpect(jsonPath("$.[*].arName").value(hasItem(DEFAULT_AR_NAME.toString())))
            .andExpect(jsonPath("$.[*].enName").value(hasItem(DEFAULT_EN_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getOilType() throws Exception {
        // Initialize the database
        oilTypeRepository.saveAndFlush(oilType);

        // Get the oilType
        restOilTypeMockMvc.perform(get("/api/oil-types/{id}", oilType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(oilType.getId().intValue()))
            .andExpect(jsonPath("$.arName").value(DEFAULT_AR_NAME.toString()))
            .andExpect(jsonPath("$.enName").value(DEFAULT_EN_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOilType() throws Exception {
        // Get the oilType
        restOilTypeMockMvc.perform(get("/api/oil-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOilType() throws Exception {
        // Initialize the database
        oilTypeRepository.saveAndFlush(oilType);

        int databaseSizeBeforeUpdate = oilTypeRepository.findAll().size();

        // Update the oilType
        OilType updatedOilType = oilTypeRepository.findById(oilType.getId()).get();
        // Disconnect from session so that the updates on updatedOilType are not directly saved in db
        em.detach(updatedOilType);
        updatedOilType
            .arName(UPDATED_AR_NAME)
            .enName(UPDATED_EN_NAME);

        restOilTypeMockMvc.perform(put("/api/oil-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOilType)))
            .andExpect(status().isOk());

        // Validate the OilType in the database
        List<OilType> oilTypeList = oilTypeRepository.findAll();
        assertThat(oilTypeList).hasSize(databaseSizeBeforeUpdate);
        OilType testOilType = oilTypeList.get(oilTypeList.size() - 1);
        assertThat(testOilType.getArName()).isEqualTo(UPDATED_AR_NAME);
        assertThat(testOilType.getEnName()).isEqualTo(UPDATED_EN_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingOilType() throws Exception {
        int databaseSizeBeforeUpdate = oilTypeRepository.findAll().size();

        // Create the OilType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOilTypeMockMvc.perform(put("/api/oil-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oilType)))
            .andExpect(status().isBadRequest());

        // Validate the OilType in the database
        List<OilType> oilTypeList = oilTypeRepository.findAll();
        assertThat(oilTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOilType() throws Exception {
        // Initialize the database
        oilTypeRepository.saveAndFlush(oilType);

        int databaseSizeBeforeDelete = oilTypeRepository.findAll().size();

        // Delete the oilType
        restOilTypeMockMvc.perform(delete("/api/oil-types/{id}", oilType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<OilType> oilTypeList = oilTypeRepository.findAll();
        assertThat(oilTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OilType.class);
        OilType oilType1 = new OilType();
        oilType1.setId(1L);
        OilType oilType2 = new OilType();
        oilType2.setId(oilType1.getId());
        assertThat(oilType1).isEqualTo(oilType2);
        oilType2.setId(2L);
        assertThat(oilType1).isNotEqualTo(oilType2);
        oilType1.setId(null);
        assertThat(oilType1).isNotEqualTo(oilType2);
    }
}
