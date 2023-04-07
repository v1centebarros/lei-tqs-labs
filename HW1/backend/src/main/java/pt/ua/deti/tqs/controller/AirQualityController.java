package pt.ua.deti.tqs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pt.ua.deti.tqs.data.*;
import pt.ua.deti.tqs.service.CityService;
import pt.ua.deti.tqs.service.IAirQualityService;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@RequestMapping("/api")
public class AirQualityController {

    @Autowired
    private IAirQualityService airQualityService;

    @Autowired
    private CityService cityService;

    @GetMapping("/quality")
    public ResponseEntity<Object> getQuality(@RequestParam(value = "city") String city) {
        City cityData = cityService.getCity(city);

        if (cityData == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(APPLICATION_JSON)
                    .body(new ErrorDTO("It was not possible to retrieve the city data"));
        }

        AirQuality airQuality = airQualityService.getAirQuality(cityData);
        if (airQuality == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(APPLICATION_JSON)
                    .body(new ErrorDTO("It was not possible to retrieve the air quality data"));
        }

        return ResponseEntity.ok(new AirQualityDTO(cityData, airQuality));
    }

    @GetMapping("/forecast")
    public ResponseEntity<Object> getForecast(@RequestParam(value = "city") String city) {
        City cityData = cityService.getCity(city);
        if (cityData == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(APPLICATION_JSON)
                    .body(new ErrorDTO("It was not possible to retrieve the city data"));
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
                    .body(new ErrorDTO("It was not possible to retrieve the city data"));
        }

        return ResponseEntity.ok(cityData);
    }

    @GetMapping("/openweather")
    public ResponseEntity<Object> getOpenWeather(@RequestParam(value = "city") String city) {
        City cityData = cityService.getCity(city);

        if (cityData == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(APPLICATION_JSON)
                    .body(new ErrorDTO("It was not possible to retrieve the city data"));
        }

        AirQuality airQuality = airQualityService.getAirQualityOpenWeather(cityData.getLatitude(), cityData.getLongitude());

        if (airQuality == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(APPLICATION_JSON)
                    .body(new ErrorDTO("It was not possible to retrieve the air quality data"));
        }
        return ResponseEntity.ok(new AirQualityDTO(cityData, airQuality));
    }

    @GetMapping("/ninja")
    public ResponseEntity<Object> getNinja(@RequestParam(value = "city") String city) {
        AirQuality airQuality = airQualityService.getAirQualityNinja(city);

        if (airQuality == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(APPLICATION_JSON)
                    .body(new ErrorDTO("It was not possible to retrieve the air quality data"));
        }

        return ResponseEntity.ok(new AirQualityDTO(cityService.getCity(city), airQuality));
    }
}
