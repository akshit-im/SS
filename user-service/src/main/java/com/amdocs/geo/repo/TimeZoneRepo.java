package com.amdocs.geo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amdocs.geo.entity.TimeZone;

interface TimeZoneRepo extends JpaRepository<TimeZone, Long> {

	public Optional<TimeZone> findByName(String name);
}
