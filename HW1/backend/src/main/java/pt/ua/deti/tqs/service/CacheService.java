package pt.ua.deti.tqs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.deti.tqs.cache.LocalVolatileCache;
import pt.ua.deti.tqs.data.AirQuality;

@Service
public class CacheService {

    @Autowired
    private LocalVolatileCache<String, AirQuality> airQualityCache;

    public boolean hasCityQuality(String city) {
        return airQualityCache.containsKey(city);
    }

    public AirQuality getAirQuality(String city) {
        return airQualityCache.get(city);
    }

    public void addAirQuality(String city, AirQuality airQuality) {
        airQualityCache.put(city, airQuality);
    }

}
