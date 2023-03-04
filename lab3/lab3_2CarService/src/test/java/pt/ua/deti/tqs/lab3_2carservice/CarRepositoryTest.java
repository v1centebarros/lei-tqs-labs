package pt.ua.deti.tqs.lab3_2carservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pt.ua.deti.tqs.lab3_2carservice.data.Car;
import pt.ua.deti.tqs.lab3_2carservice.data.CarRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CarRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;


    @Test
    void whenFindM2ById_thenReturnM2Car() {
        Car m2 = new Car("BMW", "M2");
        entityManager.persistAndFlush(m2);

        Car found = carRepository.findByCarId(m2.getCarId());
        assertThat(found).isEqualTo(m2);
    }

    @Test
    void whenInvalidCarId_thenReturnNull() {
        Car fromDb = carRepository.findByCarId(-11L);
        assertThat(fromDb).isNull();
    }

    @Test
    void givenSetOfCars_whenFindAll_thenReturnAllCars() {
        Car m2 = new Car("BMW", "M2");
        Car m3 = new Car("Citroen", "C3");
        Car m4 = new Car("Toyota", "Yaris");
        entityManager.persist(m2);
        entityManager.persist(m3);
        entityManager.persist(m4);
        entityManager.flush();

        List<Car> allCars = carRepository.findAll();

        assertThat(allCars)
                .hasSize(3)
                .extracting(Car::getModel)
                .containsOnly(m2.getModel(), m3.getModel(), m4.getModel());
    }
}