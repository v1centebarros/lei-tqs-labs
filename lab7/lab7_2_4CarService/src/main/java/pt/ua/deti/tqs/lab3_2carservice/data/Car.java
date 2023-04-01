package pt.ua.deti.tqs.lab3_2carservice.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

//TODO Fazer os testes pela ordem C->B->A->E
@Entity
@Table(name = "tqs_car")
public class Car {

    static final int MAX_NAME_SIZE = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long carId;

    @NotNull
    @Size(min = 1, max = MAX_NAME_SIZE)
    private String maker;

    @NotNull
    @Size(min = 1, max = MAX_NAME_SIZE)
    private String model;

    public Car() {
    }

    public Car(String maker, String model) {
        this.maker = maker;
        this.model = model;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return carId == car.carId && Objects.equals(maker, car.maker) && Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carId, maker, model);
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", maker='" + maker + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}