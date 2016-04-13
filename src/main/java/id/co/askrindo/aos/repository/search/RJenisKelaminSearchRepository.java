package id.co.askrindo.aos.repository.search;

import id.co.askrindo.aos.domain.RJenisKelamin;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the RJenisKelamin entity.
 */
public interface RJenisKelaminSearchRepository extends ElasticsearchRepository<RJenisKelamin, Long> {
}
