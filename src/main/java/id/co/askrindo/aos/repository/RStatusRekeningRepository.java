package id.co.askrindo.aos.repository;

import id.co.askrindo.aos.domain.RStatusRekening;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RStatusRekening entity.
 */
public interface RStatusRekeningRepository extends JpaRepository<RStatusRekening,Long> {

}
