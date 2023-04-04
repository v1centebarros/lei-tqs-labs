package pt.ua.deti.tqs.service;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pt.ua.deti.tqs.data.City;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CityService {

    private static final String BASE_URL = "https://api.api-ninjas.com/v1/geocoding";

    private static final String APP_ID = "LCLImpqPQixnfVWmCdeg1w==xfvZvOFvVmELIcXe";


    public City getCity(String city) {
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
}