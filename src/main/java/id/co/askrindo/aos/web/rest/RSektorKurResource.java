package id.co.askrindo.aos.web.rest;

import com.codahale.metrics.annotation.Timed;
import id.co.askrindo.aos.domain.RSektorKur;
import id.co.askrindo.aos.repository.RSektorKurRepository;
import id.co.askrindo.aos.repository.search.RSektorKurSearchRepository;
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
 * REST controller for managing RSektorKur.
 */
@RestController
@RequestMapping("/api")
public class RSektorKurResource {

    private final Logger log = LoggerFactory.getLogger(RSektorKurResource.class);
        
    @Inject
    private RSektorKurRepository rSektorKurRepository;
    
    @Inject
    private RSektorKurSearchRepository rSektorKurSearchRepository;
    
    /**
     * POST  /rSektorKurs -> Create a new rSektorKur.
     */
    @RequestMapping(value = "/rSektorKurs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RSektorKur> createRSektorKur(@Valid @RequestBody RSektorKur rSektorKur) throws URISyntaxException {
        log.debug("REST request to save RSektorKur : {}", rSektorKur);
        if (rSektorKur.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rSektorKur", "idexists", "A new rSektorKur cannot already have an ID")).body(null);
        }
        RSektorKur result = rSektorKurRepository.save(rSektorKur);
        rSektorKurSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/rSektorKurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rSektorKur", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rSektorKurs -> Updates an existing rSektorKur.
     */
    @RequestMapping(value = "/rSektorKurs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RSektorKur> updateRSektorKur(@Valid @RequestBody RSektorKur rSektorKur) throws URISyntaxException {
        log.debug("REST request to update RSektorKur : {}", rSektorKur);
        if (rSektorKur.getId() == null) {
            return createRSektorKur(rSektorKur);
        }
        RSektorKur result = rSektorKurRepository.save(rSektorKur);
        rSektorKurSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rSektorKur", rSektorKur.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rSektorKurs -> get all the rSektorKurs.
     */
    @RequestMapping(value = "/rSektorKurs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RSektorKur>> getAllRSektorKurs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of RSektorKurs");
        Page<RSektorKur> page = rSektorKurRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rSektorKurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rSektorKurs/:id -> get the "id" rSektorKur.
     */
    @RequestMapping(value = "/rSektorKurs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RSektorKur> getRSektorKur(@PathVariable Long id) {
        log.debug("REST request to get RSektorKur : {}", id);
        RSektorKur rSektorKur = rSektorKurRepository.findOne(id);
        return Optional.ofNullable(rSektorKur)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rSektorKurs/:id -> delete the "id" rSektorKur.
     */
    @RequestMapping(value = "/rSektorKurs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRSektorKur(@PathVariable Long id) {
        log.debug("REST request to delete RSektorKur : {}", id);
        rSektorKurRepository.delete(id);
        rSektorKurSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rSektorKur", id.toString())).build();
    }

    /**
     * SEARCH  /_search/rSektorKurs/:query -> search for the rSektorKur corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/rSektorKurs/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RSektorKur> searchRSektorKurs(@PathVariable String query) {
        log.debug("REST request to search RSektorKurs for query {}", query);
        return StreamSupport
            .stream(rSektorKurSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
