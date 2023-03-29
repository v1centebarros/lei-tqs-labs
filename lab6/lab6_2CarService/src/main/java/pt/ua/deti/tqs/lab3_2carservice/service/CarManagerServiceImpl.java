package pt.ua.deti.tqs.lab3_2carservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.deti.tqs.lab3_2carservice.data.Car;
import pt.ua.deti.tqs.lab3_2carservice.data.CarRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarManagerServiceImpl implements CarManagerService {

    @Autowired
    private CarRepository carRepository;

    @Override
    public Car createCar(Car car) {
        return this.carRepository.save(car);
    }

    @Override
    public List<Car> getAllCars() {
        return this.carRepository.findAll();
    }

    @Override
    public Optional<Car> getCarDetails(long id) {
        return Optional.ofNullable(this.carRepository.findByCarId(id));
    }
}


