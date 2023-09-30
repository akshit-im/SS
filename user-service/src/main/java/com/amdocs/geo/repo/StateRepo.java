package com.amdocs.geo.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.amdocs.geo.entity.Country;
import com.amdocs.geo.entity.State;

interface StateRepo extends JpaRepository<State, UUID> {

	@EntityGraph(attributePaths = {"country"})
	public List<State> findByCountry(Country country);
}

interface StateCrudRepo extends CrudRepository<State, UUID> {

	@EntityGraph(attributePaths = {"country"})
	public List<State> findAll(Example<State> city, Sort sort);
}