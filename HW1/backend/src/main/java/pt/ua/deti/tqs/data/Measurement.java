package pt.ua.deti.tqs.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Measurement {
    private Double concentration;
    @JsonIgnore
    private Integer aqi;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Measurement that = (Measurement) o;
        return Objects.equals(concentration, that.concentration) && Objects.equals(aqi, that.aqi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(concentration, aqi);
    }
}
