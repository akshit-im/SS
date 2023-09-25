package com.amdocs.geo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.amdocs.geo.entity.Country;
import com.amdocs.geo.entity.State;

interface StateRepo extends JpaRepository<State, Long> {

	@EntityGraph(attributePaths = {"country"})
	public List<State> findByCountry(Country country);

}
