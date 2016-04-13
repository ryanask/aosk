package id.co.askrindo.aos.repository.search;

import id.co.askrindo.aos.domain.RKantor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the RKantor entity.
 */
public interface RKantorSearchRepository extends ElasticsearchRepository<RKantor, Long> {
}
