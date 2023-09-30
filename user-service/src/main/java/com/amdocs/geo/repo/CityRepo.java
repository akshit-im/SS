package com.amdocs.geo.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.amdocs.geo.entity.City;
import com.amdocs.geo.entity.State;

interface CityRepo extends JpaRepository<City, UUID> {

	@EntityGraph(attributePaths = {"state"})
	public List<City> findByState(State state);
}

interface CityCrudRepo extends CrudRepository<City, UUID> {

	@EntityGraph(attributePaths = {"state"})
	public List<City> findAll(Example<City> city, Sort sort);
}