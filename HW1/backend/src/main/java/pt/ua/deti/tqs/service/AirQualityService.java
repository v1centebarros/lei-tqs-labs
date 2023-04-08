package pt.ua.deti.tqs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pt.ua.deti.tqs.apis.ApiQuality;
import pt.ua.deti.tqs.cache.CacheStats;
import pt.ua.deti.tqs.data.AirQuality;
import pt.ua.deti.tqs.data.City;

import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.List;

@Service
public class AirQualityService {
    static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private CacheService cacheService;

    private final CacheStats airQualityCacheStats = new CacheStats();
    private final CacheStats airQualityForecastCacheStats = new CacheStats();

    private ApiQuality[] apis;
    public AirQualityService(ApiQuality[] apiQualities, CacheService cacheService) {
        this.apis = apiQualities;
        this.cacheService = cacheService;
    }
    public AirQuality getAirQuality(City city) {
        AirQuality airQuality;
        log.info("Requesting city {} from Cache", city);
        if (cacheService.hasCityQuality(city)) {
            airQuality = cacheService.getAirQuality(city);
            airQualityCacheStats.hit();
            log.info("City found in cache {} with {}", city, airQuality);
            return airQuality;
        }

        airQualityCacheStats.miss();

        for (ApiQuality api : apis) {
            log.info("Requesting city {} from API {}", city, api.getApiName());
            airQuality = api.getQuality(city);
            if (airQuality != null) {
                log.info("Caching {} current data", airQuality);
                cacheService.addAirQuality(city, airQuality);
                airQualityCacheStats.put();
                return airQuality;
            }
        }
        return null;
    }

    public List<AirQuality> getAirQualityForecast(City city) {
        if (cacheService.hasCityForecast(city)) {
            List<AirQuality> airQualityList = cacheService.getAirQualityForecast(city);
            log.info("City found in cache {} with {}", city, airQualityList);
            airQualityForecastCacheStats.hit();
            return airQualityList;
        }
        airQualityForecastCacheStats.miss();
        for (ApiQuality api : apis) {
            log.info("Requesting city {} from API {}", city, api.getApiName());
            List<AirQuality> airQualityList = api.getForecast(city);
            if (!airQualityList.isEmpty()) {
                log.info("Caching {} forecast data", airQualityList);
                cacheService.addAirQualityForecast(city, airQualityList);
                airQualityForecastCacheStats.put();
                return airQualityList;
            }
        }
        return Collections.emptyList();
    }


    public AirQuality getFromNinja(City city) {
        for (ApiQuality api : apis) {
            if (api.getApiName().equals("NinjaAPI")) {
                log.info("Requesting city {} from API {}", city, api.getApiName());
                return api.getQuality(city);
            }
        }
        return null;
    }

    public AirQuality getFromOpenWeather(City city) {
        for (ApiQuality api : apis) {
            if (api.getApiName().equals("OpenWeatherAPI")) {
                log.info("Requesting city {} from API {}", city, api.getApiName());
                return api.getQuality(city);
            }
        }
        return null;
    }

    public CacheStats getAirQualityCacheStats() {
        return airQualityCacheStats;
    }

    public CacheStats getAirQualityForecastCacheStats() {
        return airQualityForecastCacheStats;
    }
}
