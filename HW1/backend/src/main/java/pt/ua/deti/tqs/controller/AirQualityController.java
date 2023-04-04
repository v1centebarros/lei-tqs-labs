package pt.ua.deti.tqs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ua.deti.tqs.data.AirQuality;
import pt.ua.deti.tqs.data.City;
import pt.ua.deti.tqs.service.AirQualityService;

import static org.springframework.http.MediaType.APPLICATION_JSON;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.ua.deti.tqs.service.CacheService;
import pt.ua.deti.tqs.service.CityService;

import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping("/api")
public class AirQualityController {

    @Autowired
    private AirQualityService airQualityService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CacheService cacheService;

    static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @GetMapping("/quality")
    public ResponseEntity<Object> getQuality(@RequestParam(value = "city") String city) {
        city = city.toLowerCase();
        log.info("Requesting city {}", city);
        log.info("Cached cities {}", cacheService.getCities());
        AirQuality airQuality;

        if (cacheService.hasCityQuality(city)) {
            airQuality = cacheService.getAirQuality(city);
            log.info("City found in cache {} with {}", city, airQuality);
            return ResponseEntity.ok(airQuality);
        }

        log.info("{} not found in cache retrieving from NinjaAPI", city);
        airQuality = airQualityService.getAirQuality(city);

        if (airQuality == null) {
            log.error("City not found");
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(APPLICATION_JSON)
                    .body("{\"error\": \"City not found\"}");
        }
        log.info("Data retrieved from NinjaAPI {}", airQuality);

        log.info("Caching city {}", city);
        cacheService.addAirQuality(city, airQuality);


        log.info("Cache {}'s data", city);
        if (!cacheService.hasCity(city)) {
            log.info("City not found in cache");
            City cityData = cityService.getCity(city);
            if (cityData != null) {
                log.info("Caching city {}", city);
                cacheService.addCity(city, cityData);
            } else {
                log.error("Failed to cache city {}", city);
            }
        }
        return ResponseEntity.ok(airQuality);
    }
    @GetMapping("/city")
    public ResponseEntity<Object> getCity(@RequestParam(value = "city") String city) {
        return ResponseEntity.ok(cityService.getCity(city));
    }


}
