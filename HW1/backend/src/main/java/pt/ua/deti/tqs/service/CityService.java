package pt.ua.deti.tqs.service;

import com.jayway.jsonpath.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pt.ua.deti.tqs.data.City;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

@Service
public class CityService {

    static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String BASE_URL = "https://geocode.maps.co/search";

    private final Map<String, City> cities = new HashMap<>();

    public City fetchCity(String city) {
        WebClient builder = WebClient.create(BASE_URL);
        Object requestResult = builder.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("city", city)
                        .build())
                .retrieve()
                .bodyToMono(Object.class).block();

        if (requestResult != null && requestResult.toString().equals("[]")) {
            return null;
        }

        String displayName = JsonPath.read(requestResult, "$.[0].display_name");
        double lat = Double.parseDouble(JsonPath.read(requestResult, "$.[0].lat"));
        double lon = Double.parseDouble(JsonPath.read(requestResult, "$.[0].lon"));

        City c = new City(city, lat, lon, displayName);
        log.info("City {} found with coordinates {}", city, c);
        return c;
    }

    public City getCity(String city) {
        city = city.trim().toLowerCase();
        log.info("Requesting city {} from Cache", city);
        if (cities.containsKey(city)) {
            log.info("City found in cache {}", city);
            return cities.get(city);
        }

        log.info("{} not found in cache retrieving from Geocode Maps", city);
        City cityData = fetchCity(city);
        if (cityData == null) {
            return null;
        }

        log.info("Caching {} current data", cityData);
        cities.put(city, cityData);
        return cityData;
    }
}