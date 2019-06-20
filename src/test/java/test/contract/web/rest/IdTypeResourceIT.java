package test.contract.web.rest;

import test.contract.ContractApp;
import test.contract.domain.IdType;
import test.contract.repository.IdTypeRepository;
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
 * Integration tests for the {@Link IdTypeResource} REST controller.
 */
@SpringBootTest(classes = ContractApp.class)
public class IdTypeResourceIT {

    private static final String DEFAULT_AR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EN_NAME = "BBBBBBBBBB";

    @Autowired
    private IdTypeRepository idTypeRepository;

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

    private MockMvc restIdTypeMockMvc;

    private IdType idType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IdTypeResource idTypeResource = new IdTypeResource(idTypeRepository);
        this.restIdTypeMockMvc = MockMvcBuilders.standaloneSetup(idTypeResource)
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
    public static IdType createEntity(EntityManager em) {
        IdType idType = new IdType()
            .arName(DEFAULT_AR_NAME)
            .enName(DEFAULT_EN_NAME);
        return idType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IdType createUpdatedEntity(EntityManager em) {
        IdType idType = new IdType()
            .arName(UPDATED_AR_NAME)
            .enName(UPDATED_EN_NAME);
        return idType;
    }

    @BeforeEach
    public void initTest() {
        idType = createEntity(em);
    }

    @Test
    @Transactional
    public void createIdType() throws Exception {
        int databaseSizeBeforeCreate = idTypeRepository.findAll().size();

        // Create the IdType
        restIdTypeMockMvc.perform(post("/api/id-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idType)))
            .andExpect(status().isCreated());

        // Validate the IdType in the database
        List<IdType> idTypeList = idTypeRepository.findAll();
        assertThat(idTypeList).hasSize(databaseSizeBeforeCreate + 1);
        IdType testIdType = idTypeList.get(idTypeList.size() - 1);
        assertThat(testIdType.getArName()).isEqualTo(DEFAULT_AR_NAME);
        assertThat(testIdType.getEnName()).isEqualTo(DEFAULT_EN_NAME);
    }

    @Test
    @Transactional
    public void createIdTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = idTypeRepository.findAll().size();

        // Create the IdType with an existing ID
        idType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdTypeMockMvc.perform(post("/api/id-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idType)))
            .andExpect(status().isBadRequest());

        // Validate the IdType in the database
        List<IdType> idTypeList = idTypeRepository.findAll();
        assertThat(idTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllIdTypes() throws Exception {
        // Initialize the database
        idTypeRepository.saveAndFlush(idType);

        // Get all the idTypeList
        restIdTypeMockMvc.perform(get("/api/id-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(idType.getId().intValue())))
            .andExpect(jsonPath("$.[*].arName").value(hasItem(DEFAULT_AR_NAME.toString())))
            .andExpect(jsonPath("$.[*].enName").value(hasItem(DEFAULT_EN_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getIdType() throws Exception {
        // Initialize the database
        idTypeRepository.saveAndFlush(idType);

        // Get the idType
        restIdTypeMockMvc.perform(get("/api/id-types/{id}", idType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(idType.getId().intValue()))
            .andExpect(jsonPath("$.arName").value(DEFAULT_AR_NAME.toString()))
            .andExpect(jsonPath("$.enName").value(DEFAULT_EN_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIdType() throws Exception {
        // Get the idType
        restIdTypeMockMvc.perform(get("/api/id-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIdType() throws Exception {
        // Initialize the database
        idTypeRepository.saveAndFlush(idType);

        int databaseSizeBeforeUpdate = idTypeRepository.findAll().size();

        // Update the idType
        IdType updatedIdType = idTypeRepository.findById(idType.getId()).get();
        // Disconnect from session so that the updates on updatedIdType are not directly saved in db
        em.detach(updatedIdType);
        updatedIdType
            .arName(UPDATED_AR_NAME)
            .enName(UPDATED_EN_NAME);

        restIdTypeMockMvc.perform(put("/api/id-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIdType)))
            .andExpect(status().isOk());

        // Validate the IdType in the database
        List<IdType> idTypeList = idTypeRepository.findAll();
        assertThat(idTypeList).hasSize(databaseSizeBeforeUpdate);
        IdType testIdType = idTypeList.get(idTypeList.size() - 1);
        assertThat(testIdType.getArName()).isEqualTo(UPDATED_AR_NAME);
        assertThat(testIdType.getEnName()).isEqualTo(UPDATED_EN_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingIdType() throws Exception {
        int databaseSizeBeforeUpdate = idTypeRepository.findAll().size();

        // Create the IdType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdTypeMockMvc.perform(put("/api/id-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idType)))
            .andExpect(status().isBadRequest());

        // Validate the IdType in the database
        List<IdType> idTypeList = idTypeRepository.findAll();
        assertThat(idTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIdType() throws Exception {
        // Initialize the database
        idTypeRepository.saveAndFlush(idType);

        int databaseSizeBeforeDelete = idTypeRepository.findAll().size();

        // Delete the idType
        restIdTypeMockMvc.perform(delete("/api/id-types/{id}", idType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<IdType> idTypeList = idTypeRepository.findAll();
        assertThat(idTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IdType.class);
        IdType idType1 = new IdType();
        idType1.setId(1L);
        IdType idType2 = new IdType();
        idType2.setId(idType1.getId());
        assertThat(idType1).isEqualTo(idType2);
        idType2.setId(2L);
        assertThat(idType1).isNotEqualTo(idType2);
        idType1.setId(null);
        assertThat(idType1).isNotEqualTo(idType2);
    }
}
