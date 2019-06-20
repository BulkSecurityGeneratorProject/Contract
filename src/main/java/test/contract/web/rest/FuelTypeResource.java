package test.contract.web.rest;

import test.contract.domain.FuelType;
import test.contract.repository.FuelTypeRepository;
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
 * REST controller for managing {@link test.contract.domain.FuelType}.
 */
@RestController
@RequestMapping("/api")
public class FuelTypeResource {

    private final Logger log = LoggerFactory.getLogger(FuelTypeResource.class);

    private static final String ENTITY_NAME = "fuelType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuelTypeRepository fuelTypeRepository;

    public FuelTypeResource(FuelTypeRepository fuelTypeRepository) {
        this.fuelTypeRepository = fuelTypeRepository;
    }

    /**
     * {@code POST  /fuel-types} : Create a new fuelType.
     *
     * @param fuelType the fuelType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fuelType, or with status {@code 400 (Bad Request)} if the fuelType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fuel-types")
    public ResponseEntity<FuelType> createFuelType(@RequestBody FuelType fuelType) throws URISyntaxException {
        log.debug("REST request to save FuelType : {}", fuelType);
        if (fuelType.getId() != null) {
            throw new BadRequestAlertException("A new fuelType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FuelType result = fuelTypeRepository.save(fuelType);
        return ResponseEntity.created(new URI("/api/fuel-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fuel-types} : Updates an existing fuelType.
     *
     * @param fuelType the fuelType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuelType,
     * or with status {@code 400 (Bad Request)} if the fuelType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fuelType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fuel-types")
    public ResponseEntity<FuelType> updateFuelType(@RequestBody FuelType fuelType) throws URISyntaxException {
        log.debug("REST request to update FuelType : {}", fuelType);
        if (fuelType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FuelType result = fuelTypeRepository.save(fuelType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fuelType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fuel-types} : get all the fuelTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fuelTypes in body.
     */
    @GetMapping("/fuel-types")
    public List<FuelType> getAllFuelTypes() {
        log.debug("REST request to get all FuelTypes");
        return fuelTypeRepository.findAll();
    }

    /**
     * {@code GET  /fuel-types/:id} : get the "id" fuelType.
     *
     * @param id the id of the fuelType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fuelType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fuel-types/{id}")
    public ResponseEntity<FuelType> getFuelType(@PathVariable Long id) {
        log.debug("REST request to get FuelType : {}", id);
        Optional<FuelType> fuelType = fuelTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fuelType);
    }

    /**
     * {@code DELETE  /fuel-types/:id} : delete the "id" fuelType.
     *
     * @param id the id of the fuelType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fuel-types/{id}")
    public ResponseEntity<Void> deleteFuelType(@PathVariable Long id) {
        log.debug("REST request to delete FuelType : {}", id);
        fuelTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
