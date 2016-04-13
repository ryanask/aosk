package id.co.askrindo.aos.repository;

import id.co.askrindo.aos.domain.BankPelaksanaOnline;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BankPelaksanaOnline entity.
 */
public interface BankPelaksanaOnlineRepository extends JpaRepository<BankPelaksanaOnline,Long> {

}
