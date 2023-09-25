package com.amdocs.geo.repo;

import java.util.List;
import java.util.Optional;

import com.amdocs.geo.entity.City;
import com.amdocs.geo.entity.Country;
import com.amdocs.geo.entity.State;
import com.amdocs.geo.entity.TimeZone;

public interface GeoService {

	public TimeZone saveTimeZone(TimeZone timeZone);

	public Iterable<TimeZone> saveTimeZone(Iterable<TimeZone> timeZoneList);

	public Iterable<Country> saveCountry(Iterable<Country> countryList);

	public Iterable<State> saveState(Iterable<State> stateList);

	public Iterable<City> saveCity(Iterable<City> stateList);

	public Optional<TimeZone> timeZone(String name) throws Throwable;

	public List<Country> country() throws Throwable;

	public Country country(Long countryId) throws Throwable;

	public List<State> state(Country country) throws Throwable;

	public State state(Long stateId) throws Throwable;

	public List<City> city(State state) throws Throwable;

	public City city(Long cityId) throws Throwable;

}
