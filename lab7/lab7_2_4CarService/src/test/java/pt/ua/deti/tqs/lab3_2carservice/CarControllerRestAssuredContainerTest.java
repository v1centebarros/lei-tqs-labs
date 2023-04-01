package pt.ua.deti.tqs.lab3_2carservice;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pt.ua.deti.tqs.lab3_2carservice.data.Car;
import pt.ua.deti.tqs.lab3_2carservice.data.CarRepository;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create")
public class CarControllerRestAssuredContainerTest {

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:13.2")
            .withUsername("postgres")
            .withPassword("postgres")
            .withDatabaseName("cars");

    @LocalServerPort
    int randomServerPort;
    Car car1, car2;

    @Autowired
    private CarRepository repository;


    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @BeforeEach
    void setup() {
        car1 = repository.save(new Car("BMW", "M2"));
        car2 = repository.save(new Car("Audi", "A3"));
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void givenCars_whenGetCars_thenStatus200() {

        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(randomServerPort)
                .path("/api/cars")
                .build()
                .toUriString();


        given()
                .when()
                .get(endpoint)
                .then()
                .statusCode(200);
    }

    @Test
    void givenCar_whenGetCarById_thenStatus200() {
        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(randomServerPort)
                .path("/api/cars/{id}")
                .buildAndExpand(car1.getCarId())
                .toUriString();

        given()
                .when()
                .get(endpoint)
                .then()
                .statusCode(200)
                .assertThat()
                .body("maker", is(car1.getMaker()));
    }

    @Test
    void givenCar_whenGetCarById_thenStatus404() {
        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(randomServerPort)
                .path("/api/cars/{id}")
                .buildAndExpand(2)
                .toUriString();

        given()
                .when()
                .get(endpoint)
                .then()
                .statusCode(404);
    }

    @Test
    void givenCar_whenPostCar_thenStatus201() {
        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(randomServerPort)
                .path("/api/cars")
                .build()
                .toUriString();

        Car car = new Car("Audi", "A4");
        try {
            given()
                    .contentType("application/json")
                    .body(JsonUtils.toJson(car))
                    .when()
                    .post(endpoint)
                    .then()
                    .statusCode(201);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
