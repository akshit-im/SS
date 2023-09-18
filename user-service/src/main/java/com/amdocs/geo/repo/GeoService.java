package com.amdocs.geo.repo;

import java.util.List;

import com.amdocs.geo.dto.CityView;
import com.amdocs.geo.dto.StateView;
import com.amdocs.geo.entity.Country;

public interface GeoService {

	public List<Country> country() throws Throwable;

	public Country country(Long countryId) throws Throwable;

	public List<StateView> state(StateView stateView) throws Throwable;

	public StateView state(Long stateId) throws Throwable;

	public List<StateView> state() throws Throwable;

	public List<CityView> city(CityView cityView) throws Throwable;

	public List<CityView> city() throws Throwable;

	public CityView city(Long cityId) throws Throwable;
}
