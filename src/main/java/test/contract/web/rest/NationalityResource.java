package test.contract.web.rest;

import test.contract.domain.Nationality;
import test.contract.repository.NationalityRepository;
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
 * REST controller for managing {@link test.contract.domain.Nationality}.
 */
@RestController
@RequestMapping("/api")
public class NationalityResource {

    private final Logger log = LoggerFactory.getLogger(NationalityResource.class);

    private static final String ENTITY_NAME = "nationality";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NationalityRepository nationalityRepository;

    public NationalityResource(NationalityRepository nationalityRepository) {
        this.nationalityRepository = nationalityRepository;
    }

    /**
     * {@code POST  /nationalities} : Create a new nationality.
     *
     * @param nationality the nationality to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nationality, or with status {@code 400 (Bad Request)} if the nationality has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nationalities")
    public ResponseEntity<Nationality> createNationality(@RequestBody Nationality nationality) throws URISyntaxException {
        log.debug("REST request to save Nationality : {}", nationality);
        if (nationality.getId() != null) {
            throw new BadRequestAlertException("A new nationality cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Nationality result = nationalityRepository.save(nationality);
        return ResponseEntity.created(new URI("/api/nationalities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nationalities} : Updates an existing nationality.
     *
     * @param nationality the nationality to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nationality,
     * or with status {@code 400 (Bad Request)} if the nationality is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nationality couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nationalities")
    public ResponseEntity<Nationality> updateNationality(@RequestBody Nationality nationality) throws URISyntaxException {
        log.debug("REST request to update Nationality : {}", nationality);
        if (nationality.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Nationality result = nationalityRepository.save(nationality);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nationality.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nationalities} : get all the nationalities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nationalities in body.
     */
    @GetMapping("/nationalities")
    public List<Nationality> getAllNationalities() {
        log.debug("REST request to get all Nationalities");
        return nationalityRepository.findAll();
    }

    /**
     * {@code GET  /nationalities/:id} : get the "id" nationality.
     *
     * @param id the id of the nationality to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nationality, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nationalities/{id}")
    public ResponseEntity<Nationality> getNationality(@PathVariable Long id) {
        log.debug("REST request to get Nationality : {}", id);
        Optional<Nationality> nationality = nationalityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nationality);
    }

    /**
     * {@code DELETE  /nationalities/:id} : delete the "id" nationality.
     *
     * @param id the id of the nationality to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nationalities/{id}")
    public ResponseEntity<Void> deleteNationality(@PathVariable Long id) {
        log.debug("REST request to delete Nationality : {}", id);
        nationalityRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
