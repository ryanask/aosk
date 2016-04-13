package id.co.askrindo.aos.web.rest;

import com.codahale.metrics.annotation.Timed;
import id.co.askrindo.aos.domain.RPendidikan;
import id.co.askrindo.aos.repository.RPendidikanRepository;
import id.co.askrindo.aos.repository.search.RPendidikanSearchRepository;
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
 * REST controller for managing RPendidikan.
 */
@RestController
@RequestMapping("/api")
public class RPendidikanResource {

    private final Logger log = LoggerFactory.getLogger(RPendidikanResource.class);
        
    @Inject
    private RPendidikanRepository rPendidikanRepository;
    
    @Inject
    private RPendidikanSearchRepository rPendidikanSearchRepository;
    
    /**
     * POST  /rPendidikans -> Create a new rPendidikan.
     */
    @RequestMapping(value = "/rPendidikans",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RPendidikan> createRPendidikan(@Valid @RequestBody RPendidikan rPendidikan) throws URISyntaxException {
        log.debug("REST request to save RPendidikan : {}", rPendidikan);
        if (rPendidikan.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rPendidikan", "idexists", "A new rPendidikan cannot already have an ID")).body(null);
        }
        RPendidikan result = rPendidikanRepository.save(rPendidikan);
        rPendidikanSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/rPendidikans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rPendidikan", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rPendidikans -> Updates an existing rPendidikan.
     */
    @RequestMapping(value = "/rPendidikans",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RPendidikan> updateRPendidikan(@Valid @RequestBody RPendidikan rPendidikan) throws URISyntaxException {
        log.debug("REST request to update RPendidikan : {}", rPendidikan);
        if (rPendidikan.getId() == null) {
            return createRPendidikan(rPendidikan);
        }
        RPendidikan result = rPendidikanRepository.save(rPendidikan);
        rPendidikanSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rPendidikan", rPendidikan.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rPendidikans -> get all the rPendidikans.
     */
    @RequestMapping(value = "/rPendidikans",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RPendidikan>> getAllRPendidikans(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of RPendidikans");
        Page<RPendidikan> page = rPendidikanRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rPendidikans");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rPendidikans/:id -> get the "id" rPendidikan.
     */
    @RequestMapping(value = "/rPendidikans/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RPendidikan> getRPendidikan(@PathVariable Long id) {
        log.debug("REST request to get RPendidikan : {}", id);
        RPendidikan rPendidikan = rPendidikanRepository.findOne(id);
        return Optional.ofNullable(rPendidikan)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rPendidikans/:id -> delete the "id" rPendidikan.
     */
    @RequestMapping(value = "/rPendidikans/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRPendidikan(@PathVariable Long id) {
        log.debug("REST request to delete RPendidikan : {}", id);
        rPendidikanRepository.delete(id);
        rPendidikanSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rPendidikan", id.toString())).build();
    }

    /**
     * SEARCH  /_search/rPendidikans/:query -> search for the rPendidikan corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/rPendidikans/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RPendidikan> searchRPendidikans(@PathVariable String query) {
        log.debug("REST request to search RPendidikans for query {}", query);
        return StreamSupport
            .stream(rPendidikanSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
