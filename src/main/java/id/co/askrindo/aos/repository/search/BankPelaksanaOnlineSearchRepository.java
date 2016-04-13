package id.co.askrindo.aos.repository.search;

import id.co.askrindo.aos.domain.BankPelaksanaOnline;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the BankPelaksanaOnline entity.
 */
public interface BankPelaksanaOnlineSearchRepository extends ElasticsearchRepository<BankPelaksanaOnline, Long> {
}
