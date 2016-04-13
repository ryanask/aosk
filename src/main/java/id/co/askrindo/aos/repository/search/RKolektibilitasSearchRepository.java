package id.co.askrindo.aos.repository.search;

import id.co.askrindo.aos.domain.RKolektibilitas;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the RKolektibilitas entity.
 */
public interface RKolektibilitasSearchRepository extends ElasticsearchRepository<RKolektibilitas, Long> {
}
