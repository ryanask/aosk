package id.co.askrindo.aos.web.rest;

import com.codahale.metrics.annotation.Timed;
import id.co.askrindo.aos.domain.RStatusRekening;
import id.co.askrindo.aos.repository.RStatusRekeningRepository;
import id.co.askrindo.aos.repository.search.RStatusRekeningSearchRepository;
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
 * REST controller for managing RStatusRekening.
 */
@RestController
@RequestMapping("/api")
public class RStatusRekeningResource {

    private final Logger log = LoggerFactory.getLogger(RStatusRekeningResource.class);
        
    @Inject
    private RStatusRekeningRepository rStatusRekeningRepository;
    
    @Inject
    private RStatusRekeningSearchRepository rStatusRekeningSearchRepository;
    
    /**
     * POST  /rStatusRekenings -> Create a new rStatusRekening.
     */
    @RequestMapping(value = "/rStatusRekenings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RStatusRekening> createRStatusRekening(@Valid @RequestBody RStatusRekening rStatusRekening) throws URISyntaxException {
        log.debug("REST request to save RStatusRekening : {}", rStatusRekening);
        if (rStatusRekening.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rStatusRekening", "idexists", "A new rStatusRekening cannot already have an ID")).body(null);
        }
        RStatusRekening result = rStatusRekeningRepository.save(rStatusRekening);
        rStatusRekeningSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/rStatusRekenings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rStatusRekening", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rStatusRekenings -> Updates an existing rStatusRekening.
     */
    @RequestMapping(value = "/rStatusRekenings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RStatusRekening> updateRStatusRekening(@Valid @RequestBody RStatusRekening rStatusRekening) throws URISyntaxException {
        log.debug("REST request to update RStatusRekening : {}", rStatusRekening);
        if (rStatusRekening.getId() == null) {
            return createRStatusRekening(rStatusRekening);
        }
        RStatusRekening result = rStatusRekeningRepository.save(rStatusRekening);
        rStatusRekeningSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rStatusRekening", rStatusRekening.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rStatusRekenings -> get all the rStatusRekenings.
     */
    @RequestMapping(value = "/rStatusRekenings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RStatusRekening>> getAllRStatusRekenings(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of RStatusRekenings");
        Page<RStatusRekening> page = rStatusRekeningRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rStatusRekenings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rStatusRekenings/:id -> get the "id" rStatusRekening.
     */
    @RequestMapping(value = "/rStatusRekenings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RStatusRekening> getRStatusRekening(@PathVariable Long id) {
        log.debug("REST request to get RStatusRekening : {}", id);
        RStatusRekening rStatusRekening = rStatusRekeningRepository.findOne(id);
        return Optional.ofNullable(rStatusRekening)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rStatusRekenings/:id -> delete the "id" rStatusRekening.
     */
    @RequestMapping(value = "/rStatusRekenings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRStatusRekening(@PathVariable Long id) {
        log.debug("REST request to delete RStatusRekening : {}", id);
        rStatusRekeningRepository.delete(id);
        rStatusRekeningSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rStatusRekening", id.toString())).build();
    }

    /**
     * SEARCH  /_search/rStatusRekenings/:query -> search for the rStatusRekening corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/rStatusRekenings/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RStatusRekening> searchRStatusRekenings(@PathVariable String query) {
        log.debug("REST request to search RStatusRekenings for query {}", query);
        return StreamSupport
            .stream(rStatusRekeningSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
