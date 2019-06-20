package test.contract.web.rest;

import test.contract.domain.OilType;
import test.contract.repository.OilTypeRepository;
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
 * REST controller for managing {@link test.contract.domain.OilType}.
 */
@RestController
@RequestMapping("/api")
public class OilTypeResource {

    private final Logger log = LoggerFactory.getLogger(OilTypeResource.class);

    private static final String ENTITY_NAME = "oilType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OilTypeRepository oilTypeRepository;

    public OilTypeResource(OilTypeRepository oilTypeRepository) {
        this.oilTypeRepository = oilTypeRepository;
    }

    /**
     * {@code POST  /oil-types} : Create a new oilType.
     *
     * @param oilType the oilType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new oilType, or with status {@code 400 (Bad Request)} if the oilType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/oil-types")
    public ResponseEntity<OilType> createOilType(@RequestBody OilType oilType) throws URISyntaxException {
        log.debug("REST request to save OilType : {}", oilType);
        if (oilType.getId() != null) {
            throw new BadRequestAlertException("A new oilType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OilType result = oilTypeRepository.save(oilType);
        return ResponseEntity.created(new URI("/api/oil-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /oil-types} : Updates an existing oilType.
     *
     * @param oilType the oilType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oilType,
     * or with status {@code 400 (Bad Request)} if the oilType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the oilType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/oil-types")
    public ResponseEntity<OilType> updateOilType(@RequestBody OilType oilType) throws URISyntaxException {
        log.debug("REST request to update OilType : {}", oilType);
        if (oilType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OilType result = oilTypeRepository.save(oilType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, oilType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /oil-types} : get all the oilTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of oilTypes in body.
     */
    @GetMapping("/oil-types")
    public List<OilType> getAllOilTypes() {
        log.debug("REST request to get all OilTypes");
        return oilTypeRepository.findAll();
    }

    /**
     * {@code GET  /oil-types/:id} : get the "id" oilType.
     *
     * @param id the id of the oilType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the oilType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/oil-types/{id}")
    public ResponseEntity<OilType> getOilType(@PathVariable Long id) {
        log.debug("REST request to get OilType : {}", id);
        Optional<OilType> oilType = oilTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(oilType);
    }

    /**
     * {@code DELETE  /oil-types/:id} : delete the "id" oilType.
     *
     * @param id the id of the oilType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/oil-types/{id}")
    public ResponseEntity<Void> deleteOilType(@PathVariable Long id) {
        log.debug("REST request to delete OilType : {}", id);
        oilTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
