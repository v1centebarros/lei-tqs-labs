package pt.ua.deti.tqs.service;

import com.jayway.jsonpath.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pt.ua.deti.tqs.data.AirQuality;
import reactor.core.publisher.Mono;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;

@Service
public class AirQualityService implements IAirQualityService {
    static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private CacheService cacheService;

    @Autowired
    private CityService cityService;

    @Override
    public AirQuality getAirQuality(String city) {
        AirQuality airQuality;
        log.info("Requesting city {} from Cache", city);
        if (cacheService.hasCityQuality(city)) {
            airQuality = cacheService.getAirQuality(city);
            log.info("City found in cache {} with {}", city, airQuality);
            return airQuality;
        }

        log.info("{} not found in cache retrieving from NinjaAPI", city);
        airQuality = getAirQualityNinja(city);

        if (airQuality == null) {
            log.info("{} not found in NinjaAPI retrieving from OpenWeather", city);
            List<Number> coordinates = cityService.getCityCoordinates(city);
            airQuality = getAirQualityOpenWeather(coordinates.get(0).doubleValue(), coordinates.get(1).doubleValue());
        }

        if (airQuality == null) {
            log.error("Could not retrieve data for {}", city);
            return null;
        }

        log.info("Caching {} current data", airQuality);
        cacheService.addAirQuality(city, airQuality);
        return airQuality;
    }

    @Override
    public AirQuality getAirQualityNinja(String city) {
        WebClient builder = WebClient.create("https://api.api-ninjas.com/v1/airquality");

        Object requestResult = builder.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("city", city)
                        .build())
                .header("X-Api-Key", "LCLImpqPQixnfVWmCdeg1w==xfvZvOFvVmELIcXe")
                .retrieve()
                .bodyToMono(Object.class)
                .onErrorResume(e -> Mono.empty()).block();

        if (requestResult == null) return null;

        List<Number> measurements = JsonPath.read(requestResult, "$..[*].concentration");
        Integer aqi = JsonPath.read(requestResult, "$.overall_aqi");
        return new AirQuality(aqi, measurements);
    }


    @Override
    public AirQuality getAirQualityOpenWeather(Double lat , Double lon) {
        WebClient builder = WebClient.create("http://api.openweathermap.org/data/2.5/air_pollution");

        Object requestResult = builder.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("lat", "31.2047")
                        .queryParam("lon", "121.449")
                        .queryParam("appid","7d32153711277ad313bf9e6b26a5eaca")
                        .build())
                .retrieve()
                .bodyToMono(Object.class)
                .onErrorResume(e -> Mono.empty()).block();

        if (requestResult == null) return null;

        Map<String,Number> measurements = JsonPath.read(requestResult, "$.list[0].components");
        Integer aqi = JsonPath.read(requestResult, "$.list[0].main.aqi");
        return new AirQuality(aqi, measurements);

    }
}
