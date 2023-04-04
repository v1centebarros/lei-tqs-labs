package pt.ua.deti.tqs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.deti.tqs.cache.LocalCache;
import pt.ua.deti.tqs.cache.LocalVolatileCache;
import pt.ua.deti.tqs.data.AirQuality;
import pt.ua.deti.tqs.data.City;

@Service
public class CacheService {

    @Autowired
    private LocalVolatileCache<String, AirQuality> airQualityCache;

    @Autowired
    private LocalCache <String, City> cityCache;

    public Iterable<String> getCities() {
        return cityCache.getKeys();
    }

    public City getCity(String name) {
        return cityCache.get(name);
    }

    public boolean hasCityQuality(String city) {
        return airQualityCache.containsKey(city);
    }

    public AirQuality getAirQuality(String city) {
        return airQualityCache.get(city);
    }

    public void addAirQuality(String city, AirQuality airQuality) {
        airQualityCache.put(city, airQuality);
    }

    public void addCity(String city, City cityObject) {
        cityCache.put(city, cityObject);
    }

    public boolean hasCity(String city) {
        return cityCache.containsKey(city);
    }

}
