package com.amdocs.geo.repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;

import com.amdocs.geo.entity.City;
import com.amdocs.geo.entity.Country;
import com.amdocs.geo.entity.State;
import com.amdocs.geo.entity.TimeZone;
import com.amdocs.user.entity.Type;

public interface GeoService {

	public TimeZone saveTimeZone(TimeZone timeZone);

	public Iterable<TimeZone> saveTimeZone(Iterable<TimeZone> timeZoneList);

	public Iterable<Country> saveCountry(Iterable<Country> countryList);

	public Iterable<State> saveState(Iterable<State> stateList);

	public Iterable<City> saveCity(Iterable<City> stateList);

	public Optional<TimeZone> timeZone(String name) throws Throwable;

	public List<Country> country() throws Throwable;

	public Optional<Country> country(UUID id) throws Throwable;

	public List<State> state(Example<State> state, Sort sort) throws Throwable;

	public Optional<State> state(UUID id) throws Throwable;

	public List<City> city(Example<City> city, Sort sort) throws Throwable;

	public Optional<City> city(UUID id) throws Throwable;

}
