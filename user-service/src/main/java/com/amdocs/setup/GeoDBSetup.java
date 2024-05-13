package com.amdocs.setup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.amdocs.geo.entity.City;
import com.amdocs.geo.entity.Country;
import com.amdocs.geo.entity.State;
import com.amdocs.geo.entity.TimeZone;
import com.amdocs.geo.repo.GeoService;

import jakarta.annotation.PostConstruct;

@Component
public class GeoDBSetup {

	@Autowired
	private GeoService geoSvc;

	@PostConstruct
	public void init() {

		try {
			if (geoSvc.country().size() < 1) {
				List<String> countryListIn = new ArrayList<>();
				List<String> stateListIn = new ArrayList<>();
				List<String> cityListIn = new ArrayList<>();
				String[] line = null;

				ClassPathResource cl = new ClassPathResource("geo/countries.csv");
				URL url = cl.getURL();
				try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
					Stream<String> stream = br.lines();
					countryListIn = stream.collect(Collectors.toList());
				} catch (IOException e) {
					System.err.println("file error " + e.getMessage());
				}
				Map<Integer, UUID> countryMap = new HashMap<>();
				List<Country> countryList = new LinkedList<Country>();
				Iterator<String> itr = countryListIn.iterator();
				while (itr.hasNext()) {
					line = itr.next().split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
					if (line[0].equalsIgnoreCase("id"))
						continue;
					Country country = new Country();
					country.setId(UUID.randomUUID());
					country.setName(line[1]);
					country.setCode(line[2]);
					country.setCode2(line[3]);
					country.setPhCode(line[5]);
					country.setCapital(line[6]);
					country.setCurrencyCode(line[7]);
					country.setCurrency(line[8]);
					country.setRegion(line[12]);
					country.setSubRegion(line[13]);
					country.setStatus(1);
					JSONArray zoneArray = new JSONArray(line[14].replaceAll("\"", ""));
					Set<TimeZone> timeZoneListIn = new HashSet<TimeZone>();
					for (int i = 0; i < zoneArray.length(); i++) {
						JSONObject zoneJson = zoneArray.getJSONObject(i);
						TimeZone timeZone = new TimeZone();
						timeZone.setName(zoneJson.getString("zoneName"));
						timeZone.setGmtOffset(Integer.toString(zoneJson.getInt("gmtOffset")));
						timeZone.setGmtName(zoneJson.getString("gmtOffsetName"));
						timeZone.setTzName(zoneJson.getString("tzName"));
						timeZone.setAbbreviation(zoneJson.getString("abbreviation"));
						Optional<TimeZone> timeZoneOpt = null;
						try {
							timeZoneOpt = geoSvc.timeZone(zoneJson.getString("zoneName"));
						} catch (JSONException e) {
							e.printStackTrace();
						} catch (Throwable e) {
							e.printStackTrace();
						}
						if (!timeZoneOpt.isPresent())
							geoSvc.saveTimeZone(timeZone);
						else
							timeZone = timeZoneOpt.get();

						timeZoneListIn.add(timeZone);
					}
					List<TimeZone> timeZoneListFinal = new ArrayList<TimeZone>();
					timeZoneListFinal.addAll(timeZoneListIn);
					country.setTimeZone(timeZoneListFinal);
					country.setLatitude(line[15]);
					country.setLongitude(line[16]);
					countryMap.put(Integer.parseInt(line[0].trim()), country.getId());
					countryList.add(country);
				}
				geoSvc.saveCountry(countryList);

				cl = new ClassPathResource("geo/states.csv");
				url = cl.getURL();
				try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
					Stream<String> stream = br.lines();
					stateListIn = stream.collect(Collectors.toList());
				} catch (IOException e) {
					System.err.println("file error " + e.getMessage());
				}
				Map<Integer, UUID> stateMap = new HashMap<>();
				Set<State> stateList = new HashSet<State>();
				itr = stateListIn.iterator();
				while (itr.hasNext()) {
					line = itr.next().split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
					if (line[0].equalsIgnoreCase("id"))
						continue;
					State state = new State();
					state.setId(UUID.randomUUID());
					state.setName(line[1].replace("\"", "").trim());
					state.setCountry(new Country(countryMap.get(Integer.parseInt(line[2].trim()))));
					state.setCode(line[5]);
					try {
						state.setType(line[6]);
						state.setLatitude(line[7]);
						state.setLongitude(line[8]);
					} catch (Exception e) {
					}
					state.setStatus(1);
					stateMap.put(Integer.parseInt(line[0].trim()), state.getId());
					stateList.add(state);
				}
				System.out.println("State count" + stateList.size());
				geoSvc.saveState(stateList);

				cl = new ClassPathResource("geo/cities.csv");
				url = cl.getURL();
				try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
					Stream<String> stream = br.lines();
					cityListIn = stream.collect(Collectors.toList());
				} catch (IOException e) {
					System.err.println("file error " + e.getMessage());
				}

				Set<City> cityList = new HashSet<City>();
				itr = cityListIn.iterator();
				while (itr.hasNext()) {
					line = itr.next().split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
					if (line[0].equalsIgnoreCase("id"))
						continue;
					City city = new City();
					city.setName(line[1].replace("\"", "").trim());
					city.setState(new State(stateMap.get(Integer.parseInt(line[2].trim()))));
					try {
						city.setLatitude(line[8]);
						city.setLongitude(line[9]);
					} catch (Exception e) {
					}
					city.setStatus(1);
					cityList.add(city);
				}
				geoSvc.saveCity(cityList);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}