package id.co.askrindo.aos.repository.search;

import id.co.askrindo.aos.domain.RStatusRekening;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the RStatusRekening entity.
 */
public interface RStatusRekeningSearchRepository extends ElasticsearchRepository<RStatusRekening, Long> {
}
