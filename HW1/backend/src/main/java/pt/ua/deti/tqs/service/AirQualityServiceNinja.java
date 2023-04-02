package pt.ua.deti.tqs.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pt.ua.deti.tqs.data.AirQuality;
import reactor.core.publisher.Mono;

@Service
public class AirQualityServiceNinja implements AirQualityService{
    private static final String BASE_URL = "https://api.api-ninjas.com/v1/airquality";
    private static final String APP_ID = "LCLImpqPQixnfVWmCdeg1w==xfvZvOFvVmELIcXe";
    @Override
    public AirQuality getAirQuality(String city) {
        WebClient builder = WebClient.create(BASE_URL);

        Mono<AirQuality> requestResult = builder.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("city", city)
                        .build())
                .header("X-Api-Key", APP_ID)
                .retrieve()
                .bodyToMono(AirQuality.class)
                .onErrorResume(e -> Mono.empty());

        return requestResult.block();
    }
}
