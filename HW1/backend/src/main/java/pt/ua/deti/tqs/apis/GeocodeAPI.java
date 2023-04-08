package pt.ua.deti.tqs.apis;

import com.jayway.jsonpath.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pt.ua.deti.tqs.data.City;

import java.lang.invoke.MethodHandles;

@Service
public class GeocodeAPI implements GeoAPI{

    private static final String BASE_URL = "https://geocode.maps.co/search";

    static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
}
