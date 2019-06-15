package test.contract.web.rest;

import test.contract.ContractApp;
import test.contract.domain.COCO;
import test.contract.repository.COCORepository;
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
 * Integration tests for the {@Link COCOResource} REST controller.
 */
@SpringBootTest(classes = ContractApp.class)
public class COCOResourceIT {

    private static final String DEFAULT_COCO = "AAAAAAAAAA";
    private static final String UPDATED_COCO = "BBBBBBBBBB";

    @Autowired
    private COCORepository cOCORepository;

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

    private MockMvc restCOCOMockMvc;

    private COCO cOCO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final COCOResource cOCOResource = new COCOResource(cOCORepository);
        this.restCOCOMockMvc = MockMvcBuilders.standaloneSetup(cOCOResource)
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
    public static COCO createEntity(EntityManager em) {
        COCO cOCO = new COCO()
            .coco(DEFAULT_COCO);
        return cOCO;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static COCO createUpdatedEntity(EntityManager em) {
        COCO cOCO = new COCO()
            .coco(UPDATED_COCO);
        return cOCO;
    }

    @BeforeEach
    public void initTest() {
        cOCO = createEntity(em);
    }

    @Test
    @Transactional
    public void createCOCO() throws Exception {
        int databaseSizeBeforeCreate = cOCORepository.findAll().size();

        // Create the COCO
        restCOCOMockMvc.perform(post("/api/cocos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cOCO)))
            .andExpect(status().isCreated());

        // Validate the COCO in the database
        List<COCO> cOCOList = cOCORepository.findAll();
        assertThat(cOCOList).hasSize(databaseSizeBeforeCreate + 1);
        COCO testCOCO = cOCOList.get(cOCOList.size() - 1);
        assertThat(testCOCO.getCoco()).isEqualTo(DEFAULT_COCO);
    }

    @Test
    @Transactional
    public void createCOCOWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cOCORepository.findAll().size();

        // Create the COCO with an existing ID
        cOCO.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCOCOMockMvc.perform(post("/api/cocos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cOCO)))
            .andExpect(status().isBadRequest());

        // Validate the COCO in the database
        List<COCO> cOCOList = cOCORepository.findAll();
        assertThat(cOCOList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCOCOS() throws Exception {
        // Initialize the database
        cOCORepository.saveAndFlush(cOCO);

        // Get all the cOCOList
        restCOCOMockMvc.perform(get("/api/cocos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cOCO.getId().intValue())))
            .andExpect(jsonPath("$.[*].coco").value(hasItem(DEFAULT_COCO.toString())));
    }
    
    @Test
    @Transactional
    public void getCOCO() throws Exception {
        // Initialize the database
        cOCORepository.saveAndFlush(cOCO);

        // Get the cOCO
        restCOCOMockMvc.perform(get("/api/cocos/{id}", cOCO.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cOCO.getId().intValue()))
            .andExpect(jsonPath("$.coco").value(DEFAULT_COCO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCOCO() throws Exception {
        // Get the cOCO
        restCOCOMockMvc.perform(get("/api/cocos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCOCO() throws Exception {
        // Initialize the database
        cOCORepository.saveAndFlush(cOCO);

        int databaseSizeBeforeUpdate = cOCORepository.findAll().size();

        // Update the cOCO
        COCO updatedCOCO = cOCORepository.findById(cOCO.getId()).get();
        // Disconnect from session so that the updates on updatedCOCO are not directly saved in db
        em.detach(updatedCOCO);
        updatedCOCO
            .coco(UPDATED_COCO);

        restCOCOMockMvc.perform(put("/api/cocos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCOCO)))
            .andExpect(status().isOk());

        // Validate the COCO in the database
        List<COCO> cOCOList = cOCORepository.findAll();
        assertThat(cOCOList).hasSize(databaseSizeBeforeUpdate);
        COCO testCOCO = cOCOList.get(cOCOList.size() - 1);
        assertThat(testCOCO.getCoco()).isEqualTo(UPDATED_COCO);
    }

    @Test
    @Transactional
    public void updateNonExistingCOCO() throws Exception {
        int databaseSizeBeforeUpdate = cOCORepository.findAll().size();

        // Create the COCO

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCOCOMockMvc.perform(put("/api/cocos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cOCO)))
            .andExpect(status().isBadRequest());

        // Validate the COCO in the database
        List<COCO> cOCOList = cOCORepository.findAll();
        assertThat(cOCOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCOCO() throws Exception {
        // Initialize the database
        cOCORepository.saveAndFlush(cOCO);

        int databaseSizeBeforeDelete = cOCORepository.findAll().size();

        // Delete the cOCO
        restCOCOMockMvc.perform(delete("/api/cocos/{id}", cOCO.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<COCO> cOCOList = cOCORepository.findAll();
        assertThat(cOCOList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(COCO.class);
        COCO cOCO1 = new COCO();
        cOCO1.setId(1L);
        COCO cOCO2 = new COCO();
        cOCO2.setId(cOCO1.getId());
        assertThat(cOCO1).isEqualTo(cOCO2);
        cOCO2.setId(2L);
        assertThat(cOCO1).isNotEqualTo(cOCO2);
        cOCO1.setId(null);
        assertThat(cOCO1).isNotEqualTo(cOCO2);
    }
}
