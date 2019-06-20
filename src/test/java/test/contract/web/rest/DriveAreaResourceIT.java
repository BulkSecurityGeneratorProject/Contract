package test.contract.web.rest;

import test.contract.ContractApp;
import test.contract.domain.DriveArea;
import test.contract.repository.DriveAreaRepository;
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
 * Integration tests for the {@Link DriveAreaResource} REST controller.
 */
@SpringBootTest(classes = ContractApp.class)
public class DriveAreaResourceIT {

    private static final String DEFAULT_AR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EN_NAME = "BBBBBBBBBB";

    @Autowired
    private DriveAreaRepository driveAreaRepository;

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

    private MockMvc restDriveAreaMockMvc;

    private DriveArea driveArea;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DriveAreaResource driveAreaResource = new DriveAreaResource(driveAreaRepository);
        this.restDriveAreaMockMvc = MockMvcBuilders.standaloneSetup(driveAreaResource)
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
    public static DriveArea createEntity(EntityManager em) {
        DriveArea driveArea = new DriveArea()
            .arName(DEFAULT_AR_NAME)
            .enName(DEFAULT_EN_NAME);
        return driveArea;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DriveArea createUpdatedEntity(EntityManager em) {
        DriveArea driveArea = new DriveArea()
            .arName(UPDATED_AR_NAME)
            .enName(UPDATED_EN_NAME);
        return driveArea;
    }

    @BeforeEach
    public void initTest() {
        driveArea = createEntity(em);
    }

    @Test
    @Transactional
    public void createDriveArea() throws Exception {
        int databaseSizeBeforeCreate = driveAreaRepository.findAll().size();

        // Create the DriveArea
        restDriveAreaMockMvc.perform(post("/api/drive-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driveArea)))
            .andExpect(status().isCreated());

        // Validate the DriveArea in the database
        List<DriveArea> driveAreaList = driveAreaRepository.findAll();
        assertThat(driveAreaList).hasSize(databaseSizeBeforeCreate + 1);
        DriveArea testDriveArea = driveAreaList.get(driveAreaList.size() - 1);
        assertThat(testDriveArea.getArName()).isEqualTo(DEFAULT_AR_NAME);
        assertThat(testDriveArea.getEnName()).isEqualTo(DEFAULT_EN_NAME);
    }

    @Test
    @Transactional
    public void createDriveAreaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = driveAreaRepository.findAll().size();

        // Create the DriveArea with an existing ID
        driveArea.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDriveAreaMockMvc.perform(post("/api/drive-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driveArea)))
            .andExpect(status().isBadRequest());

        // Validate the DriveArea in the database
        List<DriveArea> driveAreaList = driveAreaRepository.findAll();
        assertThat(driveAreaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDriveAreas() throws Exception {
        // Initialize the database
        driveAreaRepository.saveAndFlush(driveArea);

        // Get all the driveAreaList
        restDriveAreaMockMvc.perform(get("/api/drive-areas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(driveArea.getId().intValue())))
            .andExpect(jsonPath("$.[*].arName").value(hasItem(DEFAULT_AR_NAME.toString())))
            .andExpect(jsonPath("$.[*].enName").value(hasItem(DEFAULT_EN_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getDriveArea() throws Exception {
        // Initialize the database
        driveAreaRepository.saveAndFlush(driveArea);

        // Get the driveArea
        restDriveAreaMockMvc.perform(get("/api/drive-areas/{id}", driveArea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(driveArea.getId().intValue()))
            .andExpect(jsonPath("$.arName").value(DEFAULT_AR_NAME.toString()))
            .andExpect(jsonPath("$.enName").value(DEFAULT_EN_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDriveArea() throws Exception {
        // Get the driveArea
        restDriveAreaMockMvc.perform(get("/api/drive-areas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDriveArea() throws Exception {
        // Initialize the database
        driveAreaRepository.saveAndFlush(driveArea);

        int databaseSizeBeforeUpdate = driveAreaRepository.findAll().size();

        // Update the driveArea
        DriveArea updatedDriveArea = driveAreaRepository.findById(driveArea.getId()).get();
        // Disconnect from session so that the updates on updatedDriveArea are not directly saved in db
        em.detach(updatedDriveArea);
        updatedDriveArea
            .arName(UPDATED_AR_NAME)
            .enName(UPDATED_EN_NAME);

        restDriveAreaMockMvc.perform(put("/api/drive-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDriveArea)))
            .andExpect(status().isOk());

        // Validate the DriveArea in the database
        List<DriveArea> driveAreaList = driveAreaRepository.findAll();
        assertThat(driveAreaList).hasSize(databaseSizeBeforeUpdate);
        DriveArea testDriveArea = driveAreaList.get(driveAreaList.size() - 1);
        assertThat(testDriveArea.getArName()).isEqualTo(UPDATED_AR_NAME);
        assertThat(testDriveArea.getEnName()).isEqualTo(UPDATED_EN_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDriveArea() throws Exception {
        int databaseSizeBeforeUpdate = driveAreaRepository.findAll().size();

        // Create the DriveArea

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDriveAreaMockMvc.perform(put("/api/drive-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driveArea)))
            .andExpect(status().isBadRequest());

        // Validate the DriveArea in the database
        List<DriveArea> driveAreaList = driveAreaRepository.findAll();
        assertThat(driveAreaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDriveArea() throws Exception {
        // Initialize the database
        driveAreaRepository.saveAndFlush(driveArea);

        int databaseSizeBeforeDelete = driveAreaRepository.findAll().size();

        // Delete the driveArea
        restDriveAreaMockMvc.perform(delete("/api/drive-areas/{id}", driveArea.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<DriveArea> driveAreaList = driveAreaRepository.findAll();
        assertThat(driveAreaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DriveArea.class);
        DriveArea driveArea1 = new DriveArea();
        driveArea1.setId(1L);
        DriveArea driveArea2 = new DriveArea();
        driveArea2.setId(driveArea1.getId());
        assertThat(driveArea1).isEqualTo(driveArea2);
        driveArea2.setId(2L);
        assertThat(driveArea1).isNotEqualTo(driveArea2);
        driveArea1.setId(null);
        assertThat(driveArea1).isNotEqualTo(driveArea2);
    }
}
