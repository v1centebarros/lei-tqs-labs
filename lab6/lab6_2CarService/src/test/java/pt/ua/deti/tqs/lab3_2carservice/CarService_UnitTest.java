package pt.ua.deti.tqs.lab3_2carservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.ua.deti.tqs.lab3_2carservice.data.Car;
import pt.ua.deti.tqs.lab3_2carservice.data.CarRepository;
import pt.ua.deti.tqs.lab3_2carservice.service.CarManagerServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CarService_UnitTest {

    @Mock(lenient = true)
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerServiceImpl employeeService;

    @BeforeEach
    public void setUp() {
        Car car = new Car("Audi", "A3");
        car.setCarId(12L);

        Car car2 = new Car("Citroen", "C3");
        Car car3 = new Car("BMW", "M3");

        List<Car> cars = List.of(car, car2, car3);

        Mockito.when(carRepository.findByCarId(car.getCarId())).thenReturn(car);
        Mockito.when(carRepository.findByCarId(car2.getCarId())).thenReturn(car2);
        Mockito.when(carRepository.findAll()).thenReturn(cars);
        Mockito.when(carRepository.findByCarId(-12L)).thenReturn(null);
    }

    @Test
    void whenSearchValidId_thenCarShouldBeFound() {
        long id = 12L;
        Optional<Car> found = employeeService.getCarDetails(id);
        assertThat(found).isPresent();
        assertThat(found.get().getCarId()).isEqualTo(id);
        verifyFindByIdIsCalledOnce();
    }

    @Test
    void whenSearchInvalidId_thenCarShouldNotBeFound() {
        long id = -12L;
        Optional<Car> found = employeeService.getCarDetails(id);
        assertThat(found).isNotPresent();
        verifyFindByIdIsCalledOnce();
    }

    @Test
    void whenSearchAllCars_thenCarsShouldBeFound() {
        Car car = new Car("Audi", "A3");
        car.setCarId(12L);
        Car car2 = new Car("Citroen", "C3");
        Car car3 = new Car("BMW", "M3");

        List<Car> cars = List.of(car, car2, car3);
        List<Car> found = employeeService.getAllCars();
        verifyFindAllCarsIsCalledOnce();

        assertThat(found)
                .hasSize(3)
                .extracting(Car::getModel)
                .contains(car.getModel(), car2.getModel(), car3.getModel());
    }

    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(carRepository, Mockito.times(1)).findByCarId(Mockito.anyLong());
    }

    private void verifyFindAllCarsIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findAll();
    }
}
