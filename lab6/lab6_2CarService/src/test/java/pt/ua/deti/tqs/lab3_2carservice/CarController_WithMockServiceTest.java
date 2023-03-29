package pt.ua.deti.tqs.lab3_2carservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.ua.deti.tqs.lab3_2carservice.boundary.CarController;
import pt.ua.deti.tqs.lab3_2carservice.data.Car;
import pt.ua.deti.tqs.lab3_2carservice.service.CarManagerService;

import java.util.List;

import static org.mockito.Mockito.when;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
public class CarController_WithMockServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService service;

    @BeforeEach
    public void setUp() {
    }

    @Test
    void whenPostCar_thenCreateCar() throws Exception {
        Car car = new Car("Audi", "A3");

        when(service.createCar(Mockito.any())).thenReturn(car);

        mvc.perform(post("/api/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(car)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.maker", is(car.getMaker())));

        verify(service, times(1)).createCar(Mockito.any());
    }

    @Test
     void whenGetCarById_thenReturnJsonCar() throws Exception {
        Car car = new Car("Audi", "A3");

        when(service.getCarDetails(Mockito.anyLong())).thenReturn(java.util.Optional.of(car));

        mvc.perform(get("/api/cars/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maker", is(car.getMaker())));

        verify(service, times(1)).getCarDetails(Mockito.anyLong());
    }

    @Test
    void givenManyCars_whenGetCars_thenReturnJsonArray() throws Exception {
        Car car1 = new Car("Audi", "A3");
        Car car2 = new Car("BMW", "M3");
        Car car3 = new Car("Mercedes", "C200");

        when(service.getAllCars()).thenReturn(List.of(car1, car2, car3));

        mvc.perform(get("/api/cars")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].maker", is(car1.getMaker())))
                .andExpect(jsonPath("$[1].maker", is(car2.getMaker())))
                .andExpect(jsonPath("$[2].maker", is(car3.getMaker())));

        verify(service, times(1)).getAllCars();
    }

}
