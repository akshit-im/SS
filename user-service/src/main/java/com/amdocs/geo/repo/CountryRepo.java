package com.amdocs.geo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.amdocs.geo.entity.Country;

interface CountryRepo extends JpaRepository<Country, Long> {

	@Override
	@EntityGraph(attributePaths = {"timeZone"})
	public List<Country> findAll();
}
