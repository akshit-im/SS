package com.amdocs.geo.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	private StateRepo stateRepo;

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
	public Country country(Long countryId) throws Throwable {
		return countryRepo.getById(countryId);
	}

	@Override
	public List<State> state(Country country) throws Throwable {
		return stateRepo.findByCountry(country);
	}

	@Override
	public State state(Long stateId) throws Throwable {
		return stateRepo.findById(stateId).get();
	}

	@Override
	public List<City> city(State state) throws Throwable {
		return cityRepo.findByState(state);
	}

	@Override
	public City city(Long cityId) throws Throwable {
		return cityRepo.findById(cityId).get();
	}

}
