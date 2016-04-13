package id.co.askrindo.aos.web.rest;

import com.codahale.metrics.annotation.Timed;
import id.co.askrindo.aos.domain.RStatusAkad;
import id.co.askrindo.aos.repository.RStatusAkadRepository;
import id.co.askrindo.aos.repository.search.RStatusAkadSearchRepository;
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
 * REST controller for managing RStatusAkad.
 */
@RestController
@RequestMapping("/api")
public class RStatusAkadResource {

    private final Logger log = LoggerFactory.getLogger(RStatusAkadResource.class);
        
    @Inject
    private RStatusAkadRepository rStatusAkadRepository;
    
    @Inject
    private RStatusAkadSearchRepository rStatusAkadSearchRepository;
    
    /**
     * POST  /rStatusAkads -> Create a new rStatusAkad.
     */
    @RequestMapping(value = "/rStatusAkads",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RStatusAkad> createRStatusAkad(@Valid @RequestBody RStatusAkad rStatusAkad) throws URISyntaxException {
        log.debug("REST request to save RStatusAkad : {}", rStatusAkad);
        if (rStatusAkad.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rStatusAkad", "idexists", "A new rStatusAkad cannot already have an ID")).body(null);
        }
        RStatusAkad result = rStatusAkadRepository.save(rStatusAkad);
        rStatusAkadSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/rStatusAkads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rStatusAkad", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rStatusAkads -> Updates an existing rStatusAkad.
     */
    @RequestMapping(value = "/rStatusAkads",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RStatusAkad> updateRStatusAkad(@Valid @RequestBody RStatusAkad rStatusAkad) throws URISyntaxException {
        log.debug("REST request to update RStatusAkad : {}", rStatusAkad);
        if (rStatusAkad.getId() == null) {
            return createRStatusAkad(rStatusAkad);
        }
        RStatusAkad result = rStatusAkadRepository.save(rStatusAkad);
        rStatusAkadSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rStatusAkad", rStatusAkad.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rStatusAkads -> get all the rStatusAkads.
     */
    @RequestMapping(value = "/rStatusAkads",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RStatusAkad>> getAllRStatusAkads(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of RStatusAkads");
        Page<RStatusAkad> page = rStatusAkadRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rStatusAkads");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rStatusAkads/:id -> get the "id" rStatusAkad.
     */
    @RequestMapping(value = "/rStatusAkads/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RStatusAkad> getRStatusAkad(@PathVariable Long id) {
        log.debug("REST request to get RStatusAkad : {}", id);
        RStatusAkad rStatusAkad = rStatusAkadRepository.findOne(id);
        return Optional.ofNullable(rStatusAkad)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rStatusAkads/:id -> delete the "id" rStatusAkad.
     */
    @RequestMapping(value = "/rStatusAkads/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRStatusAkad(@PathVariable Long id) {
        log.debug("REST request to delete RStatusAkad : {}", id);
        rStatusAkadRepository.delete(id);
        rStatusAkadSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rStatusAkad", id.toString())).build();
    }

    /**
     * SEARCH  /_search/rStatusAkads/:query -> search for the rStatusAkad corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/rStatusAkads/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RStatusAkad> searchRStatusAkads(@PathVariable String query) {
        log.debug("REST request to search RStatusAkads for query {}", query);
        return StreamSupport
            .stream(rStatusAkadSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
