package pt.ua.deti.tqs.data;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
public class City {
    private String name;
    private double latitude;
    private double longitude;
    private String country;

    public City(String name, double latitude, double longitude, String country) {
        this.name = name.toLowerCase();
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
    }

    public City(String name, String country) {
        this.name = name.toLowerCase();
        this.country = country;
    }

    public City(String name) {
        this.name = name.toLowerCase();
    }

    public City() {
    }


    public void setName(String name) {
        this.name = name.toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Double.compare(city.latitude, latitude) == 0 && Double.compare(city.longitude, longitude) == 0 && name.equals(city.name) && country.equals(city.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, latitude, longitude, country);
    }

}
