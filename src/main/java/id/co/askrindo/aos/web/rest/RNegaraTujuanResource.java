package id.co.askrindo.aos.web.rest;

import com.codahale.metrics.annotation.Timed;
import id.co.askrindo.aos.domain.RNegaraTujuan;
import id.co.askrindo.aos.repository.RNegaraTujuanRepository;
import id.co.askrindo.aos.repository.search.RNegaraTujuanSearchRepository;
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
 * REST controller for managing RNegaraTujuan.
 */
@RestController
@RequestMapping("/api")
public class RNegaraTujuanResource {

    private final Logger log = LoggerFactory.getLogger(RNegaraTujuanResource.class);
        
    @Inject
    private RNegaraTujuanRepository rNegaraTujuanRepository;
    
    @Inject
    private RNegaraTujuanSearchRepository rNegaraTujuanSearchRepository;
    
    /**
     * POST  /rNegaraTujuans -> Create a new rNegaraTujuan.
     */
    @RequestMapping(value = "/rNegaraTujuans",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RNegaraTujuan> createRNegaraTujuan(@Valid @RequestBody RNegaraTujuan rNegaraTujuan) throws URISyntaxException {
        log.debug("REST request to save RNegaraTujuan : {}", rNegaraTujuan);
        if (rNegaraTujuan.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rNegaraTujuan", "idexists", "A new rNegaraTujuan cannot already have an ID")).body(null);
        }
        RNegaraTujuan result = rNegaraTujuanRepository.save(rNegaraTujuan);
        rNegaraTujuanSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/rNegaraTujuans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rNegaraTujuan", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rNegaraTujuans -> Updates an existing rNegaraTujuan.
     */
    @RequestMapping(value = "/rNegaraTujuans",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RNegaraTujuan> updateRNegaraTujuan(@Valid @RequestBody RNegaraTujuan rNegaraTujuan) throws URISyntaxException {
        log.debug("REST request to update RNegaraTujuan : {}", rNegaraTujuan);
        if (rNegaraTujuan.getId() == null) {
            return createRNegaraTujuan(rNegaraTujuan);
        }
        RNegaraTujuan result = rNegaraTujuanRepository.save(rNegaraTujuan);
        rNegaraTujuanSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rNegaraTujuan", rNegaraTujuan.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rNegaraTujuans -> get all the rNegaraTujuans.
     */
    @RequestMapping(value = "/rNegaraTujuans",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RNegaraTujuan>> getAllRNegaraTujuans(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of RNegaraTujuans");
        Page<RNegaraTujuan> page = rNegaraTujuanRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rNegaraTujuans");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rNegaraTujuans/:id -> get the "id" rNegaraTujuan.
     */
    @RequestMapping(value = "/rNegaraTujuans/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RNegaraTujuan> getRNegaraTujuan(@PathVariable Long id) {
        log.debug("REST request to get RNegaraTujuan : {}", id);
        RNegaraTujuan rNegaraTujuan = rNegaraTujuanRepository.findOne(id);
        return Optional.ofNullable(rNegaraTujuan)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rNegaraTujuans/:id -> delete the "id" rNegaraTujuan.
     */
    @RequestMapping(value = "/rNegaraTujuans/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRNegaraTujuan(@PathVariable Long id) {
        log.debug("REST request to delete RNegaraTujuan : {}", id);
        rNegaraTujuanRepository.delete(id);
        rNegaraTujuanSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rNegaraTujuan", id.toString())).build();
    }

    /**
     * SEARCH  /_search/rNegaraTujuans/:query -> search for the rNegaraTujuan corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/rNegaraTujuans/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RNegaraTujuan> searchRNegaraTujuans(@PathVariable String query) {
        log.debug("REST request to search RNegaraTujuans for query {}", query);
        return StreamSupport
            .stream(rNegaraTujuanSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
