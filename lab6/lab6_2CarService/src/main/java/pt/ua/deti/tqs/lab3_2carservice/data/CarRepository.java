package pt.ua.deti.tqs.lab3_2carservice.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    Car findByCarId(long id);
    List<Car> findAll();
}
