package id.co.askrindo.aos.repository;

import id.co.askrindo.aos.domain.RKantor;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RKantor entity.
 */
public interface RKantorRepository extends JpaRepository<RKantor,Long> {

}
