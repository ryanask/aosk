package id.co.askrindo.aos.web.rest;

import com.codahale.metrics.annotation.Timed;
import id.co.askrindo.aos.domain.BankPelaksanaOnline;
import id.co.askrindo.aos.repository.BankPelaksanaOnlineRepository;
import id.co.askrindo.aos.repository.search.BankPelaksanaOnlineSearchRepository;
import id.co.askrindo.aos.web.rest.util.HeaderUtil;
import id.co.askrindo.aos.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing BankPelaksanaOnline.
 */
@RestController
@RequestMapping("/api")
public class BankPelaksanaOnlineResource {

    private final Logger log = LoggerFactory.getLogger(BankPelaksanaOnlineResource.class);
        
    @Inject
    private BankPelaksanaOnlineRepository bankPelaksanaOnlineRepository;
    
    @Inject
    private BankPelaksanaOnlineSearchRepository bankPelaksanaOnlineSearchRepository;
    
    /**
     * POST  /bankPelaksanaOnlines -> Create a new bankPelaksanaOnline.
     */
    @RequestMapping(value = "/bankPelaksanaOnlines",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BankPelaksanaOnline> createBankPelaksanaOnline(@Valid @RequestBody BankPelaksanaOnline bankPelaksanaOnline) throws URISyntaxException {
        log.debug("REST request to save BankPelaksanaOnline : {}", bankPelaksanaOnline);
        if (bankPelaksanaOnline.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("bankPelaksanaOnline", "idexists", "A new bankPelaksanaOnline cannot already have an ID")).body(null);
        }
        BankPelaksanaOnline result = bankPelaksanaOnlineRepository.save(bankPelaksanaOnline);
        bankPelaksanaOnlineSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/bankPelaksanaOnlines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("bankPelaksanaOnline", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bankPelaksanaOnlines -> Updates an existing bankPelaksanaOnline.
     */
    @RequestMapping(value = "/bankPelaksanaOnlines",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BankPelaksanaOnline> updateBankPelaksanaOnline(@Valid @RequestBody BankPelaksanaOnline bankPelaksanaOnline) throws URISyntaxException {
        log.debug("REST request to update BankPelaksanaOnline : {}", bankPelaksanaOnline);
        if (bankPelaksanaOnline.getId() == null) {
            return createBankPelaksanaOnline(bankPelaksanaOnline);
        }
        BankPelaksanaOnline result = bankPelaksanaOnlineRepository.save(bankPelaksanaOnline);
        bankPelaksanaOnlineSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("bankPelaksanaOnline", bankPelaksanaOnline.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bankPelaksanaOnlines -> get all the bankPelaksanaOnlines.
     */
    @RequestMapping(value = "/bankPelaksanaOnlines",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<BankPelaksanaOnline>> getAllBankPelaksanaOnlines(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of BankPelaksanaOnlines");
        Page<BankPelaksanaOnline> page = bankPelaksanaOnlineRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bankPelaksanaOnlines");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bankPelaksanaOnlines/:id -> get the "id" bankPelaksanaOnline.
     */
    @RequestMapping(value = "/bankPelaksanaOnlines/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BankPelaksanaOnline> getBankPelaksanaOnline(@PathVariable Long id) {
        log.debug("REST request to get BankPelaksanaOnline : {}", id);
        BankPelaksanaOnline bankPelaksanaOnline = bankPelaksanaOnlineRepository.findOne(id);
        return Optional.ofNullable(bankPelaksanaOnline)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /bankPelaksanaOnlines/:id -> delete the "id" bankPelaksanaOnline.
     */
    @RequestMapping(value = "/bankPelaksanaOnlines/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBankPelaksanaOnline(@PathVariable Long id) {
        log.debug("REST request to delete BankPelaksanaOnline : {}", id);
        bankPelaksanaOnlineRepository.delete(id);
        bankPelaksanaOnlineSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("bankPelaksanaOnline", id.toString())).build();
    }

    /**
     * SEARCH  /_search/bankPelaksanaOnlines/:query -> search for the bankPelaksanaOnline corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/bankPelaksanaOnlines/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BankPelaksanaOnline> searchBankPelaksanaOnlines(@PathVariable String query) {
        log.debug("REST request to search BankPelaksanaOnlines for query {}", query);
        return StreamSupport
            .stream(bankPelaksanaOnlineSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
