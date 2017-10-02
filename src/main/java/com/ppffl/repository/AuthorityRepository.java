package com.ppffl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppffl.domain.Authority;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
