package id.co.askrindo.aos.repository.search;

import id.co.askrindo.aos.domain.RPendidikan;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the RPendidikan entity.
 */
public interface RPendidikanSearchRepository extends ElasticsearchRepository<RPendidikan, Long> {
}
