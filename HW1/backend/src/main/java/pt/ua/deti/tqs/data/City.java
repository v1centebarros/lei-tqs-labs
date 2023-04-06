package pt.ua.deti.tqs.data;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class City {
    private String name;
    private Double latitude;
    private Double longitude;
    private String displayName;

    public City(String name, double latitude, double longitude, String displayName) {
        this.name = name.toLowerCase();
        this.latitude = latitude;
        this.longitude = longitude;
        this.displayName = displayName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Double.compare(city.latitude, latitude) == 0 && Double.compare(city.longitude, longitude) == 0 && name.equals(city.name) && displayName.equals(city.displayName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, latitude, longitude, displayName);
    }

    public List<Number> getCoordinates() {
        return List.of(latitude, longitude);
    }

    @Override
    public String toString() {
        return displayName + "(" + latitude + ", " + longitude + ")";
    }
}
