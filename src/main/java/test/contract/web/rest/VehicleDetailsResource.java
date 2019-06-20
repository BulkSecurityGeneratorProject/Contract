package test.contract.web.rest;

import test.contract.domain.VehicleDetails;
import test.contract.repository.VehicleDetailsRepository;
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
 * REST controller for managing {@link test.contract.domain.VehicleDetails}.
 */
@RestController
@RequestMapping("/api")
public class VehicleDetailsResource {

    private final Logger log = LoggerFactory.getLogger(VehicleDetailsResource.class);

    private static final String ENTITY_NAME = "vehicleDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehicleDetailsRepository vehicleDetailsRepository;

    public VehicleDetailsResource(VehicleDetailsRepository vehicleDetailsRepository) {
        this.vehicleDetailsRepository = vehicleDetailsRepository;
    }

    /**
     * {@code POST  /vehicle-details} : Create a new vehicleDetails.
     *
     * @param vehicleDetails the vehicleDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehicleDetails, or with status {@code 400 (Bad Request)} if the vehicleDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vehicle-details")
    public ResponseEntity<VehicleDetails> createVehicleDetails(@Valid @RequestBody VehicleDetails vehicleDetails) throws URISyntaxException {
        log.debug("REST request to save VehicleDetails : {}", vehicleDetails);
        if (vehicleDetails.getId() != null) {
            throw new BadRequestAlertException("A new vehicleDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleDetails result = vehicleDetailsRepository.save(vehicleDetails);
        return ResponseEntity.created(new URI("/api/vehicle-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vehicle-details} : Updates an existing vehicleDetails.
     *
     * @param vehicleDetails the vehicleDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleDetails,
     * or with status {@code 400 (Bad Request)} if the vehicleDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehicleDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vehicle-details")
    public ResponseEntity<VehicleDetails> updateVehicleDetails(@Valid @RequestBody VehicleDetails vehicleDetails) throws URISyntaxException {
        log.debug("REST request to update VehicleDetails : {}", vehicleDetails);
        if (vehicleDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VehicleDetails result = vehicleDetailsRepository.save(vehicleDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vehicleDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vehicle-details} : get all the vehicleDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicleDetails in body.
     */
    @GetMapping("/vehicle-details")
    public List<VehicleDetails> getAllVehicleDetails() {
        log.debug("REST request to get all VehicleDetails");
        return vehicleDetailsRepository.findAll();
    }

    /**
     * {@code GET  /vehicle-details/:id} : get the "id" vehicleDetails.
     *
     * @param id the id of the vehicleDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehicleDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehicle-details/{id}")
    public ResponseEntity<VehicleDetails> getVehicleDetails(@PathVariable Long id) {
        log.debug("REST request to get VehicleDetails : {}", id);
        Optional<VehicleDetails> vehicleDetails = vehicleDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vehicleDetails);
    }

    /**
     * {@code DELETE  /vehicle-details/:id} : delete the "id" vehicleDetails.
     *
     * @param id the id of the vehicleDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vehicle-details/{id}")
    public ResponseEntity<Void> deleteVehicleDetails(@PathVariable Long id) {
        log.debug("REST request to delete VehicleDetails : {}", id);
        vehicleDetailsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
