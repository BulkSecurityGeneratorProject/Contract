package test.contract.web.rest;

import test.contract.domain.ContractStatus;
import test.contract.repository.ContractStatusRepository;
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
 * REST controller for managing {@link test.contract.domain.ContractStatus}.
 */
@RestController
@RequestMapping("/api")
public class ContractStatusResource {

    private final Logger log = LoggerFactory.getLogger(ContractStatusResource.class);

    private static final String ENTITY_NAME = "contractStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContractStatusRepository contractStatusRepository;

    public ContractStatusResource(ContractStatusRepository contractStatusRepository) {
        this.contractStatusRepository = contractStatusRepository;
    }

    /**
     * {@code POST  /contract-statuses} : Create a new contractStatus.
     *
     * @param contractStatus the contractStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contractStatus, or with status {@code 400 (Bad Request)} if the contractStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contract-statuses")
    public ResponseEntity<ContractStatus> createContractStatus(@RequestBody ContractStatus contractStatus) throws URISyntaxException {
        log.debug("REST request to save ContractStatus : {}", contractStatus);
        if (contractStatus.getId() != null) {
            throw new BadRequestAlertException("A new contractStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContractStatus result = contractStatusRepository.save(contractStatus);
        return ResponseEntity.created(new URI("/api/contract-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contract-statuses} : Updates an existing contractStatus.
     *
     * @param contractStatus the contractStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contractStatus,
     * or with status {@code 400 (Bad Request)} if the contractStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contractStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contract-statuses")
    public ResponseEntity<ContractStatus> updateContractStatus(@RequestBody ContractStatus contractStatus) throws URISyntaxException {
        log.debug("REST request to update ContractStatus : {}", contractStatus);
        if (contractStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContractStatus result = contractStatusRepository.save(contractStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, contractStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contract-statuses} : get all the contractStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contractStatuses in body.
     */
    @GetMapping("/contract-statuses")
    public List<ContractStatus> getAllContractStatuses() {
        log.debug("REST request to get all ContractStatuses");
        return contractStatusRepository.findAll();
    }

    /**
     * {@code GET  /contract-statuses/:id} : get the "id" contractStatus.
     *
     * @param id the id of the contractStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contractStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contract-statuses/{id}")
    public ResponseEntity<ContractStatus> getContractStatus(@PathVariable Long id) {
        log.debug("REST request to get ContractStatus : {}", id);
        Optional<ContractStatus> contractStatus = contractStatusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(contractStatus);
    }

    /**
     * {@code DELETE  /contract-statuses/:id} : delete the "id" contractStatus.
     *
     * @param id the id of the contractStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contract-statuses/{id}")
    public ResponseEntity<Void> deleteContractStatus(@PathVariable Long id) {
        log.debug("REST request to delete ContractStatus : {}", id);
        contractStatusRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
