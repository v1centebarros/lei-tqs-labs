package pt.ua.deti.tqs.apis;

import com.jayway.jsonpath.JsonPath;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pt.ua.deti.tqs.data.AirQuality;
import pt.ua.deti.tqs.data.City;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
public class NinjaAPI implements ApiQuality {
    @Override
    public AirQuality getQuality(City city) {
        WebClient builder = WebClient.create("https://api.api-ninjas.com/v1/airquality");

        Object requestResult = builder.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("city", city.getName())
                        .build())
                .header("X-Api-Key", "LCLImpqPQixnfVWmCdeg1w==xfvZvOFvVmELIcXe")
                .retrieve()
                .bodyToMono(Object.class)
                .onErrorResume(e -> Mono.empty()).block();

        if (requestResult == null) return null;

        List<Number> measurements = JsonPath.read(requestResult, "$..[*].concentration");
        Integer aqi = JsonPath.read(requestResult, "$.overall_aqi");
        return new AirQuality(aqi, measurements);
    }

    @Override
    public List<AirQuality> getForecast(City city) {
        return Collections.emptyList();
    }

    @Override
    public String getApiName() {
        return "NinjaAPI";
    }

}
