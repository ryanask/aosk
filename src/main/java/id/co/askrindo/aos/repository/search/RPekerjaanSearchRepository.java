package id.co.askrindo.aos.repository.search;

import id.co.askrindo.aos.domain.RPekerjaan;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the RPekerjaan entity.
 */
public interface RPekerjaanSearchRepository extends ElasticsearchRepository<RPekerjaan, Long> {
}
