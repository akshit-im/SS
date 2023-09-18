package com.amdocs.geo.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.amdocs.geo.dto.CityView;
import com.amdocs.geo.dto.StateView;
import com.amdocs.geo.entity.Country;

import jakarta.transaction.Transactional;

@Service
@Transactional
class GeoServiceImpl implements GeoService {

	@Autowired
	private CityViewRepo cityVRepo;

	@Autowired
	private StateViewRepo stateVRepo;

	@Autowired
	private CountryRepo countryRepo;

	@Override
	public List<Country> country() throws Throwable {
		return countryRepo.findAll();
	}

	@Override
	public Country country(Long countryId) throws Throwable {
		return countryRepo.getById(countryId);
	}

	@Override
	public List<StateView> state(StateView stateView) throws Throwable {
		return stateVRepo.findAll(Example.of(stateView));
	}

	@Override
	public List<StateView> state() throws Throwable {
		return stateVRepo.findAll();
	}

	@Override
	public StateView state(Long stateId) throws Throwable {
		return stateVRepo.findById(stateId).get();
	}

	@Override
	public List<CityView> city(CityView cityView) throws Throwable {
		return cityVRepo.findAll(Example.of(cityView));
	}

	@Override
	public List<CityView> city() throws Throwable {
		return cityVRepo.findAll();
	}

	@Override
	public CityView city(Long cityId) throws Throwable {
		return cityVRepo.findById(cityId).get();
	}

}
