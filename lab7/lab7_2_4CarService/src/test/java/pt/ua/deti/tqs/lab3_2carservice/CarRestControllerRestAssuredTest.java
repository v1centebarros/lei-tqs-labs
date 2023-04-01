package pt.ua.deti.tqs.lab3_2carservice;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pt.ua.deti.tqs.lab3_2carservice.boundary.CarController;
import pt.ua.deti.tqs.lab3_2carservice.data.Car;
import pt.ua.deti.tqs.lab3_2carservice.service.CarManagerService;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@WebMvcTest(CarController.class)
class CarRestControllerRestAssuredTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService service;

    @BeforeEach
    void setUp() {

        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    void givenCars_whenGetCars_thenStatus200() {
        Car car = new Car("Audi", "A3");
        when(service.createCar(Mockito.any())).thenReturn(car);

        RestAssuredMockMvc.when()
                .get("/api/cars")
                .then()
                .statusCode(200);
    }

    @Test
    void givenCar_whenGetCarById_thenStatus200() {
        Car car = new Car("Audi", "A3");
        when(service.getCarDetails(Mockito.anyLong())).thenReturn(java.util.Optional.of(car));
        RestAssuredMockMvc.when()
                .get("/api/cars/1")
                .then()
                .statusCode(200)
                .assertThat()
                .body("maker", is(car.getMaker()));
    }

    @Test
    void givenCar_whenGetCarById_thenStatus404() {
        RestAssuredMockMvc.when()
                .get("/api/cars/2")
                .then()
                .statusCode(404);
    }

    @Test
    void givenCar_whenCreateCar_thenStatus201() {
        Car car = new Car("Audi", "A3");
        when(service.createCar(Mockito.any())).thenReturn(car);

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(car)
                .when()
                .post("/api/cars")
                .then()
                .statusCode(201);
    }


}
