package id.co.askrindo.aos.repository;

import id.co.askrindo.aos.domain.RMaritalStatus;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RMaritalStatus entity.
 */
public interface RMaritalStatusRepository extends JpaRepository<RMaritalStatus,Long> {

}
