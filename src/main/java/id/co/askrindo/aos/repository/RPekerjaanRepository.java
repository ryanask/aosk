package id.co.askrindo.aos.repository;

import id.co.askrindo.aos.domain.RPekerjaan;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RPekerjaan entity.
 */
public interface RPekerjaanRepository extends JpaRepository<RPekerjaan,Long> {

}
