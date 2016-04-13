package id.co.askrindo.aos.repository.search;

import id.co.askrindo.aos.domain.RSektorKur;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the RSektorKur entity.
 */
public interface RSektorKurSearchRepository extends ElasticsearchRepository<RSektorKur, Long> {
}
