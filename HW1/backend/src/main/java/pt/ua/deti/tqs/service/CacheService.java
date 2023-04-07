package pt.ua.deti.tqs.service;

import org.springframework.stereotype.Service;
import pt.ua.deti.tqs.cache.LocalVolatileCache;
import pt.ua.deti.tqs.data.AirQuality;
import pt.ua.deti.tqs.data.City;

import java.util.List;

@Service
public class CacheService {

    private final LocalVolatileCache<City, AirQuality> airQualityCache = new LocalVolatileCache<>();
    private final LocalVolatileCache<City, List<AirQuality>> airQualityForecastCache = new LocalVolatileCache<>();

    public boolean hasCityQuality(City city) {
        return airQualityCache.containsKey(city);
    }

    public AirQuality getAirQuality(City city) {
        return airQualityCache.get(city);
    }

    public void addAirQuality(City city, AirQuality airQuality) {
        airQualityCache.put(city, airQuality);
    }


    public boolean hasCityForecast(City city) {
        return airQualityForecastCache.containsKey(city);
    }

    public List<AirQuality> getAirQualityForecast(City city) {
        return airQualityForecastCache.get(city);
    }

    public void addAirQualityForecast(City city, List<AirQuality> airQualityForecast) {
        airQualityForecastCache.put(city, airQualityForecast);
    }
}
