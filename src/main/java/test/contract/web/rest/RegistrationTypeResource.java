package test.contract.web.rest;

import test.contract.domain.RegistrationType;
import test.contract.repository.RegistrationTypeRepository;
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
 * REST controller for managing {@link test.contract.domain.RegistrationType}.
 */
@RestController
@RequestMapping("/api")
public class RegistrationTypeResource {

    private final Logger log = LoggerFactory.getLogger(RegistrationTypeResource.class);

    private static final String ENTITY_NAME = "registrationType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegistrationTypeRepository registrationTypeRepository;

    public RegistrationTypeResource(RegistrationTypeRepository registrationTypeRepository) {
        this.registrationTypeRepository = registrationTypeRepository;
    }

    /**
     * {@code POST  /registration-types} : Create a new registrationType.
     *
     * @param registrationType the registrationType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new registrationType, or with status {@code 400 (Bad Request)} if the registrationType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/registration-types")
    public ResponseEntity<RegistrationType> createRegistrationType(@RequestBody RegistrationType registrationType) throws URISyntaxException {
        log.debug("REST request to save RegistrationType : {}", registrationType);
        if (registrationType.getId() != null) {
            throw new BadRequestAlertException("A new registrationType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegistrationType result = registrationTypeRepository.save(registrationType);
        return ResponseEntity.created(new URI("/api/registration-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /registration-types} : Updates an existing registrationType.
     *
     * @param registrationType the registrationType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated registrationType,
     * or with status {@code 400 (Bad Request)} if the registrationType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the registrationType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/registration-types")
    public ResponseEntity<RegistrationType> updateRegistrationType(@RequestBody RegistrationType registrationType) throws URISyntaxException {
        log.debug("REST request to update RegistrationType : {}", registrationType);
        if (registrationType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RegistrationType result = registrationTypeRepository.save(registrationType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, registrationType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /registration-types} : get all the registrationTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of registrationTypes in body.
     */
    @GetMapping("/registration-types")
    public List<RegistrationType> getAllRegistrationTypes() {
        log.debug("REST request to get all RegistrationTypes");
        return registrationTypeRepository.findAll();
    }

    /**
     * {@code GET  /registration-types/:id} : get the "id" registrationType.
     *
     * @param id the id of the registrationType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the registrationType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/registration-types/{id}")
    public ResponseEntity<RegistrationType> getRegistrationType(@PathVariable Long id) {
        log.debug("REST request to get RegistrationType : {}", id);
        Optional<RegistrationType> registrationType = registrationTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(registrationType);
    }

    /**
     * {@code DELETE  /registration-types/:id} : delete the "id" registrationType.
     *
     * @param id the id of the registrationType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/registration-types/{id}")
    public ResponseEntity<Void> deleteRegistrationType(@PathVariable Long id) {
        log.debug("REST request to delete RegistrationType : {}", id);
        registrationTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
