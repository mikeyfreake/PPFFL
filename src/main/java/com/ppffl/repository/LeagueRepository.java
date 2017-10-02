package com.ppffl.repository;

import com.ppffl.domain.League;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the League entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {

}
