package id.co.askrindo.aos.repository.search;

import id.co.askrindo.aos.domain.RStatusAkad;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the RStatusAkad entity.
 */
public interface RStatusAkadSearchRepository extends ElasticsearchRepository<RStatusAkad, Long> {
}
