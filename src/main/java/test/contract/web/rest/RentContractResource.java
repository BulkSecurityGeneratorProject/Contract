package test.contract.web.rest;

import test.contract.domain.RentContract;
import test.contract.repository.RentContractRepository;
import test.contract.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link test.contract.domain.RentContract}.
 */
@RestController
@RequestMapping("/api")
public class RentContractResource {

    private final Logger log = LoggerFactory.getLogger(RentContractResource.class);

    private static final String ENTITY_NAME = "rentContract";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RentContractRepository rentContractRepository;

    public RentContractResource(RentContractRepository rentContractRepository) {
        this.rentContractRepository = rentContractRepository;
    }

    /**
     * {@code POST  /rent-contracts} : Create a new rentContract.
     *
     * @param rentContract the rentContract to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rentContract, or with status {@code 400 (Bad Request)} if the rentContract has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rent-contracts")
    public ResponseEntity<RentContract> createRentContract(@Valid @RequestBody RentContract rentContract) throws URISyntaxException {
        log.debug("REST request to save RentContract : {}", rentContract);
        if (rentContract.getId() != null) {
            throw new BadRequestAlertException("A new rentContract cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RentContract result = rentContractRepository.save(rentContract);
        return ResponseEntity.created(new URI("/api/rent-contracts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rent-contracts} : Updates an existing rentContract.
     *
     * @param rentContract the rentContract to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rentContract,
     * or with status {@code 400 (Bad Request)} if the rentContract is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rentContract couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rent-contracts")
    public ResponseEntity<RentContract> updateRentContract(@Valid @RequestBody RentContract rentContract) throws URISyntaxException {
        log.debug("REST request to update RentContract : {}", rentContract);
        if (rentContract.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RentContract result = rentContractRepository.save(rentContract);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rentContract.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rent-contracts} : get all the rentContracts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rentContracts in body.
     */
    @GetMapping("/rent-contracts")
    public ResponseEntity<List<RentContract>> getAllRentContracts(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of RentContracts");
        Page<RentContract> page = rentContractRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rent-contracts/:id} : get the "id" rentContract.
     *
     * @param id the id of the rentContract to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rentContract, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rent-contracts/{id}")
    public ResponseEntity<RentContract> getRentContract(@PathVariable Long id) {
        log.debug("REST request to get RentContract : {}", id);
        Optional<RentContract> rentContract = rentContractRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rentContract);
    }

    /**
     * {@code DELETE  /rent-contracts/:id} : delete the "id" rentContract.
     *
     * @param id the id of the rentContract to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rent-contracts/{id}")
    public ResponseEntity<Void> deleteRentContract(@PathVariable Long id) {
        log.debug("REST request to delete RentContract : {}", id);
        rentContractRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
