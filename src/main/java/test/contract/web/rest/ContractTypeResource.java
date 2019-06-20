package test.contract.web.rest;

import test.contract.domain.ContractType;
import test.contract.repository.ContractTypeRepository;
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
 * REST controller for managing {@link test.contract.domain.ContractType}.
 */
@RestController
@RequestMapping("/api")
public class ContractTypeResource {

    private final Logger log = LoggerFactory.getLogger(ContractTypeResource.class);

    private static final String ENTITY_NAME = "contractType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContractTypeRepository contractTypeRepository;

    public ContractTypeResource(ContractTypeRepository contractTypeRepository) {
        this.contractTypeRepository = contractTypeRepository;
    }

    /**
     * {@code POST  /contract-types} : Create a new contractType.
     *
     * @param contractType the contractType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contractType, or with status {@code 400 (Bad Request)} if the contractType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contract-types")
    public ResponseEntity<ContractType> createContractType(@RequestBody ContractType contractType) throws URISyntaxException {
        log.debug("REST request to save ContractType : {}", contractType);
        if (contractType.getId() != null) {
            throw new BadRequestAlertException("A new contractType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContractType result = contractTypeRepository.save(contractType);
        return ResponseEntity.created(new URI("/api/contract-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contract-types} : Updates an existing contractType.
     *
     * @param contractType the contractType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contractType,
     * or with status {@code 400 (Bad Request)} if the contractType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contractType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contract-types")
    public ResponseEntity<ContractType> updateContractType(@RequestBody ContractType contractType) throws URISyntaxException {
        log.debug("REST request to update ContractType : {}", contractType);
        if (contractType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContractType result = contractTypeRepository.save(contractType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, contractType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contract-types} : get all the contractTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contractTypes in body.
     */
    @GetMapping("/contract-types")
    public List<ContractType> getAllContractTypes() {
        log.debug("REST request to get all ContractTypes");
        return contractTypeRepository.findAll();
    }

    /**
     * {@code GET  /contract-types/:id} : get the "id" contractType.
     *
     * @param id the id of the contractType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contractType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contract-types/{id}")
    public ResponseEntity<ContractType> getContractType(@PathVariable Long id) {
        log.debug("REST request to get ContractType : {}", id);
        Optional<ContractType> contractType = contractTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(contractType);
    }

    /**
     * {@code DELETE  /contract-types/:id} : delete the "id" contractType.
     *
     * @param id the id of the contractType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contract-types/{id}")
    public ResponseEntity<Void> deleteContractType(@PathVariable Long id) {
        log.debug("REST request to delete ContractType : {}", id);
        contractTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
