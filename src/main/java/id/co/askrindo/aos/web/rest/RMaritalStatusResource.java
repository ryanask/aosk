package id.co.askrindo.aos.web.rest;

import com.codahale.metrics.annotation.Timed;
import id.co.askrindo.aos.domain.RMaritalStatus;
import id.co.askrindo.aos.repository.RMaritalStatusRepository;
import id.co.askrindo.aos.repository.search.RMaritalStatusSearchRepository;
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
 * REST controller for managing RMaritalStatus.
 */
@RestController
@RequestMapping("/api")
public class RMaritalStatusResource {

    private final Logger log = LoggerFactory.getLogger(RMaritalStatusResource.class);
        
    @Inject
    private RMaritalStatusRepository rMaritalStatusRepository;
    
    @Inject
    private RMaritalStatusSearchRepository rMaritalStatusSearchRepository;
    
    /**
     * POST  /rMaritalStatuss -> Create a new rMaritalStatus.
     */
    @RequestMapping(value = "/rMaritalStatuss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RMaritalStatus> createRMaritalStatus(@Valid @RequestBody RMaritalStatus rMaritalStatus) throws URISyntaxException {
        log.debug("REST request to save RMaritalStatus : {}", rMaritalStatus);
        if (rMaritalStatus.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rMaritalStatus", "idexists", "A new rMaritalStatus cannot already have an ID")).body(null);
        }
        RMaritalStatus result = rMaritalStatusRepository.save(rMaritalStatus);
        rMaritalStatusSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/rMaritalStatuss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rMaritalStatus", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rMaritalStatuss -> Updates an existing rMaritalStatus.
     */
    @RequestMapping(value = "/rMaritalStatuss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RMaritalStatus> updateRMaritalStatus(@Valid @RequestBody RMaritalStatus rMaritalStatus) throws URISyntaxException {
        log.debug("REST request to update RMaritalStatus : {}", rMaritalStatus);
        if (rMaritalStatus.getId() == null) {
            return createRMaritalStatus(rMaritalStatus);
        }
        RMaritalStatus result = rMaritalStatusRepository.save(rMaritalStatus);
        rMaritalStatusSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rMaritalStatus", rMaritalStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rMaritalStatuss -> get all the rMaritalStatuss.
     */
    @RequestMapping(value = "/rMaritalStatuss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RMaritalStatus>> getAllRMaritalStatuss(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of RMaritalStatuss");
        Page<RMaritalStatus> page = rMaritalStatusRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rMaritalStatuss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rMaritalStatuss/:id -> get the "id" rMaritalStatus.
     */
    @RequestMapping(value = "/rMaritalStatuss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RMaritalStatus> getRMaritalStatus(@PathVariable Long id) {
        log.debug("REST request to get RMaritalStatus : {}", id);
        RMaritalStatus rMaritalStatus = rMaritalStatusRepository.findOne(id);
        return Optional.ofNullable(rMaritalStatus)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rMaritalStatuss/:id -> delete the "id" rMaritalStatus.
     */
    @RequestMapping(value = "/rMaritalStatuss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRMaritalStatus(@PathVariable Long id) {
        log.debug("REST request to delete RMaritalStatus : {}", id);
        rMaritalStatusRepository.delete(id);
        rMaritalStatusSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rMaritalStatus", id.toString())).build();
    }

    /**
     * SEARCH  /_search/rMaritalStatuss/:query -> search for the rMaritalStatus corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/rMaritalStatuss/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RMaritalStatus> searchRMaritalStatuss(@PathVariable String query) {
        log.debug("REST request to search RMaritalStatuss for query {}", query);
        return StreamSupport
            .stream(rMaritalStatusSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
