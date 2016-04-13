package id.co.askrindo.aos.repository.search;

import id.co.askrindo.aos.domain.RMaritalStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the RMaritalStatus entity.
 */
public interface RMaritalStatusSearchRepository extends ElasticsearchRepository<RMaritalStatus, Long> {
}
