package pt.ua.deti.tqs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pt.ua.deti.tqs.apis.GeoAPI;
import pt.ua.deti.tqs.data.City;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

@Service
public class CityService {

    static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final Map<String, City> cities;

    private final GeoAPI geoAPI;

    public CityService(GeoAPI geoAPI) {
        this.geoAPI = geoAPI;
        this.cities = new HashMap<>();
    }

    public City getCity(String city) {
        city = city.trim().toLowerCase();
        log.info("Requesting city {} from Cache", city);
        if (cities.containsKey(city)) {
            log.info("City found in cache {}", city);
            return cities.get(city);
        }

        log.info("{} not found in cache retrieving from Geocode Maps", city);
        City cityData = geoAPI.fetchCity(city);
        if (cityData == null) {
            return null;
        }

        log.info("Caching {} current data", cityData);
        cities.put(city, cityData);
        return cityData;
    }
}