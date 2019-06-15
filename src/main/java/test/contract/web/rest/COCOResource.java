package test.contract.web.rest;

import test.contract.domain.COCO;
import test.contract.repository.COCORepository;
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
 * REST controller for managing {@link test.contract.domain.COCO}.
 */
@RestController
@RequestMapping("/api")
public class COCOResource {

    private final Logger log = LoggerFactory.getLogger(COCOResource.class);

    private static final String ENTITY_NAME = "cOCO";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final COCORepository cOCORepository;

    public COCOResource(COCORepository cOCORepository) {
        this.cOCORepository = cOCORepository;
    }

    /**
     * {@code POST  /cocos} : Create a new cOCO.
     *
     * @param cOCO the cOCO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cOCO, or with status {@code 400 (Bad Request)} if the cOCO has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cocos")
    public ResponseEntity<COCO> createCOCO(@RequestBody COCO cOCO) throws URISyntaxException {
        log.debug("REST request to save COCO : {}", cOCO);
        if (cOCO.getId() != null) {
            throw new BadRequestAlertException("A new cOCO cannot already have an ID", ENTITY_NAME, "idexists");
        }
        COCO result = cOCORepository.save(cOCO);
        return ResponseEntity.created(new URI("/api/cocos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cocos} : Updates an existing cOCO.
     *
     * @param cOCO the cOCO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cOCO,
     * or with status {@code 400 (Bad Request)} if the cOCO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cOCO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cocos")
    public ResponseEntity<COCO> updateCOCO(@RequestBody COCO cOCO) throws URISyntaxException {
        log.debug("REST request to update COCO : {}", cOCO);
        if (cOCO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        COCO result = cOCORepository.save(cOCO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cOCO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cocos} : get all the cOCOS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cOCOS in body.
     */
    @GetMapping("/cocos")
    public List<COCO> getAllCOCOS() {
        log.debug("REST request to get all COCOS");
        return cOCORepository.findAll();
    }

    /**
     * {@code GET  /cocos/:id} : get the "id" cOCO.
     *
     * @param id the id of the cOCO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cOCO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cocos/{id}")
    public ResponseEntity<COCO> getCOCO(@PathVariable Long id) {
        log.debug("REST request to get COCO : {}", id);
        Optional<COCO> cOCO = cOCORepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cOCO);
    }

    /**
     * {@code DELETE  /cocos/:id} : delete the "id" cOCO.
     *
     * @param id the id of the cOCO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cocos/{id}")
    public ResponseEntity<Void> deleteCOCO(@PathVariable Long id) {
        log.debug("REST request to delete COCO : {}", id);
        cOCORepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
