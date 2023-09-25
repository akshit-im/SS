package com.amdocs.geo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.amdocs.geo.entity.City;
import com.amdocs.geo.entity.State;

interface CityRepo extends JpaRepository<City, Long> {

	@EntityGraph(attributePaths = {"state"})
	public List<City> findByState(State state);

}