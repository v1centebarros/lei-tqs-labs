package pt.ua.deti.tqs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ua.deti.tqs.apis.ApiQuality;
import pt.ua.deti.tqs.apis.NinjaAPI;
import pt.ua.deti.tqs.data.AirQuality;
import pt.ua.deti.tqs.data.City;

import static org.assertj.core.api.Assertions.assertThat;

class NinjaAPIUnitTests {

    private ApiQuality ninjaAPI;

    private final City aveiro = new City("aveiro", 40.640496, -8.6537841,"Aveiro, Portugal" );
    @BeforeEach
    void setup() {
        ninjaAPI = new NinjaAPI();
    }

    @Test
    void testFetchAirQualityFromValidCity() {

        AirQuality aq = ninjaAPI.getQuality(aveiro);
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
        City c = new City("asdsddd", 0, 0, "asdsddd");
        AirQuality aq = ninjaAPI.getQuality(c);
        assertThat(aq).isNull();
    }

    @Test
    void testFetchGetForecast() {
        assertThat(ninjaAPI.getForecast(aveiro)).isEmpty();
    }




}
