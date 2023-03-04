package pt.ua.deti.tqs.lab3_2carservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.deti.tqs.lab3_2carservice.data.Car;

import java.util.List;
import java.util.Optional;

@Service
public class CarManagerServiceImpl implements CarManagerService {

    @Autowired
    private CarManagerService carManagerService;

    @Override
    public Car createCar(Car car) {
        return null;
    }

    @Override
    public List<Car> getAllCars() {
        return null;
    }

    @Override
    public Optional<Car> getCarDetails(long id) {
        return Optional.empty();
    }
}


