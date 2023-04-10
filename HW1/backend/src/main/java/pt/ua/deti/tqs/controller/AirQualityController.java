package pt.ua.deti.tqs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ua.deti.tqs.data.*;
import pt.ua.deti.tqs.service.AirQualityService;
import pt.ua.deti.tqs.service.CityService;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
//@CrossOrigin
@RequestMapping("/api")
public class AirQualityController {

    @Autowired
    private AirQualityService airQualityService;

    @Autowired
    private CityService cityService;

    private static final ErrorDTO AQ_ERROR = new ErrorDTO("It was not possible to retrieve the air quality data");

    private static final ErrorDTO CITY_ERROR = new ErrorDTO("It was not possible to retrieve the city data");

    @GetMapping("/quality")
    public ResponseEntity<Object> getQuality(@RequestParam(value = "city") String city) {
        City cityData = cityService.getCity(city);

        if (cityData == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(APPLICATION_JSON)
                    .body(CITY_ERROR);
        }

        AirQuality airQuality = airQualityService.getAirQuality(cityData);
        if (airQuality == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(APPLICATION_JSON)
                    .body(AQ_ERROR);
        }

        return ResponseEntity.ok(new AirQualityDTO(cityData, airQuality));
    }

    @GetMapping("/forecast")
    public ResponseEntity<Object> getForecast(@RequestParam(value = "city") String city) {
        City cityData = cityService.getCity(city);
        if (cityData == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(APPLICATION_JSON)
                    .body(CITY_ERROR);
        }
        List<AirQuality> airQualityForecast = airQualityService.getAirQualityForecast(cityData);

        if (airQualityForecast == null || airQualityForecast.equals(Collections.emptyList())){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(APPLICATION_JSON)
                    .body(new ErrorDTO("It was not possible to retrieve the forecast data"));
        }

        return ResponseEntity.ok(new ForecastDTO(cityData, airQualityForecast));
    }

    @GetMapping("/city")
    public ResponseEntity<Object> getCity(@RequestParam(value = "city") String city) {
        City cityData = cityService.getCity(city);

        if (cityData == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(APPLICATION_JSON)
                    .body(CITY_ERROR);
        }

        return ResponseEntity.ok(cityData);
    }

    @GetMapping("/openweather")
    public ResponseEntity<Object> getOpenWeather(@RequestParam(value = "city") String city) {
        City cityData = cityService.getCity(city);

        if (cityData == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(APPLICATION_JSON)
                    .body(CITY_ERROR);
        }

        AirQuality airQuality = airQualityService.getFromOpenWeather(cityData);

        if (airQuality == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(APPLICATION_JSON)
                    .body(AQ_ERROR);
        }
        return ResponseEntity.ok(new AirQualityDTO(cityData, airQuality));
    }

    @GetMapping("/ninja")
    public ResponseEntity<Object> getNinja(@RequestParam(value = "city") String city) {

        City cityData = cityService.getCity(city);

        if (cityData == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(APPLICATION_JSON)
                    .body(CITY_ERROR);
        }

        AirQuality airQuality = airQualityService.getFromNinja(cityData);

        if (airQuality == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(APPLICATION_JSON)
                    .body(AQ_ERROR);
        }

        return ResponseEntity.ok(new AirQualityDTO(cityData, airQuality));
    }
}
