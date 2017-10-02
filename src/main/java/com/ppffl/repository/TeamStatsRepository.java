package com.ppffl.repository;

import com.ppffl.domain.TeamStats;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TeamStats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamStatsRepository extends JpaRepository<TeamStats, Long> {

}
