package id.co.askrindo.aos.repository.search;

import id.co.askrindo.aos.domain.RPengikatanAgunan;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the RPengikatanAgunan entity.
 */
public interface RPengikatanAgunanSearchRepository extends ElasticsearchRepository<RPengikatanAgunan, Long> {
}
