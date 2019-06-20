package test.contract.web.rest;

import test.contract.domain.IdType;
import test.contract.repository.IdTypeRepository;
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
 * REST controller for managing {@link test.contract.domain.IdType}.
 */
@RestController
@RequestMapping("/api")
public class IdTypeResource {

    private final Logger log = LoggerFactory.getLogger(IdTypeResource.class);

    private static final String ENTITY_NAME = "idType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IdTypeRepository idTypeRepository;

    public IdTypeResource(IdTypeRepository idTypeRepository) {
        this.idTypeRepository = idTypeRepository;
    }

    /**
     * {@code POST  /id-types} : Create a new idType.
     *
     * @param idType the idType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new idType, or with status {@code 400 (Bad Request)} if the idType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/id-types")
    public ResponseEntity<IdType> createIdType(@RequestBody IdType idType) throws URISyntaxException {
        log.debug("REST request to save IdType : {}", idType);
        if (idType.getId() != null) {
            throw new BadRequestAlertException("A new idType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IdType result = idTypeRepository.save(idType);
        return ResponseEntity.created(new URI("/api/id-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /id-types} : Updates an existing idType.
     *
     * @param idType the idType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated idType,
     * or with status {@code 400 (Bad Request)} if the idType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the idType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/id-types")
    public ResponseEntity<IdType> updateIdType(@RequestBody IdType idType) throws URISyntaxException {
        log.debug("REST request to update IdType : {}", idType);
        if (idType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IdType result = idTypeRepository.save(idType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, idType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /id-types} : get all the idTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of idTypes in body.
     */
    @GetMapping("/id-types")
    public List<IdType> getAllIdTypes() {
        log.debug("REST request to get all IdTypes");
        return idTypeRepository.findAll();
    }

    /**
     * {@code GET  /id-types/:id} : get the "id" idType.
     *
     * @param id the id of the idType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the idType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/id-types/{id}")
    public ResponseEntity<IdType> getIdType(@PathVariable Long id) {
        log.debug("REST request to get IdType : {}", id);
        Optional<IdType> idType = idTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(idType);
    }

    /**
     * {@code DELETE  /id-types/:id} : delete the "id" idType.
     *
     * @param id the id of the idType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/id-types/{id}")
    public ResponseEntity<Void> deleteIdType(@PathVariable Long id) {
        log.debug("REST request to delete IdType : {}", id);
        idTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
