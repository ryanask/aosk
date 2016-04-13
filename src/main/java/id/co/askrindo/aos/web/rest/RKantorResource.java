package id.co.askrindo.aos.web.rest;

import com.codahale.metrics.annotation.Timed;
import id.co.askrindo.aos.domain.RKantor;
import id.co.askrindo.aos.repository.RKantorRepository;
import id.co.askrindo.aos.repository.search.RKantorSearchRepository;
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
 * REST controller for managing RKantor.
 */
@RestController
@RequestMapping("/api")
public class RKantorResource {

    private final Logger log = LoggerFactory.getLogger(RKantorResource.class);
        
    @Inject
    private RKantorRepository rKantorRepository;
    
    @Inject
    private RKantorSearchRepository rKantorSearchRepository;
    
    /**
     * POST  /rKantors -> Create a new rKantor.
     */
    @RequestMapping(value = "/rKantors",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RKantor> createRKantor(@Valid @RequestBody RKantor rKantor) throws URISyntaxException {
        log.debug("REST request to save RKantor : {}", rKantor);
        if (rKantor.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rKantor", "idexists", "A new rKantor cannot already have an ID")).body(null);
        }
        RKantor result = rKantorRepository.save(rKantor);
        rKantorSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/rKantors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rKantor", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rKantors -> Updates an existing rKantor.
     */
    @RequestMapping(value = "/rKantors",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RKantor> updateRKantor(@Valid @RequestBody RKantor rKantor) throws URISyntaxException {
        log.debug("REST request to update RKantor : {}", rKantor);
        if (rKantor.getId() == null) {
            return createRKantor(rKantor);
        }
        RKantor result = rKantorRepository.save(rKantor);
        rKantorSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rKantor", rKantor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rKantors -> get all the rKantors.
     */
    @RequestMapping(value = "/rKantors",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RKantor>> getAllRKantors(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of RKantors");
        Page<RKantor> page = rKantorRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rKantors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rKantors/:id -> get the "id" rKantor.
     */
    @RequestMapping(value = "/rKantors/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RKantor> getRKantor(@PathVariable Long id) {
        log.debug("REST request to get RKantor : {}", id);
        RKantor rKantor = rKantorRepository.findOne(id);
        return Optional.ofNullable(rKantor)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rKantors/:id -> delete the "id" rKantor.
     */
    @RequestMapping(value = "/rKantors/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRKantor(@PathVariable Long id) {
        log.debug("REST request to delete RKantor : {}", id);
        rKantorRepository.delete(id);
        rKantorSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rKantor", id.toString())).build();
    }

    /**
     * SEARCH  /_search/rKantors/:query -> search for the rKantor corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/rKantors/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RKantor> searchRKantors(@PathVariable String query) {
        log.debug("REST request to search RKantors for query {}", query);
        return StreamSupport
            .stream(rKantorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
