package pt.ua.deti.tqs.lab3_2carservice;


import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create")
public class CarREstControllerRestAssuredTestIT {

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:13.2")
            .withDatabaseName("cars")
            .withUsername("postgres")
            .withPassword("postgres");

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

    @Test
    void contextLoads() {
    }

//    @Test
//    void givenCars_whenGetCars_thenStatus200() {
//
//        String endpoint = UriComponentsBuilder.newInstance()
//                .scheme("http")
//                .host("localhost")
//                .port(randomServerPort)
//                .path("/api/cars")
//                .build()
//                .toUriString();
//
//
//        given()
//                .when()
//                .get("/api/cars")
//                .then()
//                .statusCode(200);
//    }

}
