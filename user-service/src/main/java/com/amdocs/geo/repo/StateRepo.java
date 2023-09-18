package com.amdocs.geo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amdocs.geo.dto.StateView;
import com.amdocs.geo.entity.State;

interface StateRepo extends JpaRepository<State, Long> {

}

interface StateViewRepo extends JpaRepository<StateView, Long> {

}
