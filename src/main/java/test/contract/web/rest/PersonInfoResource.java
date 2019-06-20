package test.contract.web.rest;

import test.contract.domain.PersonInfo;
import test.contract.repository.PersonInfoRepository;
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
 * REST controller for managing {@link test.contract.domain.PersonInfo}.
 */
@RestController
@RequestMapping("/api")
public class PersonInfoResource {

    private final Logger log = LoggerFactory.getLogger(PersonInfoResource.class);

    private static final String ENTITY_NAME = "personInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonInfoRepository personInfoRepository;

    public PersonInfoResource(PersonInfoRepository personInfoRepository) {
        this.personInfoRepository = personInfoRepository;
    }

    /**
     * {@code POST  /person-infos} : Create a new personInfo.
     *
     * @param personInfo the personInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personInfo, or with status {@code 400 (Bad Request)} if the personInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/person-infos")
    public ResponseEntity<PersonInfo> createPersonInfo(@Valid @RequestBody PersonInfo personInfo) throws URISyntaxException {
        log.debug("REST request to save PersonInfo : {}", personInfo);
        if (personInfo.getId() != null) {
            throw new BadRequestAlertException("A new personInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonInfo result = personInfoRepository.save(personInfo);
        return ResponseEntity.created(new URI("/api/person-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /person-infos} : Updates an existing personInfo.
     *
     * @param personInfo the personInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personInfo,
     * or with status {@code 400 (Bad Request)} if the personInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/person-infos")
    public ResponseEntity<PersonInfo> updatePersonInfo(@Valid @RequestBody PersonInfo personInfo) throws URISyntaxException {
        log.debug("REST request to update PersonInfo : {}", personInfo);
        if (personInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PersonInfo result = personInfoRepository.save(personInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, personInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /person-infos} : get all the personInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personInfos in body.
     */
    @GetMapping("/person-infos")
    public List<PersonInfo> getAllPersonInfos() {
        log.debug("REST request to get all PersonInfos");
        return personInfoRepository.findAll();
    }

    /**
     * {@code GET  /person-infos/:id} : get the "id" personInfo.
     *
     * @param id the id of the personInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/person-infos/{id}")
    public ResponseEntity<PersonInfo> getPersonInfo(@PathVariable Long id) {
        log.debug("REST request to get PersonInfo : {}", id);
        Optional<PersonInfo> personInfo = personInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(personInfo);
    }

    /**
     * {@code DELETE  /person-infos/:id} : delete the "id" personInfo.
     *
     * @param id the id of the personInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/person-infos/{id}")
    public ResponseEntity<Void> deletePersonInfo(@PathVariable Long id) {
        log.debug("REST request to delete PersonInfo : {}", id);
        personInfoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
