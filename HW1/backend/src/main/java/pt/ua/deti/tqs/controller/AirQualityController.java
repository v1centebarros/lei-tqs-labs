package pt.ua.deti.tqs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pt.ua.deti.tqs.data.AirQuality;
import pt.ua.deti.tqs.service.CityService;
import pt.ua.deti.tqs.service.IAirQualityService;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@RequestMapping("/api")
public class AirQualityController {

    @Autowired
    private IAirQualityService airQualityService;

    @Autowired
    private CityService cityService;

    private static final String ERROR_MSG = "{\"error\": \"Could not find data\"}";

    @GetMapping("/quality")
    public ResponseEntity<Object> getQuality(@RequestParam(value = "city") String city) {
        AirQuality airQuality = airQualityService.getAirQuality(city.trim().toLowerCase());

        if (airQuality == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(APPLICATION_JSON)
                    .body(ERROR_MSG);
        }

        return ResponseEntity.ok(airQuality);
    }

    @GetMapping("/city")
    public ResponseEntity<Object> getCity(@RequestParam(value = "city") String city) {
        if (city == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(APPLICATION_JSON)
                    .body(ERROR_MSG);
        }
        return ResponseEntity.ok(cityService.getCity(city));
    }


    @GetMapping("/openweather")
    public ResponseEntity<Object> getOpenWeather(@RequestParam(value = "lat") Double lat, @RequestParam(value = "lon") Double lon) {
        if (lat == null || lon == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(APPLICATION_JSON)
                    .body(ERROR_MSG);
        }
        return ResponseEntity.ok(airQualityService.getAirQualityOpenWeather(lat, lon));
    }

    @GetMapping("/forecast")
    public ResponseEntity<Object> getForecast(@RequestParam(value = "city") String city) {
        if (city == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(APPLICATION_JSON)
                    .body(ERROR_MSG);
        }
        return ResponseEntity.ok(airQualityService.getAirQualityForecast(city));
    }

}
