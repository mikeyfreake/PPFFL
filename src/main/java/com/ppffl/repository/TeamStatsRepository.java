package com.ppffl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ppffl.domain.TeamStats;


/**
 * Spring Data JPA repository for the TeamStats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamStatsRepository extends JpaRepository<TeamStats, Long> {

}
