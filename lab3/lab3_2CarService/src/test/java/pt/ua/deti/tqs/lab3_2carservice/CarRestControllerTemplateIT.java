package pt.ua.deti.tqs.lab3_2carservice;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pt.ua.deti.tqs.lab3_2carservice.data.Car;
import pt.ua.deti.tqs.lab3_2carservice.data.CarRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class CarRestControllerTemplateIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository repository;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }


    @Test
    void whenValidInput_thenCreateCar() {
        Car bmw = new Car("BMW", "M2");
        ResponseEntity<Car> entity = restTemplate.postForEntity("/api/cars", bmw, Car.class);

        List<Car> found = repository.findAll();
        assertThat(found)
                .extracting(Car::getModel)
                .containsOnly("M2");
    }

    @Test
    void givenCars_whenGetCars_thenStatus200() {
        createTestCar("BMW", "M2");
        createTestCar("Citroen", "C3");
        createTestCar("Toyota", "Yaris");

        ResponseEntity<List<Car>> response = restTemplate
                .exchange("/api/cars", HttpMethod.GET,null, new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .hasSize(3)
                .extracting(Car::getModel)
                .containsOnly("M2", "C3", "Yaris");
    }

    private void createTestCar(String brand, String model) {
        Car car = new Car(brand, model);
        repository.saveAndFlush(car);
    }
}
