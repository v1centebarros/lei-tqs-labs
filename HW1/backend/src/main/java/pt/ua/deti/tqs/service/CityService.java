package pt.ua.deti.tqs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pt.ua.deti.tqs.data.City;
import reactor.core.publisher.Mono;

import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CityService {

    static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String BASE_URL = "https://api.api-ninjas.com/v1/geocoding";
    private static final String APP_ID = "LCLImpqPQixnfVWmCdeg1w==xfvZvOFvVmELIcXe";
    private final Map<String, City> cities = new HashMap<>();

    public City fetchCity(String city) {
        WebClient builder = WebClient.create(BASE_URL);

        List<City> requestResult = builder.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("city", city)
                        .build())
                .header("X-Api-Key", APP_ID)
                .retrieve()
                .toEntityList(City.class)
                .mapNotNull(HttpEntity::getBody)
                .onErrorResume(e -> Mono.empty()).block();

        if (requestResult == null || requestResult.isEmpty()) {
            return null;
        }
        return requestResult.get(0);
    }

    public City getCity(String city) {
        log.info("Requesting city {} from Cache", city);
        if (cities.containsKey(city)) {
            return cities.get(city);
        }

        log.info("{} not found in cache retrieving from NinjaAPI", city);
        City cityData = fetchCity(city);
        if (cityData == null) {
            return null;
        }

        log.info("Caching {} current data", cityData);
        cities.put(city, cityData);
        return cityData;
    }

    public List<Number> getCityCoordinates(String city) {
        City cityData = getCity(city);
        if (cityData == null) {
            return Collections.emptyList();
        }
        return cityData.getCoordinates();

    }
}