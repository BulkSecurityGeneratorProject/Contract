package test.contract.web.rest;

import test.contract.domain.RentalAccount;
import test.contract.repository.RentalAccountRepository;
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
 * REST controller for managing {@link test.contract.domain.RentalAccount}.
 */
@RestController
@RequestMapping("/api")
public class RentalAccountResource {

    private final Logger log = LoggerFactory.getLogger(RentalAccountResource.class);

    private static final String ENTITY_NAME = "rentalAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RentalAccountRepository rentalAccountRepository;

    public RentalAccountResource(RentalAccountRepository rentalAccountRepository) {
        this.rentalAccountRepository = rentalAccountRepository;
    }

    /**
     * {@code POST  /rental-accounts} : Create a new rentalAccount.
     *
     * @param rentalAccount the rentalAccount to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rentalAccount, or with status {@code 400 (Bad Request)} if the rentalAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rental-accounts")
    public ResponseEntity<RentalAccount> createRentalAccount(@RequestBody RentalAccount rentalAccount) throws URISyntaxException {
        log.debug("REST request to save RentalAccount : {}", rentalAccount);
        if (rentalAccount.getId() != null) {
            throw new BadRequestAlertException("A new rentalAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RentalAccount result = rentalAccountRepository.save(rentalAccount);
        return ResponseEntity.created(new URI("/api/rental-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rental-accounts} : Updates an existing rentalAccount.
     *
     * @param rentalAccount the rentalAccount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rentalAccount,
     * or with status {@code 400 (Bad Request)} if the rentalAccount is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rentalAccount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rental-accounts")
    public ResponseEntity<RentalAccount> updateRentalAccount(@RequestBody RentalAccount rentalAccount) throws URISyntaxException {
        log.debug("REST request to update RentalAccount : {}", rentalAccount);
        if (rentalAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RentalAccount result = rentalAccountRepository.save(rentalAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rentalAccount.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rental-accounts} : get all the rentalAccounts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rentalAccounts in body.
     */
    @GetMapping("/rental-accounts")
    public List<RentalAccount> getAllRentalAccounts() {
        log.debug("REST request to get all RentalAccounts");
        return rentalAccountRepository.findAll();
    }

    /**
     * {@code GET  /rental-accounts/:id} : get the "id" rentalAccount.
     *
     * @param id the id of the rentalAccount to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rentalAccount, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rental-accounts/{id}")
    public ResponseEntity<RentalAccount> getRentalAccount(@PathVariable Long id) {
        log.debug("REST request to get RentalAccount : {}", id);
        Optional<RentalAccount> rentalAccount = rentalAccountRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rentalAccount);
    }

    /**
     * {@code DELETE  /rental-accounts/:id} : delete the "id" rentalAccount.
     *
     * @param id the id of the rentalAccount to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rental-accounts/{id}")
    public ResponseEntity<Void> deleteRentalAccount(@PathVariable Long id) {
        log.debug("REST request to delete RentalAccount : {}", id);
        rentalAccountRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
