package pt.ua.deti.tqs.lab3_2carservice.boundary;

import org.springframework.http.ResponseEntity;
import pt.ua.deti.tqs.lab3_2carservice.data.Car;
import pt.ua.deti.tqs.lab3_2carservice.service.CarManagerService;

import java.util.List;

public class CarController {

    private CarManagerService carManagerService;


    public ResponseEntity<Car> createCar(Car car) {
        return null;
    }

    public List<Car> getAllCars() {
        return null;
    }

    public ResponseEntity<Car> getCarById(long id) {
        return null;
    }
}
