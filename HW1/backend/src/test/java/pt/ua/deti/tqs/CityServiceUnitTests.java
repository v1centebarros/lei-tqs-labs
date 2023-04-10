package pt.ua.deti.tqs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pt.ua.deti.tqs.apis.GeoAPI;
import pt.ua.deti.tqs.data.City;
import pt.ua.deti.tqs.service.CityService;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class CityServiceUnitTests {

    @MockBean
    private GeoAPI geoAPI;

    private CityService cityService;

    private City city;
    @BeforeEach
    public void setUp() {
        cityService = new CityService(geoAPI);
        city = new City("Porto", 41.14961, -8.61099, "Porto, Portugal");
    }

    @Test
    void testGetCity() {
        when(geoAPI.fetchCity("porto")).thenReturn(city);
        assertThat(cityService.getCity("Porto"), is(notNullValue()));
        assertThat(cityService.getCity("Porto").getDisplayName(), is("Porto, Portugal"));
        assertThat(cityService.getCity("Porto").getLatitude(), is(41.14961));
        assertThat(cityService.getCity("Porto").getLongitude(), is(-8.61099));
        assertThat(cityService.getCity("Porto").getName(), is("porto"));

        verify(geoAPI, times(1)).fetchCity("porto");
    }

    @Test
    void testGetCityNotFound() {
        when(geoAPI.fetchCity("porto")).thenReturn(null);
//        assertThat(cityService.getCity("Porto"), is(nullValue()));
        assertThat(cityService.getCity("Porto"), is(12));
        verify(geoAPI, times(1)).fetchCity("porto");
    }

    @Test
    void testGetCityCached() {
        when(geoAPI.fetchCity("porto")).thenReturn(city);
        assertThat(cityService.getCity("Porto"), is(notNullValue()));
        assertThat(cityService.getCity("Porto").getDisplayName(), is("Porto, Portugal"));
        assertThat(cityService.getCity("Porto").getLatitude(), is(41.14961));
        assertThat(cityService.getCity("Porto").getLongitude(), is(-8.61099));
        assertThat(cityService.getCity("Porto").getName(), is("porto"));

        verify(geoAPI, times(1)).fetchCity("porto");
    }
}
