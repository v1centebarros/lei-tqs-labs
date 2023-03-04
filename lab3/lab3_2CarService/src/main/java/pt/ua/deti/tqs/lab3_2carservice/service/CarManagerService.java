package pt.ua.deti.tqs.lab3_2carservice.service;

import pt.ua.deti.tqs.lab3_2carservice.data.Car;
import java.util.List;
import java.util.Optional;

public interface CarManagerService {

    Car createCar(Car car);

    List<Car> getAllCars();

    Optional<Car> getCarDetails(long id);
}
