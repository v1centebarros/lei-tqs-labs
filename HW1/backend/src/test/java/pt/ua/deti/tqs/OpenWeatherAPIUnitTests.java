package pt.ua.deti.tqs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ua.deti.tqs.apis.ApiQuality;
import pt.ua.deti.tqs.apis.OpenWeatherAPI;
import pt.ua.deti.tqs.data.AirQuality;
import pt.ua.deti.tqs.data.City;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OpenWeatherAPIUnitTests {

    private ApiQuality api;

    private final City c = new City("aveiro", 40.640496, -8.6537841,"Aveiro, Portugal" );

    @BeforeEach
    void setup() {
        api = new OpenWeatherAPI();
    }

    @Test
    void testFetchAirQualityFromValidCity() {

        AirQuality aq = api.getQuality(c);
        assertThat(aq).isNotNull();
        assertThat(aq.getAqi()).isInstanceOf(Integer.class);
        assertThat(aq.getCo()).isInstanceOf(Number.class);
        assertThat(aq.getNo2()).isInstanceOf(Number.class);
        assertThat(aq.getO3()).isInstanceOf(Number.class);
        assertThat(aq.getPm10()).isInstanceOf(Number.class);
        assertThat(aq.getPm25()).isInstanceOf(Number.class);
        assertThat(aq.getSo2()).isInstanceOf(Number.class);
    }

    @Test
    void testFetchAirQualityFromInvalidCity() {
        City c1 = new City("asdsadsa", -1324344, -122322324, "asdsadsa");
        AirQuality aq = api.getQuality(c1);
        assertThat(aq).isNull();
    }

    @Test
    void testFetchGetForecast() {
        List<AirQuality> forecast = api.getForecast(c);

        assertThat(forecast).hasSize(95);
        assertThat(forecast.get(0).getAqi()).isInstanceOf(Integer.class);
        assertThat(forecast.get(0).getCo()).isInstanceOf(Number.class);
        assertThat(forecast.get(0).getNo2()).isInstanceOf(Number.class);
        assertThat(forecast.get(0).getO3()).isInstanceOf(Number.class);
        assertThat(forecast.get(0).getPm10()).isInstanceOf(Number.class);
        assertThat(forecast.get(0).getPm25()).isInstanceOf(Number.class);
        assertThat(forecast.get(0).getSo2()).isInstanceOf(Number.class);
    }

    @Test
    void testFetchGetForecastFromInvalidCity() {
        City c1 = new City("asdsadsa", -1324344, -122322324, "asdsadsa");
        List<AirQuality> forecast = api.getForecast(c1);
        assertThat(forecast).isEmpty();
    }

}
