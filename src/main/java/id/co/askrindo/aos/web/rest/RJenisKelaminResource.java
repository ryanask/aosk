package id.co.askrindo.aos.web.rest;

import com.codahale.metrics.annotation.Timed;
import id.co.askrindo.aos.domain.RJenisKelamin;
import id.co.askrindo.aos.repository.RJenisKelaminRepository;
import id.co.askrindo.aos.repository.search.RJenisKelaminSearchRepository;
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
 * REST controller for managing RJenisKelamin.
 */
@RestController
@RequestMapping("/api")
public class RJenisKelaminResource {

    private final Logger log = LoggerFactory.getLogger(RJenisKelaminResource.class);
        
    @Inject
    private RJenisKelaminRepository rJenisKelaminRepository;
    
    @Inject
    private RJenisKelaminSearchRepository rJenisKelaminSearchRepository;
    
    /**
     * POST  /rJenisKelamins -> Create a new rJenisKelamin.
     */
    @RequestMapping(value = "/rJenisKelamins",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RJenisKelamin> createRJenisKelamin(@Valid @RequestBody RJenisKelamin rJenisKelamin) throws URISyntaxException {
        log.debug("REST request to save RJenisKelamin : {}", rJenisKelamin);
        if (rJenisKelamin.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rJenisKelamin", "idexists", "A new rJenisKelamin cannot already have an ID")).body(null);
        }
        RJenisKelamin result = rJenisKelaminRepository.save(rJenisKelamin);
        rJenisKelaminSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/rJenisKelamins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rJenisKelamin", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rJenisKelamins -> Updates an existing rJenisKelamin.
     */
    @RequestMapping(value = "/rJenisKelamins",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RJenisKelamin> updateRJenisKelamin(@Valid @RequestBody RJenisKelamin rJenisKelamin) throws URISyntaxException {
        log.debug("REST request to update RJenisKelamin : {}", rJenisKelamin);
        if (rJenisKelamin.getId() == null) {
            return createRJenisKelamin(rJenisKelamin);
        }
        RJenisKelamin result = rJenisKelaminRepository.save(rJenisKelamin);
        rJenisKelaminSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rJenisKelamin", rJenisKelamin.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rJenisKelamins -> get all the rJenisKelamins.
     */
    @RequestMapping(value = "/rJenisKelamins",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RJenisKelamin>> getAllRJenisKelamins(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of RJenisKelamins");
        Page<RJenisKelamin> page = rJenisKelaminRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rJenisKelamins");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rJenisKelamins/:id -> get the "id" rJenisKelamin.
     */
    @RequestMapping(value = "/rJenisKelamins/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RJenisKelamin> getRJenisKelamin(@PathVariable Long id) {
        log.debug("REST request to get RJenisKelamin : {}", id);
        RJenisKelamin rJenisKelamin = rJenisKelaminRepository.findOne(id);
        return Optional.ofNullable(rJenisKelamin)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rJenisKelamins/:id -> delete the "id" rJenisKelamin.
     */
    @RequestMapping(value = "/rJenisKelamins/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRJenisKelamin(@PathVariable Long id) {
        log.debug("REST request to delete RJenisKelamin : {}", id);
        rJenisKelaminRepository.delete(id);
        rJenisKelaminSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rJenisKelamin", id.toString())).build();
    }

    /**
     * SEARCH  /_search/rJenisKelamins/:query -> search for the rJenisKelamin corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/rJenisKelamins/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RJenisKelamin> searchRJenisKelamins(@PathVariable String query) {
        log.debug("REST request to search RJenisKelamins for query {}", query);
        return StreamSupport
            .stream(rJenisKelaminSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
