package id.co.askrindo.aos.web.rest;

import com.codahale.metrics.annotation.Timed;
import id.co.askrindo.aos.domain.RJenisAgunan;
import id.co.askrindo.aos.repository.RJenisAgunanRepository;
import id.co.askrindo.aos.repository.search.RJenisAgunanSearchRepository;
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
 * REST controller for managing RJenisAgunan.
 */
@RestController
@RequestMapping("/api")
public class RJenisAgunanResource {

    private final Logger log = LoggerFactory.getLogger(RJenisAgunanResource.class);
        
    @Inject
    private RJenisAgunanRepository rJenisAgunanRepository;
    
    @Inject
    private RJenisAgunanSearchRepository rJenisAgunanSearchRepository;
    
    /**
     * POST  /rJenisAgunans -> Create a new rJenisAgunan.
     */
    @RequestMapping(value = "/rJenisAgunans",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RJenisAgunan> createRJenisAgunan(@Valid @RequestBody RJenisAgunan rJenisAgunan) throws URISyntaxException {
        log.debug("REST request to save RJenisAgunan : {}", rJenisAgunan);
        if (rJenisAgunan.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rJenisAgunan", "idexists", "A new rJenisAgunan cannot already have an ID")).body(null);
        }
        RJenisAgunan result = rJenisAgunanRepository.save(rJenisAgunan);
        rJenisAgunanSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/rJenisAgunans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rJenisAgunan", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rJenisAgunans -> Updates an existing rJenisAgunan.
     */
    @RequestMapping(value = "/rJenisAgunans",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RJenisAgunan> updateRJenisAgunan(@Valid @RequestBody RJenisAgunan rJenisAgunan) throws URISyntaxException {
        log.debug("REST request to update RJenisAgunan : {}", rJenisAgunan);
        if (rJenisAgunan.getId() == null) {
            return createRJenisAgunan(rJenisAgunan);
        }
        RJenisAgunan result = rJenisAgunanRepository.save(rJenisAgunan);
        rJenisAgunanSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rJenisAgunan", rJenisAgunan.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rJenisAgunans -> get all the rJenisAgunans.
     */
    @RequestMapping(value = "/rJenisAgunans",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RJenisAgunan>> getAllRJenisAgunans(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of RJenisAgunans");
        Page<RJenisAgunan> page = rJenisAgunanRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rJenisAgunans");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rJenisAgunans/:id -> get the "id" rJenisAgunan.
     */
    @RequestMapping(value = "/rJenisAgunans/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RJenisAgunan> getRJenisAgunan(@PathVariable Long id) {
        log.debug("REST request to get RJenisAgunan : {}", id);
        RJenisAgunan rJenisAgunan = rJenisAgunanRepository.findOne(id);
        return Optional.ofNullable(rJenisAgunan)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rJenisAgunans/:id -> delete the "id" rJenisAgunan.
     */
    @RequestMapping(value = "/rJenisAgunans/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRJenisAgunan(@PathVariable Long id) {
        log.debug("REST request to delete RJenisAgunan : {}", id);
        rJenisAgunanRepository.delete(id);
        rJenisAgunanSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rJenisAgunan", id.toString())).build();
    }

    /**
     * SEARCH  /_search/rJenisAgunans/:query -> search for the rJenisAgunan corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/rJenisAgunans/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RJenisAgunan> searchRJenisAgunans(@PathVariable String query) {
        log.debug("REST request to search RJenisAgunans for query {}", query);
        return StreamSupport
            .stream(rJenisAgunanSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
