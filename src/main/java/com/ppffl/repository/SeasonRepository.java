package com.ppffl.repository;

import com.ppffl.domain.Season;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Season entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {

}
