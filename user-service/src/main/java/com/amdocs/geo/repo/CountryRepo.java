package com.amdocs.geo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amdocs.geo.entity.Country; 

interface CountryRepo extends JpaRepository<Country, Long> {

}
