package test.contract.web.rest;

import test.contract.domain.LicenseType;
import test.contract.repository.LicenseTypeRepository;
import test.contract.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link test.contract.domain.LicenseType}.
 */
@RestController
@RequestMapping("/api")
public class LicenseTypeResource {

    private final Logger log = LoggerFactory.getLogger(LicenseTypeResource.class);

    private static final String ENTITY_NAME = "licenseType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LicenseTypeRepository licenseTypeRepository;

    public LicenseTypeResource(LicenseTypeRepository licenseTypeRepository) {
        this.licenseTypeRepository = licenseTypeRepository;
    }

    /**
     * {@code POST  /license-types} : Create a new licenseType.
     *
     * @param licenseType the licenseType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new licenseType, or with status {@code 400 (Bad Request)} if the licenseType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/license-types")
    public ResponseEntity<LicenseType> createLicenseType(@RequestBody LicenseType licenseType) throws URISyntaxException {
        log.debug("REST request to save LicenseType : {}", licenseType);
        if (licenseType.getId() != null) {
            throw new BadRequestAlertException("A new licenseType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LicenseType result = licenseTypeRepository.save(licenseType);
        return ResponseEntity.created(new URI("/api/license-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /license-types} : Updates an existing licenseType.
     *
     * @param licenseType the licenseType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated licenseType,
     * or with status {@code 400 (Bad Request)} if the licenseType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the licenseType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/license-types")
    public ResponseEntity<LicenseType> updateLicenseType(@RequestBody LicenseType licenseType) throws URISyntaxException {
        log.debug("REST request to update LicenseType : {}", licenseType);
        if (licenseType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LicenseType result = licenseTypeRepository.save(licenseType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, licenseType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /license-types} : get all the licenseTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of licenseTypes in body.
     */
    @GetMapping("/license-types")
    public List<LicenseType> getAllLicenseTypes() {
        log.debug("REST request to get all LicenseTypes");
        return licenseTypeRepository.findAll();
    }

    /**
     * {@code GET  /license-types/:id} : get the "id" licenseType.
     *
     * @param id the id of the licenseType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the licenseType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/license-types/{id}")
    public ResponseEntity<LicenseType> getLicenseType(@PathVariable Long id) {
        log.debug("REST request to get LicenseType : {}", id);
        Optional<LicenseType> licenseType = licenseTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(licenseType);
    }

    /**
     * {@code DELETE  /license-types/:id} : delete the "id" licenseType.
     *
     * @param id the id of the licenseType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/license-types/{id}")
    public ResponseEntity<Void> deleteLicenseType(@PathVariable Long id) {
        log.debug("REST request to delete LicenseType : {}", id);
        licenseTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
