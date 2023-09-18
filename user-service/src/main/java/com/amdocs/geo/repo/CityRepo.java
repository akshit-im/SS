package com.amdocs.geo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amdocs.geo.dto.CityView;
import com.amdocs.geo.entity.City;

interface CityRepo extends JpaRepository<City, Long> {

}

interface CityViewRepo extends JpaRepository<CityView, Long> {

}
