package pt.ua.deti.tqs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.deti.tqs.cache.LocalVolatileCache;
import pt.ua.deti.tqs.data.AirQuality;

import java.util.List;

@Service
public class CacheService {

    @Autowired
    private LocalVolatileCache<String, AirQuality> airQualityCache;

    @Autowired
    private LocalVolatileCache<String, List<AirQuality>> airQualityForecastCache;

    public boolean hasCityQuality(String city) {
        return airQualityCache.containsKey(city);
    }

    public AirQuality getAirQuality(String city) {
        return airQualityCache.get(city);
    }

    public void addAirQuality(String city, AirQuality airQuality) {
        airQualityCache.put(city, airQuality);
    }


    public boolean hasCityForecast(String city) {
        return airQualityForecastCache.containsKey(city);
    }

    public List<AirQuality> getAirQualityForecast(String city) {
        return airQualityForecastCache.get(city);
    }

    public void addAirQualityForecast(String city, List<AirQuality> airQualityForecast) {
        airQualityForecastCache.put(city, airQualityForecast);
    }
}
