package com.amdocs.geo.repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.amdocs.geo.entity.City;
import com.amdocs.geo.entity.Country;
import com.amdocs.geo.entity.State;
import com.amdocs.geo.entity.TimeZone;

import jakarta.transaction.Transactional;

@Service
@Transactional
class GeoServiceImpl implements GeoService {

	@Autowired
	private CityRepo cityRepo;

	@Autowired
	private CityCrudRepo cityCrudRepo;

	@Autowired
	private StateRepo stateRepo;

	@Autowired
	private StateCrudRepo stateCrudRepo;

	@Autowired
	private CountryRepo countryRepo;

	@Autowired
	private TimeZoneRepo zoneRepo;

	@Override
	public TimeZone saveTimeZone(TimeZone timeZone) {
		return zoneRepo.saveAndFlush(timeZone);
	}

	@Override
	public Iterable<TimeZone> saveTimeZone(Iterable<TimeZone> timeZoneList) {
		return zoneRepo.saveAllAndFlush(timeZoneList);
	}

	@Override
	public Iterable<Country> saveCountry(Iterable<Country> countryList) {
		return countryRepo.saveAllAndFlush(countryList);
	}

	@Override
	public Iterable<State> saveState(Iterable<State> stateList) {
		return stateRepo.saveAllAndFlush(stateList);
	}

	@Override
	public Iterable<City> saveCity(Iterable<City> cityList) {
		return cityRepo.saveAllAndFlush(cityList);
	}

	@Override
	public Optional<TimeZone> timeZone(String name) throws Throwable {
		return zoneRepo.findByName(name);
	}

	@Override
	public List<Country> country() throws Throwable {
		return countryRepo.findAll();
	}

	@Override
	public Optional<Country> country(UUID id) throws Throwable {
		return countryRepo.findById(id);
	}

	@Override
	public List<State> state(Example<State> state, Sort sort) throws Throwable {
		return stateCrudRepo.findAll(state, sort);
	}

	@Override
	public Optional<State> state(UUID id) throws Throwable {
		return stateRepo.findById(id);
	}

	@Override
	public List<City> city(Example<City> city, Sort sort) throws Throwable {
		return cityCrudRepo.findAll(city, sort);
	}

	@Override
	public Optional<City> city(UUID id) throws Throwable {
		return cityRepo.findById(id);
	}

}
