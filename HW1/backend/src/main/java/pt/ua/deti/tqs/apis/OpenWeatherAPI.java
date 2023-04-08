package pt.ua.deti.tqs.apis;

import com.jayway.jsonpath.JsonPath;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pt.ua.deti.tqs.data.AirQuality;
import pt.ua.deti.tqs.data.City;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class OpenWeatherAPI implements ApiQuality{
    @Override
    public AirQuality getQuality(City c) {
        WebClient builder = WebClient.create("http://api.openweathermap.org/data/2.5/air_pollution");

        Object requestResult = builder.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("lat", c.getLatitude())
                        .queryParam("lon", c.getLongitude())
                        .queryParam("appid","7d32153711277ad313bf9e6b26a5eaca")
                        .build())
                .retrieve()
                .bodyToMono(Object.class)
                .onErrorResume(e -> Mono.empty()).block();

        if (requestResult == null) return null;

        Map<String,Number> measurements = JsonPath.read(requestResult, "$.list[0].components");
        Integer aqi = JsonPath.read(requestResult, "$.list[0].main.aqi");
        Integer dt = JsonPath.read(requestResult, "$.list[0].dt");
        return new AirQuality(aqi, measurements, dt);
    }

    @Override
    public List<AirQuality> getForecast(City city) {
        WebClient builder = WebClient.create("http://api.openweathermap.org/data/2.5/air_pollution/forecast");

        Object requestResult = builder.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("lat", city.getLatitude())
                        .queryParam("lon", city.getLongitude())
                        .queryParam("appid","7d32153711277ad313bf9e6b26a5eaca")
                        .build())
                .retrieve()
                .bodyToMono(Object.class)
                .onErrorResume(e -> Mono.empty()).block();

        if (requestResult == null) return Collections.emptyList();

        List<Map<String,Object>> temp = JsonPath.read(requestResult, "$.list[*]");

        List <AirQuality> airQualityList = new ArrayList<>();
        for (Map<String,Object> t : temp) {
            Map<String,Number> measurements = JsonPath.read(t, "$.components");
            Integer aqi = JsonPath.read(t, "$.main.aqi");
            Integer dt = JsonPath.read(t, "$.dt");
            airQualityList.add(new AirQuality(aqi, measurements, dt));
        }

        return airQualityList;
    }

    @Override
    public String getApiName() {
        return "OpenWeatherAPI";
    }
}
