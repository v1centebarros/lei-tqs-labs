package pt.ua.deti.tqs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ua.deti.tqs.apis.GeoAPI;
import pt.ua.deti.tqs.apis.GeocodeAPI;
import pt.ua.deti.tqs.data.City;

import static org.assertj.core.api.Assertions.assertThat;

class GeocodeAPIUnitTests {

    private GeoAPI geoAPI;

    @BeforeEach
    void setup() {
        geoAPI = new GeocodeAPI();
    }
    @Test
    void testFetchCityFromValidCity() {
        City c = geoAPI.fetchCity("Aveiro");
        assertThat(c).isNotNull();
        assertThat(c.getName()).isEqualTo("aveiro");
        assertThat(c.getDisplayName()).isEqualTo("Aveiro, Portugal");
        assertThat(c.getLatitude()).isEqualTo(40.640496);
        assertThat(c.getLongitude()).isEqualTo(-8.6537841);

    }

    @Test
    void testFetchCityFromInvalidCity() {
        City c = geoAPI.fetchCity("asdsddd");
        assertThat(c).isNull();
    }
}
