package pt.ua.deti.tqs.lab3_2carservice.data;

import java.util.List;

public interface CarRepository {
    Car findByCarId(long id);
    List<Car> findAll();
}
