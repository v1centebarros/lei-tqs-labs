package pt.ua.deti.tqs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ua.deti.tqs.cache.LocalCache;
import pt.ua.deti.tqs.data.AirQuality;
import pt.ua.deti.tqs.service.AirQualityService;

import static org.springframework.http.MediaType.APPLICATION_JSON;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping("/api")
public class AirQualityController {

    @Autowired
    private AirQualityService service;

    @Autowired
    private LocalCache<String, AirQuality> localCache;

    static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @GetMapping("/quality")
    public ResponseEntity<Object> getFact(@RequestParam(value = "city") String city) {

        if (localCache.containsKey(city)) {
            AirQuality airQuality = localCache.get(city);
            log.info("City found in cache {} with {}", city, airQuality);
           return ResponseEntity.ok(airQuality);
        }
        AirQuality airQuality = service.getAirQuality(city);
        log.info("Data retrieved from API {}", airQuality);

        if (airQuality == null) {
            log.error("City not found");
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(APPLICATION_JSON)
                    .body("{\"error\": \"City not found\"}");
        }

        localCache.put(city, airQuality);
        log.info("Caching city {}", city);
        return ResponseEntity.ok(airQuality);
    }

}
