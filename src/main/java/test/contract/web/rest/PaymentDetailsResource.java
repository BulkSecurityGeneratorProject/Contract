package test.contract.web.rest;

import test.contract.domain.PaymentDetails;
import test.contract.repository.PaymentDetailsRepository;
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
 * REST controller for managing {@link test.contract.domain.PaymentDetails}.
 */
@RestController
@RequestMapping("/api")
public class PaymentDetailsResource {

    private final Logger log = LoggerFactory.getLogger(PaymentDetailsResource.class);

    private static final String ENTITY_NAME = "paymentDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentDetailsRepository paymentDetailsRepository;

    public PaymentDetailsResource(PaymentDetailsRepository paymentDetailsRepository) {
        this.paymentDetailsRepository = paymentDetailsRepository;
    }

    /**
     * {@code POST  /payment-details} : Create a new paymentDetails.
     *
     * @param paymentDetails the paymentDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentDetails, or with status {@code 400 (Bad Request)} if the paymentDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-details")
    public ResponseEntity<PaymentDetails> createPaymentDetails(@Valid @RequestBody PaymentDetails paymentDetails) throws URISyntaxException {
        log.debug("REST request to save PaymentDetails : {}", paymentDetails);
        if (paymentDetails.getId() != null) {
            throw new BadRequestAlertException("A new paymentDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentDetails result = paymentDetailsRepository.save(paymentDetails);
        return ResponseEntity.created(new URI("/api/payment-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-details} : Updates an existing paymentDetails.
     *
     * @param paymentDetails the paymentDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentDetails,
     * or with status {@code 400 (Bad Request)} if the paymentDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-details")
    public ResponseEntity<PaymentDetails> updatePaymentDetails(@Valid @RequestBody PaymentDetails paymentDetails) throws URISyntaxException {
        log.debug("REST request to update PaymentDetails : {}", paymentDetails);
        if (paymentDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaymentDetails result = paymentDetailsRepository.save(paymentDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paymentDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /payment-details} : get all the paymentDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentDetails in body.
     */
    @GetMapping("/payment-details")
    public List<PaymentDetails> getAllPaymentDetails() {
        log.debug("REST request to get all PaymentDetails");
        return paymentDetailsRepository.findAll();
    }

    /**
     * {@code GET  /payment-details/:id} : get the "id" paymentDetails.
     *
     * @param id the id of the paymentDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-details/{id}")
    public ResponseEntity<PaymentDetails> getPaymentDetails(@PathVariable Long id) {
        log.debug("REST request to get PaymentDetails : {}", id);
        Optional<PaymentDetails> paymentDetails = paymentDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(paymentDetails);
    }

    /**
     * {@code DELETE  /payment-details/:id} : delete the "id" paymentDetails.
     *
     * @param id the id of the paymentDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-details/{id}")
    public ResponseEntity<Void> deletePaymentDetails(@PathVariable Long id) {
        log.debug("REST request to delete PaymentDetails : {}", id);
        paymentDetailsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
