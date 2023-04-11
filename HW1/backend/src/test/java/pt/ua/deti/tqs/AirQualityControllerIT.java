package pt.ua.deti.tqs;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

class AirQualityControllerIT {
    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    void whenGetQualityFromCityThenReturnsAirQuality() {

        RestAssured.when()
                .get("/api/quality?city=Porto")
                .then()
                .statusCode(200)
                .body("city.name", equalTo("porto"))
                .body("city.displayName", is("Porto, Portugal"))
                .body("city.latitude", is(41.149452F))
                .body("city.longitude", is(-8.610788F))
                .body("data.aqi", is(instanceOf(Number.class)))
                .body("data.pm10", is(instanceOf(Number.class)))
                .body("data.pm25", is(instanceOf(Number.class)))
                .body("data.no2", is(instanceOf(Number.class)))
                .body("data.so2", is(instanceOf(Number.class)))
                .body("data.o3", is(instanceOf(Number.class)))
                .body("data.co", is(instanceOf(Number.class)));
    }

    @Test
    void whenGetQualityFromInvalidCityThenReturnsError() {
        RestAssured.when()
                .get("/api/quality?city=asdsadsa")
                .then()
                .statusCode(200)
                .body("message", is("It was not possible to retrieve the city data"))
                .body("data", is(nullValue()))
                .body("city", is(nullValue()));
    }

    @Test
    void whenGetForecastFromCityThenReturnsAirQuality() {
        RestAssured.when()
                .get("/api/forecast?city=Porto")
                .then()
                .statusCode(200)
                .body("city.name", equalTo("porto"))
                .body("city.displayName", is("Porto, Portugal"))
                .body("city.latitude", is(41.149452F))
                .body("city.longitude", is(-8.610788F))
                .body("data", hasSize(96))
                .body("data[0].aqi", is(instanceOf(Number.class)))
                .body("data[0].pm10", is(instanceOf(Number.class)))
                .body("data[0].pm25", is(instanceOf(Number.class)))
                .body("data[0].no2", is(instanceOf(Number.class)))
                .body("data[0].so2", is(instanceOf(Number.class)))
                .body("data[0].o3", is(instanceOf(Number.class)))
                .body("data[0].co", is(instanceOf(Number.class)));
    }

    @Test
    void whenGetForecastFromInvalidCityThenReturnsError() {
        RestAssured.when()
                .get("/api/forecast?city=asdsadsa")
                .then()
                .statusCode(200)
                .body("message", is("It was not possible to retrieve the city data"))
                .body("data", is(nullValue()))
                .body("city", is(nullValue()));
    }

    @Test
    void whenGetCityFromCityThenReturnsCity() {
        RestAssured.when()
                .get("/api/city?city=Porto")
                .then()
                .statusCode(200)
                .body("name", equalTo("porto"))
                .body("displayName", is("Porto, Portugal"))
                .body("latitude", is(41.149452F))
                .body("longitude", is(-8.610788F));
    }

    @Test
    void whenGetCityFromInvalidCityThenReturnsError() {
        RestAssured.when()
                .get("/api/city?city=asdsadsa")
                .then()
                .statusCode(200)
                .body("message", is("It was not possible to retrieve the city data"))
                .body("data", is(nullValue()))
                .body("city", is(nullValue()));
    }

    @Test
    void whenGetAirQualityFromNinjaThenReturnsAirQuality() {
        RestAssured.when()
                .get("/api/ninja?city=Porto")
                .then()
                .statusCode(200)
                .body("city.name", equalTo("porto"))
                .body("city.displayName", is("Porto, Portugal"))
                .body("city.latitude", is(41.149452F))
                .body("city.longitude", is(-8.610788F))
                .body("data.aqi", is(instanceOf(Number.class)))
                .body("data.pm10", is(instanceOf(Number.class)))
                .body("data.pm25", is(instanceOf(Number.class)))
                .body("data.no2", is(instanceOf(Number.class)))
                .body("data.so2", is(instanceOf(Number.class)))
                .body("data.o3", is(instanceOf(Number.class)))
                .body("data.co", is(instanceOf(Number.class)));
    }

    @Test
    void whenGetAirQualityFromNinjaInvalidCityThenReturnsError() {
        RestAssured.when()
                .get("/api/ninja?city=asdsadsa")
                .then()
                .statusCode(200)
                .body("message", is("It was not possible to retrieve the city data"))
                .body("data", is(nullValue()))
                .body("city", is(nullValue()));
    }

    @Test
    void whenGetAirQualityFromOpenWeatherThenReturnsAirQuality() {
        RestAssured.when()
                .get("/api/openweather?city=Porto")
                .then()
                .statusCode(200)
                .body("city.name", equalTo("porto"))
                .body("city.displayName", is("Porto, Portugal"))
                .body("city.latitude", is(41.149452F))
                .body("city.longitude", is(-8.610788F))
                .body("data.aqi", is(instanceOf(Number.class)))
                .body("data.pm10", is(instanceOf(Number.class)))
                .body("data.pm25", is(instanceOf(Number.class)))
                .body("data.no2", is(instanceOf(Number.class)))
                .body("data.so2", is(instanceOf(Number.class)))
                .body("data.o3", is(instanceOf(Number.class)))
                .body("data.co", is(instanceOf(Number.class)));
    }

    @Test
    void whenGetAirQualityFromOpenWeatherInvalidCityThenReturnsError() {
        RestAssured.when()
                .get("/api/openweather?city=asdsadsa")
                .then()
                .statusCode(200)
                .body("message", is("It was not possible to retrieve the city data"))
                .body("data", is(nullValue()))
                .body("city", is(nullValue()));
    }
}
