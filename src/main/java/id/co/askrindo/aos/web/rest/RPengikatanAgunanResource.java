package id.co.askrindo.aos.web.rest;

import com.codahale.metrics.annotation.Timed;
import id.co.askrindo.aos.domain.RPengikatanAgunan;
import id.co.askrindo.aos.repository.RPengikatanAgunanRepository;
import id.co.askrindo.aos.repository.search.RPengikatanAgunanSearchRepository;
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
 * REST controller for managing RPengikatanAgunan.
 */
@RestController
@RequestMapping("/api")
public class RPengikatanAgunanResource {

    private final Logger log = LoggerFactory.getLogger(RPengikatanAgunanResource.class);
        
    @Inject
    private RPengikatanAgunanRepository rPengikatanAgunanRepository;
    
    @Inject
    private RPengikatanAgunanSearchRepository rPengikatanAgunanSearchRepository;
    
    /**
     * POST  /rPengikatanAgunans -> Create a new rPengikatanAgunan.
     */
    @RequestMapping(value = "/rPengikatanAgunans",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RPengikatanAgunan> createRPengikatanAgunan(@Valid @RequestBody RPengikatanAgunan rPengikatanAgunan) throws URISyntaxException {
        log.debug("REST request to save RPengikatanAgunan : {}", rPengikatanAgunan);
        if (rPengikatanAgunan.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rPengikatanAgunan", "idexists", "A new rPengikatanAgunan cannot already have an ID")).body(null);
        }
        RPengikatanAgunan result = rPengikatanAgunanRepository.save(rPengikatanAgunan);
        rPengikatanAgunanSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/rPengikatanAgunans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rPengikatanAgunan", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rPengikatanAgunans -> Updates an existing rPengikatanAgunan.
     */
    @RequestMapping(value = "/rPengikatanAgunans",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RPengikatanAgunan> updateRPengikatanAgunan(@Valid @RequestBody RPengikatanAgunan rPengikatanAgunan) throws URISyntaxException {
        log.debug("REST request to update RPengikatanAgunan : {}", rPengikatanAgunan);
        if (rPengikatanAgunan.getId() == null) {
            return createRPengikatanAgunan(rPengikatanAgunan);
        }
        RPengikatanAgunan result = rPengikatanAgunanRepository.save(rPengikatanAgunan);
        rPengikatanAgunanSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rPengikatanAgunan", rPengikatanAgunan.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rPengikatanAgunans -> get all the rPengikatanAgunans.
     */
    @RequestMapping(value = "/rPengikatanAgunans",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RPengikatanAgunan>> getAllRPengikatanAgunans(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of RPengikatanAgunans");
        Page<RPengikatanAgunan> page = rPengikatanAgunanRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rPengikatanAgunans");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rPengikatanAgunans/:id -> get the "id" rPengikatanAgunan.
     */
    @RequestMapping(value = "/rPengikatanAgunans/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RPengikatanAgunan> getRPengikatanAgunan(@PathVariable Long id) {
        log.debug("REST request to get RPengikatanAgunan : {}", id);
        RPengikatanAgunan rPengikatanAgunan = rPengikatanAgunanRepository.findOne(id);
        return Optional.ofNullable(rPengikatanAgunan)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rPengikatanAgunans/:id -> delete the "id" rPengikatanAgunan.
     */
    @RequestMapping(value = "/rPengikatanAgunans/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRPengikatanAgunan(@PathVariable Long id) {
        log.debug("REST request to delete RPengikatanAgunan : {}", id);
        rPengikatanAgunanRepository.delete(id);
        rPengikatanAgunanSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rPengikatanAgunan", id.toString())).build();
    }

    /**
     * SEARCH  /_search/rPengikatanAgunans/:query -> search for the rPengikatanAgunan corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/rPengikatanAgunans/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RPengikatanAgunan> searchRPengikatanAgunans(@PathVariable String query) {
        log.debug("REST request to search RPengikatanAgunans for query {}", query);
        return StreamSupport
            .stream(rPengikatanAgunanSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
