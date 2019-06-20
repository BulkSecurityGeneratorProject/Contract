package test.contract.web.rest;

import test.contract.domain.VehicleItemStatus;
import test.contract.repository.VehicleItemStatusRepository;
import test.contract.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link test.contract.domain.VehicleItemStatus}.
 */
@RestController
@RequestMapping("/api")
public class VehicleItemStatusResource {

    private final Logger log = LoggerFactory.getLogger(VehicleItemStatusResource.class);

    private static final String ENTITY_NAME = "vehicleItemStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehicleItemStatusRepository vehicleItemStatusRepository;

    public VehicleItemStatusResource(VehicleItemStatusRepository vehicleItemStatusRepository) {
        this.vehicleItemStatusRepository = vehicleItemStatusRepository;
    }

    /**
     * {@code POST  /vehicle-item-statuses} : Create a new vehicleItemStatus.
     *
     * @param vehicleItemStatus the vehicleItemStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehicleItemStatus, or with status {@code 400 (Bad Request)} if the vehicleItemStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vehicle-item-statuses")
    public ResponseEntity<VehicleItemStatus> createVehicleItemStatus(@Valid @RequestBody VehicleItemStatus vehicleItemStatus) throws URISyntaxException {
        log.debug("REST request to save VehicleItemStatus : {}", vehicleItemStatus);
        if (vehicleItemStatus.getId() != null) {
            throw new BadRequestAlertException("A new vehicleItemStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleItemStatus result = vehicleItemStatusRepository.save(vehicleItemStatus);
        return ResponseEntity.created(new URI("/api/vehicle-item-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vehicle-item-statuses} : Updates an existing vehicleItemStatus.
     *
     * @param vehicleItemStatus the vehicleItemStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleItemStatus,
     * or with status {@code 400 (Bad Request)} if the vehicleItemStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehicleItemStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vehicle-item-statuses")
    public ResponseEntity<VehicleItemStatus> updateVehicleItemStatus(@Valid @RequestBody VehicleItemStatus vehicleItemStatus) throws URISyntaxException {
        log.debug("REST request to update VehicleItemStatus : {}", vehicleItemStatus);
        if (vehicleItemStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VehicleItemStatus result = vehicleItemStatusRepository.save(vehicleItemStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vehicleItemStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vehicle-item-statuses} : get all the vehicleItemStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicleItemStatuses in body.
     */
    @GetMapping("/vehicle-item-statuses")
    public List<VehicleItemStatus> getAllVehicleItemStatuses() {
        log.debug("REST request to get all VehicleItemStatuses");
        return vehicleItemStatusRepository.findAll();
    }

    /**
     * {@code GET  /vehicle-item-statuses/:id} : get the "id" vehicleItemStatus.
     *
     * @param id the id of the vehicleItemStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehicleItemStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehicle-item-statuses/{id}")
    public ResponseEntity<VehicleItemStatus> getVehicleItemStatus(@PathVariable Long id) {
        log.debug("REST request to get VehicleItemStatus : {}", id);
        Optional<VehicleItemStatus> vehicleItemStatus = vehicleItemStatusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vehicleItemStatus);
    }

    /**
     * {@code DELETE  /vehicle-item-statuses/:id} : delete the "id" vehicleItemStatus.
     *
     * @param id the id of the vehicleItemStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vehicle-item-statuses/{id}")
    public ResponseEntity<Void> deleteVehicleItemStatus(@PathVariable Long id) {
        log.debug("REST request to delete VehicleItemStatus : {}", id);
        vehicleItemStatusRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
