package id.co.askrindo.aos.repository.search;

import id.co.askrindo.aos.domain.RNegaraTujuan;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the RNegaraTujuan entity.
 */
public interface RNegaraTujuanSearchRepository extends ElasticsearchRepository<RNegaraTujuan, Long> {
}
