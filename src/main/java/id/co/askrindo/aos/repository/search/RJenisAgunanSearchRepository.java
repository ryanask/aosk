package id.co.askrindo.aos.repository.search;

import id.co.askrindo.aos.domain.RJenisAgunan;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the RJenisAgunan entity.
 */
public interface RJenisAgunanSearchRepository extends ElasticsearchRepository<RJenisAgunan, Long> {
}
