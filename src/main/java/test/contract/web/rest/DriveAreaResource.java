package test.contract.web.rest;

import test.contract.domain.DriveArea;
import test.contract.repository.DriveAreaRepository;
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
 * REST controller for managing {@link test.contract.domain.DriveArea}.
 */
@RestController
@RequestMapping("/api")
public class DriveAreaResource {

    private final Logger log = LoggerFactory.getLogger(DriveAreaResource.class);

    private static final String ENTITY_NAME = "driveArea";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DriveAreaRepository driveAreaRepository;

    public DriveAreaResource(DriveAreaRepository driveAreaRepository) {
        this.driveAreaRepository = driveAreaRepository;
    }

    /**
     * {@code POST  /drive-areas} : Create a new driveArea.
     *
     * @param driveArea the driveArea to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new driveArea, or with status {@code 400 (Bad Request)} if the driveArea has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/drive-areas")
    public ResponseEntity<DriveArea> createDriveArea(@RequestBody DriveArea driveArea) throws URISyntaxException {
        log.debug("REST request to save DriveArea : {}", driveArea);
        if (driveArea.getId() != null) {
            throw new BadRequestAlertException("A new driveArea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DriveArea result = driveAreaRepository.save(driveArea);
        return ResponseEntity.created(new URI("/api/drive-areas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /drive-areas} : Updates an existing driveArea.
     *
     * @param driveArea the driveArea to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated driveArea,
     * or with status {@code 400 (Bad Request)} if the driveArea is not valid,
     * or with status {@code 500 (Internal Server Error)} if the driveArea couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/drive-areas")
    public ResponseEntity<DriveArea> updateDriveArea(@RequestBody DriveArea driveArea) throws URISyntaxException {
        log.debug("REST request to update DriveArea : {}", driveArea);
        if (driveArea.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DriveArea result = driveAreaRepository.save(driveArea);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, driveArea.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /drive-areas} : get all the driveAreas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of driveAreas in body.
     */
    @GetMapping("/drive-areas")
    public List<DriveArea> getAllDriveAreas() {
        log.debug("REST request to get all DriveAreas");
        return driveAreaRepository.findAll();
    }

    /**
     * {@code GET  /drive-areas/:id} : get the "id" driveArea.
     *
     * @param id the id of the driveArea to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the driveArea, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/drive-areas/{id}")
    public ResponseEntity<DriveArea> getDriveArea(@PathVariable Long id) {
        log.debug("REST request to get DriveArea : {}", id);
        Optional<DriveArea> driveArea = driveAreaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(driveArea);
    }

    /**
     * {@code DELETE  /drive-areas/:id} : delete the "id" driveArea.
     *
     * @param id the id of the driveArea to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/drive-areas/{id}")
    public ResponseEntity<Void> deleteDriveArea(@PathVariable Long id) {
        log.debug("REST request to delete DriveArea : {}", id);
        driveAreaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
