package id.co.askrindo.aos.web.rest;

import com.codahale.metrics.annotation.Timed;
import id.co.askrindo.aos.domain.RKolektibilitas;
import id.co.askrindo.aos.repository.RKolektibilitasRepository;
import id.co.askrindo.aos.repository.search.RKolektibilitasSearchRepository;
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
 * REST controller for managing RKolektibilitas.
 */
@RestController
@RequestMapping("/api")
public class RKolektibilitasResource {

    private final Logger log = LoggerFactory.getLogger(RKolektibilitasResource.class);
        
    @Inject
    private RKolektibilitasRepository rKolektibilitasRepository;
    
    @Inject
    private RKolektibilitasSearchRepository rKolektibilitasSearchRepository;
    
    /**
     * POST  /rKolektibilitass -> Create a new rKolektibilitas.
     */
    @RequestMapping(value = "/rKolektibilitass",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RKolektibilitas> createRKolektibilitas(@Valid @RequestBody RKolektibilitas rKolektibilitas) throws URISyntaxException {
        log.debug("REST request to save RKolektibilitas : {}", rKolektibilitas);
        if (rKolektibilitas.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rKolektibilitas", "idexists", "A new rKolektibilitas cannot already have an ID")).body(null);
        }
        RKolektibilitas result = rKolektibilitasRepository.save(rKolektibilitas);
        rKolektibilitasSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/rKolektibilitass/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rKolektibilitas", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rKolektibilitass -> Updates an existing rKolektibilitas.
     */
    @RequestMapping(value = "/rKolektibilitass",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RKolektibilitas> updateRKolektibilitas(@Valid @RequestBody RKolektibilitas rKolektibilitas) throws URISyntaxException {
        log.debug("REST request to update RKolektibilitas : {}", rKolektibilitas);
        if (rKolektibilitas.getId() == null) {
            return createRKolektibilitas(rKolektibilitas);
        }
        RKolektibilitas result = rKolektibilitasRepository.save(rKolektibilitas);
        rKolektibilitasSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rKolektibilitas", rKolektibilitas.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rKolektibilitass -> get all the rKolektibilitass.
     */
    @RequestMapping(value = "/rKolektibilitass",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RKolektibilitas>> getAllRKolektibilitass(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of RKolektibilitass");
        Page<RKolektibilitas> page = rKolektibilitasRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rKolektibilitass");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rKolektibilitass/:id -> get the "id" rKolektibilitas.
     */
    @RequestMapping(value = "/rKolektibilitass/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RKolektibilitas> getRKolektibilitas(@PathVariable Long id) {
        log.debug("REST request to get RKolektibilitas : {}", id);
        RKolektibilitas rKolektibilitas = rKolektibilitasRepository.findOne(id);
        return Optional.ofNullable(rKolektibilitas)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rKolektibilitass/:id -> delete the "id" rKolektibilitas.
     */
    @RequestMapping(value = "/rKolektibilitass/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRKolektibilitas(@PathVariable Long id) {
        log.debug("REST request to delete RKolektibilitas : {}", id);
        rKolektibilitasRepository.delete(id);
        rKolektibilitasSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rKolektibilitas", id.toString())).build();
    }

    /**
     * SEARCH  /_search/rKolektibilitass/:query -> search for the rKolektibilitas corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/rKolektibilitass/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RKolektibilitas> searchRKolektibilitass(@PathVariable String query) {
        log.debug("REST request to search RKolektibilitass for query {}", query);
        return StreamSupport
            .stream(rKolektibilitasSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
