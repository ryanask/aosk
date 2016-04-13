package id.co.askrindo.aos.web.rest;

import com.codahale.metrics.annotation.Timed;
import id.co.askrindo.aos.domain.RPekerjaan;
import id.co.askrindo.aos.repository.RPekerjaanRepository;
import id.co.askrindo.aos.repository.search.RPekerjaanSearchRepository;
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
 * REST controller for managing RPekerjaan.
 */
@RestController
@RequestMapping("/api")
public class RPekerjaanResource {

    private final Logger log = LoggerFactory.getLogger(RPekerjaanResource.class);
        
    @Inject
    private RPekerjaanRepository rPekerjaanRepository;
    
    @Inject
    private RPekerjaanSearchRepository rPekerjaanSearchRepository;
    
    /**
     * POST  /rPekerjaans -> Create a new rPekerjaan.
     */
    @RequestMapping(value = "/rPekerjaans",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RPekerjaan> createRPekerjaan(@Valid @RequestBody RPekerjaan rPekerjaan) throws URISyntaxException {
        log.debug("REST request to save RPekerjaan : {}", rPekerjaan);
        if (rPekerjaan.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rPekerjaan", "idexists", "A new rPekerjaan cannot already have an ID")).body(null);
        }
        RPekerjaan result = rPekerjaanRepository.save(rPekerjaan);
        rPekerjaanSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/rPekerjaans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rPekerjaan", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rPekerjaans -> Updates an existing rPekerjaan.
     */
    @RequestMapping(value = "/rPekerjaans",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RPekerjaan> updateRPekerjaan(@Valid @RequestBody RPekerjaan rPekerjaan) throws URISyntaxException {
        log.debug("REST request to update RPekerjaan : {}", rPekerjaan);
        if (rPekerjaan.getId() == null) {
            return createRPekerjaan(rPekerjaan);
        }
        RPekerjaan result = rPekerjaanRepository.save(rPekerjaan);
        rPekerjaanSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rPekerjaan", rPekerjaan.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rPekerjaans -> get all the rPekerjaans.
     */
    @RequestMapping(value = "/rPekerjaans",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RPekerjaan>> getAllRPekerjaans(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of RPekerjaans");
        Page<RPekerjaan> page = rPekerjaanRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rPekerjaans");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rPekerjaans/:id -> get the "id" rPekerjaan.
     */
    @RequestMapping(value = "/rPekerjaans/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RPekerjaan> getRPekerjaan(@PathVariable Long id) {
        log.debug("REST request to get RPekerjaan : {}", id);
        RPekerjaan rPekerjaan = rPekerjaanRepository.findOne(id);
        return Optional.ofNullable(rPekerjaan)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rPekerjaans/:id -> delete the "id" rPekerjaan.
     */
    @RequestMapping(value = "/rPekerjaans/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRPekerjaan(@PathVariable Long id) {
        log.debug("REST request to delete RPekerjaan : {}", id);
        rPekerjaanRepository.delete(id);
        rPekerjaanSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rPekerjaan", id.toString())).build();
    }

    /**
     * SEARCH  /_search/rPekerjaans/:query -> search for the rPekerjaan corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/rPekerjaans/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RPekerjaan> searchRPekerjaans(@PathVariable String query) {
        log.debug("REST request to search RPekerjaans for query {}", query);
        return StreamSupport
            .stream(rPekerjaanSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
